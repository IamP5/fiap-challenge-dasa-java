package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.MedicoControllerOpenApi;
import br.com.dasa.analisepatologica.dto.MedicoFilterDTO;
import br.com.dasa.analisepatologica.dto.MedicoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicoResponseDTO;
import br.com.dasa.analisepatologica.enums.TipoMedico;
import br.com.dasa.analisepatologica.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Doctor (Medico) operations.
 * Base path: /api/medicos
 */
@RestController
@RequestMapping(value = "/api/medicos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class MedicoController implements MedicoControllerOpenApi {

    private final MedicoService medicoService;

    /**
     * POST /api/medicos - Creates a new doctor.
     *
     * @param requestDTO Doctor data
     * @return Created doctor with HTTP 201
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> create(@Valid @RequestBody MedicoRequestDTO requestDTO) {
        log.info("POST /api/medicos - Creating new medico");
        MedicoResponseDTO response = medicoService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/medicos/{id} - Retrieves a doctor by ID.
     *
     * @param id Doctor ID
     * @return Doctor data with HTTP 200
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> findById(@PathVariable Long id) {
        log.info("GET /api/medicos/{} - Finding medico by ID", id);
        MedicoResponseDTO response = medicoService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/medicos/crm/{crm}/uf/{uf} - Retrieves a doctor by CRM and UF.
     *
     * @param crm Doctor CRM
     * @param uf CRM state/UF
     * @return Doctor data with HTTP 200
     */
    @GetMapping(value = "/crm/{crm}/uf/{uf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> findByCrm(@PathVariable String crm, @PathVariable String uf) {
        log.info("GET /api/medicos/crm/{}/uf/{} - Finding medico by CRM", crm, uf);
        MedicoResponseDTO response = medicoService.findByCrm(crm, uf.toUpperCase());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/medicos - Retrieves doctors with optional filters.
     *
     * Query parameters:
     * - nome: Filter by name (partial match, case insensitive)
     * - crm: Filter by CRM number
     * - ufCrm: Filter by CRM state
     * - especialidade: Filter by specialty (partial match, case insensitive)
     * - tipo: Filter by type (SOLICITANTE, PATOLOGISTA)
     * - ativo: Filter by active status (true/false)
     *
     * @param filter Filter criteria
     * @return List of doctors with HTTP 200
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicoResponseDTO>> findAll(MedicoFilterDTO filter) {
        log.info("GET /api/medicos - Finding medicos with filters: {}", filter);
        List<MedicoResponseDTO> response = medicoService.findByFilters(filter);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/medicos/{id} - Updates an existing doctor.
     *
     * @param id Doctor ID
     * @param requestDTO Updated doctor data
     * @return Updated doctor with HTTP 200
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicoRequestDTO requestDTO) {
        log.info("PUT /api/medicos/{} - Updating medico", id);
        MedicoResponseDTO response = medicoService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/medicos/{id}/ativar - Activates a doctor.
     *
     * @param id Doctor ID
     * @return Updated doctor with HTTP 200
     */
    @PatchMapping(value = "/{id}/ativar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> ativar(@PathVariable Long id) {
        log.info("PATCH /api/medicos/{}/ativar - Activating medico", id);
        MedicoResponseDTO response = medicoService.ativar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/medicos/{id}/desativar - Deactivates a doctor.
     *
     * @param id Doctor ID
     * @return Updated doctor with HTTP 200
     */
    @PatchMapping(value = "/{id}/desativar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicoResponseDTO> desativar(@PathVariable Long id) {
        log.info("PATCH /api/medicos/{}/desativar - Deactivating medico", id);
        MedicoResponseDTO response = medicoService.desativar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/medicos/{id} - Deletes a doctor.
     *
     * @param id Doctor ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/medicos/{} - Deleting medico", id);
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/medicos/stats/count - Counts doctors with optional filter.
     *
     * @param tipo Type (optional)
     * @return Count with HTTP 200
     */
    @GetMapping(value = "/stats/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> countByTipo(@RequestParam(required = false) TipoMedico tipo) {
        log.info("GET /api/medicos/stats/count?tipo={}", tipo);
        long count = tipo != null ? medicoService.countByTipo(tipo) : medicoService.countAll();
        return ResponseEntity.ok(Map.of("count", count));
    }
}
