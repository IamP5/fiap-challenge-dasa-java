package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.AmostraFilterDTO;
import br.com.dasa.analisepatologica.dto.AmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.AmostraResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.entity.Paciente;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import br.com.dasa.analisepatologica.enums.TipoMedico;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.AmostraMapper;
import br.com.dasa.analisepatologica.repository.AmostraRepository;
import br.com.dasa.analisepatologica.repository.MedicoRepository;
import br.com.dasa.analisepatologica.repository.PacienteRepository;
import br.com.dasa.analisepatologica.repository.specification.AmostraSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing samples (Amostra).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AmostraService {

    private final AmostraRepository amostraRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final AmostraMapper amostraMapper;

    /**
     * Creates a new sample.
     */
    public AmostraResponseDTO create(AmostraRequestDTO requestDTO) {
        log.info("Creating new amostra: {}", requestDTO.codigoRastreio());

        // Validate tracking code uniqueness
        if (amostraRepository.existsByCodigoRastreio(requestDTO.codigoRastreio())) {
            throw new BusinessException("Código de rastreio já cadastrado: " + requestDTO.codigoRastreio());
        }

        // Find patient
        Paciente paciente = pacienteRepository.findById(requestDTO.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "ID", requestDTO.pacienteId()));

        // Find doctor and validate type
        Medico medico = medicoRepository.findById(requestDTO.medicoSolicitanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", requestDTO.medicoSolicitanteId()));

        if (medico.getTipoMedico() != TipoMedico.SOLICITANTE) {
            throw new BusinessException("Médico deve ser do tipo SOLICITANTE");
        }

        if (!medico.isAtivo()) {
            throw new BusinessException("Médico solicitante não está ativo");
        }

        Amostra amostra = amostraMapper.toEntity(requestDTO, paciente, medico);
        amostra.setCreatedBy("SYSTEM");

        // Manually set composite key IDs from relationships
        // This is required for @IdClass entities with insertable=false, updatable=false
        amostra.setPacienteId(paciente.getPacienteId());
        amostra.setMedicoId(medico.getMedicoId());

        Amostra savedAmostra = amostraRepository.save(amostra);
        log.info("Amostra created successfully with ID: {}", savedAmostra.getAmostraId());

        return amostraMapper.toResponseDTO(savedAmostra);
    }

    /**
     * Retrieves a sample by tracking code.
     */
    @Transactional(readOnly = true)
    public AmostraResponseDTO findByCodigoRastreio(String codigoRastreio) {
        log.info("Finding amostra by codigo rastreio: {}", codigoRastreio);
        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));
        return amostraMapper.toResponseDTO(amostra);
    }

    /**
     * Retrieves all samples.
     */
    @Transactional(readOnly = true)
    public List<AmostraResponseDTO> findAll() {
        log.info("Finding all amostras");
        return amostraRepository.findAll().stream()
                .map(amostraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds samples by multiple filter criteria using JPA Specifications.
     * Executes filtering at the database level for optimal performance.
     * If no filters are provided, returns all samples.
     */
    @Transactional(readOnly = true)
    public List<AmostraResponseDTO> findByFilters(AmostraFilterDTO filter) {
        log.info("Finding amostras with filters: {}", filter);

        // Validate related entities exist if filtering by them
        if (filter.pacienteId() != null && !pacienteRepository.existsById(filter.pacienteId())) {
            throw new ResourceNotFoundException("Paciente", "ID", filter.pacienteId());
        }

        if (filter.medicoId() != null && !medicoRepository.existsById(filter.medicoId())) {
            throw new ResourceNotFoundException("Medico", "ID", filter.medicoId());
        }

        // If no filters, return all samples
        if (!filter.hasAnyFilter()) {
            return findAll();
        }

        // Build and execute Specification at database level
        Specification<Amostra> spec = AmostraSpecification.buildSpecification(filter);
        List<Amostra> amostras = amostraRepository.findAll(spec);

        return amostras.stream()
                .map(amostraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates sample status.
     */
    public AmostraResponseDTO updateStatus(String codigoRastreio, StatusProcessamento novoStatus) {
        log.info("Updating amostra {} status to {}", codigoRastreio, novoStatus);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        amostra.atualizarStatus(novoStatus);
        Amostra updatedAmostra = amostraRepository.save(amostra);

        return amostraMapper.toResponseDTO(updatedAmostra);
    }

    /**
     * Updates a sample.
     */
    public AmostraResponseDTO update(String codigoRastreio, AmostraRequestDTO requestDTO) {
        log.info("Updating amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        amostraMapper.updateEntityFromDTO(requestDTO, amostra);
        Amostra updatedAmostra = amostraRepository.save(amostra);

        return amostraMapper.toResponseDTO(updatedAmostra);
    }

    /**
     * Deletes a sample.
     */
    public void delete(String codigoRastreio) {
        log.info("Deleting amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        // Check if sample has a report
        if (amostra.temLaudo()) {
            throw new BusinessException("Não é possível excluir amostra com laudo cadastrado");
        }

        amostraRepository.delete(amostra);
        log.info("Amostra deleted successfully: {}", codigoRastreio);
    }

    /**
     * Counts samples with optional filters.
     * If no filters are provided, returns total count.
     */
    @Transactional(readOnly = true)
    public long count(StatusProcessamento status, Long pacienteId, Long medicoId) {
        log.info("Counting amostras - status: {}, pacienteId: {}, medicoId: {}", status, pacienteId, medicoId);

        // Return appropriate count based on provided filters
        if (status != null) {
            return amostraRepository.countByStatusProcessamento(status);
        } else if (pacienteId != null) {
            if (!pacienteRepository.existsById(pacienteId)) {
                throw new ResourceNotFoundException("Paciente", "ID", pacienteId);
            }
            return amostraRepository.countByPacienteId(pacienteId);
        } else if (medicoId != null) {
            if (!medicoRepository.existsById(medicoId)) {
                throw new ResourceNotFoundException("Medico", "ID", medicoId);
            }
            return amostraRepository.countByMedicoId(medicoId);
        }

        // No filters - return total count
        return amostraRepository.count();
    }
}
