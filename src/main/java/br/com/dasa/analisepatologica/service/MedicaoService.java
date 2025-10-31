package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.MedicaoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicaoResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Medicao;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.MedicaoMapper;
import br.com.dasa.analisepatologica.repository.AmostraRepository;
import br.com.dasa.analisepatologica.repository.MedicaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing measurements (Medicao).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MedicaoService {

    private final MedicaoRepository medicaoRepository;
    private final AmostraRepository amostraRepository;
    private final MedicaoMapper medicaoMapper;

    /**
     * Creates a new measurement for a sample.
     */
    public MedicaoResponseDTO create(String codigoRastreio, MedicaoRequestDTO requestDTO) {
        log.info("Creating new medicao for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        // Check if sample is in appropriate status
        if (amostra.getStatusProcessamento() == StatusProcessamento.CANCELADA ||
            amostra.getStatusProcessamento() == StatusProcessamento.LIBERADA) {
            throw new BusinessException("Não é possível adicionar medição a amostra com status: " + amostra.getStatusProcessamento());
        }

        // Deactivate previous measurements
        List<Medicao> previousMedicoes = medicaoRepository.findByAmostra(amostra);
        previousMedicoes.forEach(Medicao::desativar);
        medicaoRepository.saveAll(previousMedicoes);

        // Calculate next version
        Integer maxVersao = medicaoRepository.findMaxVersaoByAmostra(amostra);
        Integer novaVersao = (maxVersao != null ? maxVersao : 0) + 1;

        Medicao medicao = medicaoMapper.toEntity(requestDTO, amostra, novaVersao);
        medicao.setCreatedBy("SYSTEM");

        // Manually set FK columns from amostra
        medicao.setAmostraId(amostra.getAmostraId());
        medicao.setPacienteId(amostra.getPacienteId());
        medicao.setMedicoId(amostra.getMedicoId());

        Medicao savedMedicao = medicaoRepository.save(medicao);
        log.info("Medicao created successfully with ID: {} for amostra: {}", savedMedicao.getMedicaoId(), codigoRastreio);

        // Update sample status if needed
        if (amostra.getStatusProcessamento() == StatusProcessamento.RECEBIDA ||
            amostra.getStatusProcessamento() == StatusProcessamento.EM_PROCESSAMENTO) {
            amostra.atualizarStatus(StatusProcessamento.MEDIDA);
            amostraRepository.save(amostra);
        }

        return medicaoMapper.toResponseDTO(savedMedicao);
    }

    /**
     * Retrieves all measurements for a sample.
     */
    @Transactional(readOnly = true)
    public List<MedicaoResponseDTO> findByAmostra(String codigoRastreio) {
        log.info("Finding medicoes for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        return medicaoRepository.findByAmostraOrderByVersaoDesc(amostra)
                .stream()
                .map(medicaoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the active measurement for a sample.
     */
    @Transactional(readOnly = true)
    public MedicaoResponseDTO findMedicaoAtiva(String codigoRastreio) {
        log.info("Finding active medicao for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        Medicao medicaoAtiva = medicaoRepository.findByAmostraAndAtiva(amostra, 'S')
                .orElseThrow(() -> new ResourceNotFoundException("Medicao ativa não encontrada para amostra: " + codigoRastreio));

        return medicaoMapper.toResponseDTO(medicaoAtiva);
    }

    /**
     * Activates a specific measurement version.
     */
    public MedicaoResponseDTO ativarVersao(String codigoRastreio, Integer versao) {
        log.info("Activating medicao version {} for amostra: {}", versao, codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        List<Medicao> medicoes = medicaoRepository.findByAmostra(amostra);

        // Find the measurement with the specified version
        Medicao medicaoToActivate = medicoes.stream()
                .filter(m -> m.getVersao().equals(versao))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Medicao", "versao", versao));

        // Deactivate all measurements
        medicoes.forEach(Medicao::desativar);

        // Activate the selected one
        medicaoToActivate.ativar();

        medicaoRepository.saveAll(medicoes);

        return medicaoMapper.toResponseDTO(medicaoToActivate);
    }

    /**
     * Deletes a measurement.
     */
    public void delete(Long medicaoId, Long medicoId) {
        log.info("Deleting medicao with ID: {}", medicaoId);

        // Note: Need to use composite key to find
        // For simplicity, we'll find all and filter
        // In production, you might want a better approach
        throw new BusinessException("Delete de medições não é permitido. Use ativar/desativar versões.");
    }
}
