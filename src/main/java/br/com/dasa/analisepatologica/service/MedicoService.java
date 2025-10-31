package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.MedicoFilterDTO;
import br.com.dasa.analisepatologica.dto.MedicoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicoResponseDTO;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.enums.TipoMedico;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.MedicoMapper;
import br.com.dasa.analisepatologica.repository.MedicoRepository;
import br.com.dasa.analisepatologica.repository.specification.MedicoSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing doctors (Medico).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final MedicoMapper medicoMapper;

    /**
     * Creates a new doctor.
     */
    public MedicoResponseDTO create(MedicoRequestDTO requestDTO) {
        log.info("Creating new medico: {}", requestDTO.nomeCompleto());

        // Validate CRM uniqueness
        if (medicoRepository.existsByCrmAndUfCrm(requestDTO.crm(), requestDTO.ufCrm())) {
            throw new BusinessException("CRM já cadastrado: " + requestDTO.crm() + "/" + requestDTO.ufCrm());
        }

        Medico medico = medicoMapper.toEntity(requestDTO);
        medico.setCreatedBy("SYSTEM");

        // Validate CRM format
        if (!medico.validarCrm()) {
            throw new BusinessException("CRM inválido: " + medico.getCrm());
        }

        Medico savedMedico = medicoRepository.save(medico);
        log.info("Medico created successfully with ID: {}", savedMedico.getMedicoId());

        return medicoMapper.toResponseDTO(savedMedico);
    }

    /**
     * Retrieves a doctor by ID.
     */
    @Transactional(readOnly = true)
    public MedicoResponseDTO findById(Long id) {
        log.info("Finding medico by ID: {}", id);
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", id));
        return medicoMapper.toResponseDTO(medico);
    }

    /**
     * Retrieves a doctor by CRM and UF.
     */
    @Transactional(readOnly = true)
    public MedicoResponseDTO findByCrm(String crm, String uf) {
        log.info("Finding medico by CRM: {}/{}", crm, uf);
        Medico medico = medicoRepository.findByCrmAndUfCrm(crm, uf)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "CRM", crm + "/" + uf));
        return medicoMapper.toResponseDTO(medico);
    }

    /**
     * Retrieves all doctors.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> findAll() {
        log.info("Finding all medicos");
        return medicoRepository.findAll().stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds doctors by filter criteria using database-level filtering.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> findByFilters(MedicoFilterDTO filter) {
        log.info("Finding medicos by filters: {}", filter);

        if (!filter.hasAnyFilter()) {
            return findAll();
        }

        Specification<Medico> spec = MedicoSpecification.buildSpecification(filter);
        List<Medico> medicos = medicoRepository.findAll(spec);

        return medicos.stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches doctors by name.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> searchByName(String nome) {
        log.info("Searching medicos by name: {}", nome);
        return medicoRepository.findByNomeCompletoContainingIgnoreCase(nome).stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds doctors by type.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> findByTipo(TipoMedico tipo) {
        log.info("Finding medicos by tipo: {}", tipo);
        return medicoRepository.findByTipoMedico(tipo).stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds active doctors.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> findAtivos() {
        log.info("Finding active medicos");
        return medicoRepository.findByAtivo('S').stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches doctors by specialty.
     */
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> searchByEspecialidade(String especialidade) {
        log.info("Searching medicos by especialidade: {}", especialidade);
        return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade).stream()
                .map(medicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing doctor.
     */
    public MedicoResponseDTO update(Long id, MedicoRequestDTO requestDTO) {
        log.info("Updating medico with ID: {}", id);
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", id));

        // Validate CRM uniqueness if changed
        if (!requestDTO.crm().equals(medico.getCrm()) || !requestDTO.ufCrm().equals(medico.getUfCrm())) {
            if (medicoRepository.existsByCrmAndUfCrm(requestDTO.crm(), requestDTO.ufCrm())) {
                throw new BusinessException("CRM já cadastrado: " + requestDTO.crm() + "/" + requestDTO.ufCrm());
            }
        }

        medicoMapper.updateEntityFromDTO(requestDTO, medico);

        if (!medico.validarCrm()) {
            throw new BusinessException("CRM inválido: " + medico.getCrm());
        }

        Medico updatedMedico = medicoRepository.save(medico);
        log.info("Medico updated successfully with ID: {}", updatedMedico.getMedicoId());

        return medicoMapper.toResponseDTO(updatedMedico);
    }

    /**
     * Activates a doctor.
     */
    public MedicoResponseDTO ativar(Long id) {
        log.info("Activating medico with ID: {}", id);
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", id));

        medico.ativar();
        Medico updatedMedico = medicoRepository.save(medico);

        return medicoMapper.toResponseDTO(updatedMedico);
    }

    /**
     * Deactivates a doctor.
     */
    public MedicoResponseDTO desativar(Long id) {
        log.info("Deactivating medico with ID: {}", id);
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", id));

        medico.desativar();
        Medico updatedMedico = medicoRepository.save(medico);

        return medicoMapper.toResponseDTO(updatedMedico);
    }

    /**
     * Deletes a doctor.
     */
    public void delete(Long id) {
        log.info("Deleting medico with ID: {}", id);
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", "ID", id));

        // Check if medico has samples or reports
        if ((medico.getAmostrasSolicitadas() != null && !medico.getAmostrasSolicitadas().isEmpty()) ||
            (medico.getLaudosEmitidos() != null && !medico.getLaudosEmitidos().isEmpty())) {
            throw new BusinessException("Não é possível excluir médico com amostras ou laudos cadastrados");
        }

        medicoRepository.delete(medico);
        log.info("Medico deleted successfully with ID: {}", id);
    }

    /**
     * Counts doctors by type.
     */
    @Transactional(readOnly = true)
    public long countByTipo(TipoMedico tipo) {
        return medicoRepository.countByTipoMedico(tipo);
    }

    /**
     * Counts all doctors.
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return medicoRepository.count();
    }
}
