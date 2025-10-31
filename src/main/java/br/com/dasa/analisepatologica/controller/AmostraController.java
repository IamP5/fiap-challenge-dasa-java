package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.AmostraControllerOpenApi;
import br.com.dasa.analisepatologica.dto.AmostraFilterDTO;
import br.com.dasa.analisepatologica.dto.AmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.AmostraResponseDTO;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import br.com.dasa.analisepatologica.service.AmostraService;
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
 * REST Controller for Sample (Amostra) operations.
 * Base path: /api/amostras
 *
 * Follows HTTP RFC and RESTful best practices:
 * - Uses proper HTTP methods (GET, POST, PUT, DELETE)
 * - Consolidated endpoints with query parameters for filtering
 * - Consistent resource naming and path structure
 */
@RestController
@RequestMapping(value = "/api/amostras", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AmostraController implements AmostraControllerOpenApi {

    private final AmostraService amostraService;

    /**
     * POST /api/amostras - Creates a new sample.
     *
     * @param requestDTO Sample data
     * @return Created sample with HTTP 201
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmostraResponseDTO> create(@Valid @RequestBody AmostraRequestDTO requestDTO) {
        log.info("POST /api/amostras - Creating new amostra");
        AmostraResponseDTO response = amostraService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/amostras/{codigo} - Retrieves a sample by tracking code.
     *
     * @param codigo Tracking code (path parameter)
     * @return Sample data with HTTP 200
     */
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmostraResponseDTO> findByCodigoRastreio(@PathVariable String codigo) {
        log.info("GET /api/amostras/{} - Finding amostra by codigo", codigo);
        AmostraResponseDTO response = amostraService.findByCodigoRastreio(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/amostras - Retrieves samples with optional filtering.
     *
     * Query parameters:
     * - pacienteId: Filter by patient ID
     * - medicoId: Filter by doctor ID
     * - status: Filter by processing status
     * - tipoTecido: Filter by tissue type (partial match, case-insensitive)
     * - dataColetaInicio: Filter by collection date start
     * - dataColetaFim: Filter by collection date end
     * - prontaParaAnalise: Filter samples ready for analysis (true/false)
     * - semLaudo: Filter samples without report (true/false)
     *
     * @param filter Filter criteria as query parameters
     * @return List of samples with HTTP 200
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmostraResponseDTO>> findAll(AmostraFilterDTO filter) {
        log.info("GET /api/amostras - Finding amostras with filters: {}", filter);
        List<AmostraResponseDTO> response = amostraService.findByFilters(filter);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/amostras/{codigo}/status - Updates sample status.
     * Uses PATCH since we're partially updating the resource (status only).
     *
     * @param codigo Tracking code
     * @param body Request body with new status
     * @return Updated sample with HTTP 200
     */
    @PatchMapping(value = "/{codigo}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmostraResponseDTO> updateStatus(
            @PathVariable String codigo,
            @RequestBody Map<String, String> body) {
        log.info("PATCH /api/amostras/{}/status - Updating status", codigo);

        StatusProcessamento novoStatus = StatusProcessamento.valueOf(body.get("status"));
        AmostraResponseDTO response = amostraService.updateStatus(codigo, novoStatus);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/amostras/{codigo} - Updates a sample.
     * Uses PUT for full resource replacement.
     *
     * @param codigo Tracking code
     * @param requestDTO Updated sample data
     * @return Updated sample with HTTP 200
     */
    @PutMapping(value = "/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmostraResponseDTO> update(
            @PathVariable String codigo,
            @Valid @RequestBody AmostraRequestDTO requestDTO) {
        log.info("PUT /api/amostras/{} - Updating amostra", codigo);
        AmostraResponseDTO response = amostraService.update(codigo, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/amostras/{codigo} - Deletes a sample.
     *
     * @param codigo Tracking code
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable String codigo) {
        log.info("DELETE /api/amostras/{} - Deleting amostra", codigo);
        amostraService.delete(codigo);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/amostras/stats/count - Counts samples with optional filtering.
     * Uses query parameters for filtering instead of path parameters.
     *
     * Query parameters:
     * - status: Count by processing status
     * - pacienteId: Count by patient ID
     * - medicoId: Count by doctor ID
     *
     * @param status Processing status (optional)
     * @param pacienteId Patient ID (optional)
     * @param medicoId Doctor ID (optional)
     * @return Count with HTTP 200
     */
    @GetMapping(value = "/stats/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> count(
            @RequestParam(required = false) StatusProcessamento status,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) Long medicoId) {
        log.info("GET /api/amostras/stats/count - status: {}, pacienteId: {}, medicoId: {}", status, pacienteId, medicoId);
        long count = amostraService.count(status, pacienteId, medicoId);
        return ResponseEntity.ok(count);
    }
}
