package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.LaudoRequestDTO;
import br.com.dasa.analisepatologica.dto.LaudoResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Laudo;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.enums.StatusLaudo;
import br.com.dasa.analisepatologica.enums.TipoMedico;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.LaudoMapper;
import br.com.dasa.analisepatologica.repository.AmostraRepository;
import br.com.dasa.analisepatologica.repository.LaudoRepository;
import br.com.dasa.analisepatologica.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing medical reports (Laudo).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LaudoService {

    private final LaudoRepository laudoRepository;
    private final AmostraRepository amostraRepository;
    private final MedicoRepository medicoRepository;
    private final LaudoMapper laudoMapper;

    /**
     * Creates a new report for a sample.
     */
    public LaudoResponseDTO create(String codigoRastreio, LaudoRequestDTO requestDTO) {
        log.info("Creating new laudo for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        // Check if sample already has a report
        if (amostra.getLaudo() != null) {
            throw new BusinessException("Amostra já possui laudo cadastrado");
        }

        // Validate sample is ready for report
        if (!amostra.estaProntaParaAnalise()) {
            throw new BusinessException("Amostra não está pronta para análise (necessita medições e imagens)");
        }

        // Find pathologist
        Medico patologista = medicoRepository.findById(requestDTO.patologistaId())
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", requestDTO.patologistaId()));

        // Validate pathologist type
        if (patologista.getTipoMedico() != TipoMedico.PATOLOGISTA) {
            throw new BusinessException("Médico deve ser do tipo PATOLOGISTA");
        }

        if (!patologista.isAtivo()) {
            throw new BusinessException("Patologista não está ativo");
        }

        Laudo laudo = laudoMapper.toEntity(requestDTO, amostra, patologista);
        laudo.setCreatedBy("SYSTEM");

        Laudo savedLaudo = laudoRepository.save(laudo);
        log.info("Laudo created successfully with ID: {} for amostra: {}", savedLaudo.getLaudoId(), codigoRastreio);

        return laudoMapper.toResponseDTO(savedLaudo);
    }

    /**
     * Retrieves a report by ID.
     */
    @Transactional(readOnly = true)
    public LaudoResponseDTO findById(Long id) {
        log.info("Finding laudo by ID: {}", id);
        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));
        return laudoMapper.toResponseDTO(laudo);
    }

    /**
     * Retrieves report by sample tracking code.
     */
    @Transactional(readOnly = true)
    public LaudoResponseDTO findByAmostra(String codigoRastreio) {
        log.info("Finding laudo for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        Laudo laudo = laudoRepository.findByAmostra(amostra)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo não encontrado para amostra: " + codigoRastreio));

        return laudoMapper.toResponseDTO(laudo);
    }

    /**
     * Retrieves all reports.
     */
    @Transactional(readOnly = true)
    public List<LaudoResponseDTO> findAll() {
        log.info("Finding all laudos");
        return laudoRepository.findAll().stream()
                .map(laudoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds reports by status.
     */
    @Transactional(readOnly = true)
    public List<LaudoResponseDTO> findByStatus(StatusLaudo status) {
        log.info("Finding laudos by status: {}", status);
        return laudoRepository.findByStatusLaudo(status).stream()
                .map(laudoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds reports by pathologist.
     */
    @Transactional(readOnly = true)
    public List<LaudoResponseDTO> findByPatologista(Long patologistaId) {
        log.info("Finding laudos by patologista: {}", patologistaId);

        // Find and validate pathologist
        Medico patologista = medicoRepository.findById(patologistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", patologistaId));

        return laudoRepository.findByPatologista(patologista).stream()
                .map(laudoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds reports pending review.
     */
    @Transactional(readOnly = true)
    public List<LaudoResponseDTO> findPendentesRevisao() {
        log.info("Finding laudos pendentes de revisao");
        return laudoRepository.findLaudosPendentesRevisao().stream()
                .map(laudoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds reports ready for release.
     */
    @Transactional(readOnly = true)
    public List<LaudoResponseDTO> findProntosParaLiberacao() {
        log.info("Finding laudos prontos para liberacao");
        return laudoRepository.findLaudosProntosParaLiberacao().stream()
                .map(laudoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates a report.
     */
    public LaudoResponseDTO update(Long id, LaudoRequestDTO requestDTO) {
        log.info("Updating laudo with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        // Check if report can be edited
        if (!laudo.podeSerEditado()) {
            throw new BusinessException("Laudo não pode ser editado no status atual: " + laudo.getStatusLaudo());
        }

        laudoMapper.updateEntityFromDTO(requestDTO, laudo);

        Laudo updatedLaudo = laudoRepository.save(laudo);
        log.info("Laudo updated successfully with ID: {}", updatedLaudo.getLaudoId());

        return laudoMapper.toResponseDTO(updatedLaudo);
    }

    /**
     * Issues a report (changes status from RASCUNHO to EMITIDO).
     */
    public LaudoResponseDTO emitir(Long id) {
        log.info("Emitting laudo with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        // Validate report is complete
        if (!laudo.isCompleto()) {
            throw new BusinessException("Laudo incompleto. Verifique se todos os campos obrigatórios foram preenchidos.");
        }

        laudo.emitir();
        Laudo updatedLaudo = laudoRepository.save(laudo);

        return laudoMapper.toResponseDTO(updatedLaudo);
    }

    /**
     * Releases a report for consultation.
     */
    public LaudoResponseDTO liberar(Long id) {
        log.info("Releasing laudo with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        laudo.liberar();
        Laudo updatedLaudo = laudoRepository.save(laudo);

        return laudoMapper.toResponseDTO(updatedLaudo);
    }

    /**
     * Cancels a report.
     */
    public LaudoResponseDTO cancelar(Long id) {
        log.info("Canceling laudo with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        laudo.cancelar();
        Laudo updatedLaudo = laudoRepository.save(laudo);

        return laudoMapper.toResponseDTO(updatedLaudo);
    }

    /**
     * Sends a report to review.
     */
    public LaudoResponseDTO enviarParaRevisao(Long id) {
        log.info("Sending laudo to review with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        laudo.enviarParaRevisao();
        Laudo updatedLaudo = laudoRepository.save(laudo);

        return laudoMapper.toResponseDTO(updatedLaudo);
    }

    /**
     * Deletes a report.
     */
    public void delete(Long id) {
        log.info("Deleting laudo with ID: {}", id);

        Laudo laudo = laudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laudo", "ID", id));

        // Only allow deletion of drafts or canceled reports
        if (laudo.getStatusLaudo() != StatusLaudo.RASCUNHO &&
            laudo.getStatusLaudo() != StatusLaudo.CANCELADO) {
            throw new BusinessException("Apenas laudos em rascunho ou cancelados podem ser excluídos");
        }

        laudoRepository.delete(laudo);
        log.info("Laudo deleted successfully with ID: {}", id);
    }

    /**
     * Counts reports by status.
     */
    @Transactional(readOnly = true)
    public long countByStatus(StatusLaudo status) {
        return laudoRepository.countByStatusLaudo(status);
    }
}
