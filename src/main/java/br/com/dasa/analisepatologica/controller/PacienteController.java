package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.PacienteControllerOpenApi;
import br.com.dasa.analisepatologica.dto.PacienteFilterDTO;
import br.com.dasa.analisepatologica.dto.PacienteRequestDTO;
import br.com.dasa.analisepatologica.dto.PacienteResponseDTO;
import br.com.dasa.analisepatologica.enums.Sexo;
import br.com.dasa.analisepatologica.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Patient (Paciente) operations.
 * Base path: /api/pacientes
 */
@RestController
@RequestMapping(value = "/api/pacientes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PacienteController implements PacienteControllerOpenApi {

    private final PacienteService pacienteService;

    /**
     * POST /api/pacientes - Creates a new patient.
     *
     * @param requestDTO Patient data
     * @return Created patient with HTTP 201
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("POST /api/pacientes - Creating new patient");
        PacienteResponseDTO response = pacienteService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/pacientes/{id} - Retrieves a patient by ID.
     *
     * @param id Patient ID
     * @return Patient data with HTTP 200
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> findById(@PathVariable Long id) {
        log.info("GET /api/pacientes/{} - Finding patient by ID", id);
        PacienteResponseDTO response = pacienteService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/pacientes/cpf/{cpf} - Retrieves a patient by CPF.
     *
     * @param cpf Patient CPF
     * @return Patient data with HTTP 200
     */
    @GetMapping(value = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> findByCpf(@PathVariable String cpf) {
        log.info("GET /api/pacientes/cpf/{} - Finding patient by CPF", cpf);
        PacienteResponseDTO response = pacienteService.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/pacientes - Retrieves patients with optional filters.
     *
     * Query parameters:
     * - nome: Filter by name (partial match, case insensitive)
     * - cpf: Filter by CPF
     * - sexo: Filter by gender (MASCULINO, FEMININO, OUTRO)
     * - dataNascimentoInicio: Filter by birth date start (ISO format: yyyy-MM-dd)
     * - dataNascimentoFim: Filter by birth date end (ISO format: yyyy-MM-dd)
     * - comAmostras: Filter patients with samples (true/false)
     *
     * @param filter Filter criteria
     * @return List of patients with HTTP 200
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PacienteResponseDTO>> findAll(PacienteFilterDTO filter) {
        log.info("GET /api/pacientes - Finding patients with filters: {}", filter);
        List<PacienteResponseDTO> response = pacienteService.findByFilters(filter);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/pacientes/{id} - Updates an existing patient.
     *
     * @param id Patient ID
     * @param requestDTO Updated patient data
     * @return Updated patient with HTTP 200
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO requestDTO) {
        log.info("PUT /api/pacientes/{} - Updating patient", id);
        PacienteResponseDTO response = pacienteService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/pacientes/{id} - Deletes a patient.
     *
     * @param id Patient ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/pacientes/{} - Deleting patient", id);
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/pacientes/stats/count - Counts patients with optional filter.
     *
     * @param sexo Gender (optional)
     * @return Count with HTTP 200
     */
    @GetMapping(value = "/stats/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> countBySexo(@RequestParam(required = false) Sexo sexo) {
        log.info("GET /api/pacientes/stats/count?sexo={}", sexo);
        long count = sexo != null ? pacienteService.countBySexo(sexo) : pacienteService.countAll();
        return ResponseEntity.ok(Map.of("count", count));
    }
}
