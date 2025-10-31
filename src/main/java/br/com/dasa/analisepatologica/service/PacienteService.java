package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.PacienteFilterDTO;
import br.com.dasa.analisepatologica.dto.PacienteRequestDTO;
import br.com.dasa.analisepatologica.dto.PacienteResponseDTO;
import br.com.dasa.analisepatologica.entity.Paciente;
import br.com.dasa.analisepatologica.enums.Sexo;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.PacienteMapper;
import br.com.dasa.analisepatologica.repository.PacienteRepository;
import br.com.dasa.analisepatologica.repository.specification.PacienteSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing patients (Paciente).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    /**
     * Creates a new patient.
     */
    public PacienteResponseDTO create(PacienteRequestDTO requestDTO) {
        log.info("Creating new patient: {}", requestDTO.nomeCompleto());

        // Validate CPF uniqueness if provided
        if (requestDTO.cpf() != null && pacienteRepository.existsByCpf(requestDTO.cpf())) {
            throw new BusinessException("CPF já cadastrado: " + requestDTO.cpf());
        }

        Paciente paciente = pacienteMapper.toEntity(requestDTO);
        paciente.setCreatedBy("SYSTEM"); // TODO: Get from security context

        // Validate CPF checksum if provided
        if (paciente.getCpf() != null && !paciente.validarCpf()) {
            throw new BusinessException("CPF inválido: " + paciente.getCpf());
        }

        Paciente savedPaciente = pacienteRepository.save(paciente);
        log.info("Patient created successfully with ID: {}", savedPaciente.getPacienteId());

        return pacienteMapper.toResponseDTO(savedPaciente);
    }

    /**
     * Retrieves a patient by ID.
     */
    @Transactional(readOnly = true)
    public PacienteResponseDTO findById(Long id) {
        log.info("Finding patient by ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "ID", id));

        return pacienteMapper.toResponseDTO(paciente);
    }

    /**
     * Retrieves a patient by CPF.
     */
    @Transactional(readOnly = true)
    public PacienteResponseDTO findByCpf(String cpf) {
        log.info("Finding patient by CPF: {}", cpf);

        Paciente paciente = pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "CPF", cpf));

        return pacienteMapper.toResponseDTO(paciente);
    }

    /**
     * Retrieves all patients.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findAll() {
        log.info("Finding all patients");

        return pacienteRepository.findAll().stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds patients by filter criteria using database-level filtering.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findByFilters(PacienteFilterDTO filter) {
        log.info("Finding patients by filters: {}", filter);

        if (!filter.hasAnyFilter()) {
            return findAll();
        }

        Specification<Paciente> spec = PacienteSpecification.buildSpecification(filter);
        List<Paciente> pacientes = pacienteRepository.findAll(spec);

        return pacientes.stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches patients by name.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> searchByName(String nome) {
        log.info("Searching patients by name: {}", nome);

        return pacienteRepository.findByNomeCompletoContainingIgnoreCase(nome).stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds patients by gender.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findBySexo(Sexo sexo) {
        log.info("Finding patients by gender: {}", sexo);

        return pacienteRepository.findBySexo(sexo).stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds patients born between two dates.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim) {
        log.info("Finding patients born between {} and {}", inicio, fim);

        return pacienteRepository.findByDataNascimentoBetween(inicio, fim).stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing patient.
     */
    public PacienteResponseDTO update(Long id, PacienteRequestDTO requestDTO) {
        log.info("Updating patient with ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "ID", id));

        // Validate CPF uniqueness if changed
        if (requestDTO.cpf() != null && !requestDTO.cpf().equals(paciente.getCpf())) {
            if (pacienteRepository.existsByCpf(requestDTO.cpf())) {
                throw new BusinessException("CPF já cadastrado: " + requestDTO.cpf());
            }
        }

        pacienteMapper.updateEntityFromDTO(requestDTO, paciente);

        // Validate CPF checksum if provided
        if (paciente.getCpf() != null && !paciente.validarCpf()) {
            throw new BusinessException("CPF inválido: " + paciente.getCpf());
        }

        Paciente updatedPaciente = pacienteRepository.save(paciente);
        log.info("Patient updated successfully with ID: {}", updatedPaciente.getPacienteId());

        return pacienteMapper.toResponseDTO(updatedPaciente);
    }

    /**
     * Deletes a patient.
     */
    public void delete(Long id) {
        log.info("Deleting patient with ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "ID", id));

        // Check if patient has samples
        if (paciente.getAmostras() != null && !paciente.getAmostras().isEmpty()) {
            throw new BusinessException("Não é possível excluir paciente com amostras cadastradas");
        }

        pacienteRepository.delete(paciente);
        log.info("Patient deleted successfully with ID: {}", id);
    }

    /**
     * Counts patients by gender.
     */
    @Transactional(readOnly = true)
    public long countBySexo(Sexo sexo) {
        return pacienteRepository.countBySexo(sexo);
    }

    /**
     * Finds patients with samples.
     */
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> findPacientesComAmostras() {
        log.info("Finding patients with samples");

        return pacienteRepository.findPacientesComAmostras().stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Counts all patients.
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return pacienteRepository.count();
    }
}
