package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.LaudoControllerOpenApi;
import br.com.dasa.analisepatologica.dto.LaudoRequestDTO;
import br.com.dasa.analisepatologica.dto.LaudoResponseDTO;
import br.com.dasa.analisepatologica.enums.StatusLaudo;
import br.com.dasa.analisepatologica.service.LaudoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Medical Report (Laudo) operations.
 * Base path: /api/laudos
 */
@RestController
@RequestMapping(value = "/api/laudos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class LaudoController implements LaudoControllerOpenApi {

    private final LaudoService laudoService;

    /**
     * POST /api/laudos/amostra/{codigo} - Creates a new report for a sample.
     *
     * @param codigo Sample tracking code
     * @param requestDTO Report data
     * @return Created report with HTTP 201
     */
    @PostMapping(value = "/amostra/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> create(
            @PathVariable String codigo,
            @Valid @RequestBody LaudoRequestDTO requestDTO) {
        log.info("POST /api/laudos/amostra/{} - Creating new laudo", codigo);
        LaudoResponseDTO response = laudoService.create(codigo, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/laudos/{id} - Retrieves a report by ID.
     *
     * @param id Report ID
     * @return Report data with HTTP 200
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> findById(@PathVariable Long id) {
        log.info("GET /api/laudos/{} - Finding laudo by ID", id);
        LaudoResponseDTO response = laudoService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos/amostra/{codigo} - Retrieves report by sample tracking code.
     *
     * @param codigo Sample tracking code
     * @return Report data with HTTP 200
     */
    @GetMapping(value = "/amostra/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> findByAmostra(@PathVariable String codigo) {
        log.info("GET /api/laudos/amostra/{} - Finding laudo for amostra", codigo);
        LaudoResponseDTO response = laudoService.findByAmostra(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos - Retrieves all reports.
     *
     * @return List of reports with HTTP 200
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LaudoResponseDTO>> findAll() {
        log.info("GET /api/laudos - Finding all laudos");
        List<LaudoResponseDTO> response = laudoService.findAll();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos/status/{status} - Finds reports by status.
     *
     * @param status Report status
     * @return List of reports with HTTP 200
     */
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LaudoResponseDTO>> findByStatus(@PathVariable StatusLaudo status) {
        log.info("GET /api/laudos/status/{} - Finding laudos by status", status);
        List<LaudoResponseDTO> response = laudoService.findByStatus(status);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos/patologista/{id} - Finds reports by pathologist.
     *
     * @param id Pathologist ID
     * @return List of reports with HTTP 200
     */
    @GetMapping(value = "/patologista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LaudoResponseDTO>> findByPatologista(@PathVariable Long id) {
        log.info("GET /api/laudos/patologista/{} - Finding laudos by patologista", id);
        List<LaudoResponseDTO> response = laudoService.findByPatologista(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos/pendentes-revisao - Finds reports pending review.
     *
     * @return List of reports with HTTP 200
     */
    @GetMapping(value = "/pendentes-revisao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LaudoResponseDTO>> findPendentesRevisao() {
        log.info("GET /api/laudos/pendentes-revisao - Finding laudos pendentes");
        List<LaudoResponseDTO> response = laudoService.findPendentesRevisao();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/laudos/prontos-liberacao - Finds reports ready for release.
     *
     * @return List of reports with HTTP 200
     */
    @GetMapping(value = "/prontos-liberacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LaudoResponseDTO>> findProntosParaLiberacao() {
        log.info("GET /api/laudos/prontos-liberacao - Finding laudos prontos");
        List<LaudoResponseDTO> response = laudoService.findProntosParaLiberacao();
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/laudos/{id} - Updates a report.
     *
     * @param id Report ID
     * @param requestDTO Updated report data
     * @return Updated report with HTTP 200
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LaudoRequestDTO requestDTO) {
        log.info("PUT /api/laudos/{} - Updating laudo", id);
        LaudoResponseDTO response = laudoService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/laudos/{id}/emitir - Issues a report.
     * Uses PATCH since we're partially updating the resource (status only).
     *
     * @param id Report ID
     * @return Updated report with HTTP 200
     */
    @PatchMapping(value = "/{id}/emitir", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> emitir(@PathVariable Long id) {
        log.info("PATCH /api/laudos/{}/emitir - Issuing laudo", id);
        LaudoResponseDTO response = laudoService.emitir(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/laudos/{id}/liberar - Releases a report.
     * Uses PATCH since we're partially updating the resource (status only).
     *
     * @param id Report ID
     * @return Updated report with HTTP 200
     */
    @PatchMapping(value = "/{id}/liberar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> liberar(@PathVariable Long id) {
        log.info("PATCH /api/laudos/{}/liberar - Releasing laudo", id);
        LaudoResponseDTO response = laudoService.liberar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/laudos/{id}/cancelar - Cancels a report.
     * Uses PATCH since we're partially updating the resource (status only).
     *
     * @param id Report ID
     * @return Updated report with HTTP 200
     */
    @PatchMapping(value = "/{id}/cancelar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> cancelar(@PathVariable Long id) {
        log.info("PATCH /api/laudos/{}/cancelar - Canceling laudo", id);
        LaudoResponseDTO response = laudoService.cancelar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/laudos/{id}/enviar-revisao - Sends a report to review.
     * Uses PATCH since we're partially updating the resource (status only).
     *
     * @param id Report ID
     * @return Updated report with HTTP 200
     */
    @PatchMapping(value = "/{id}/enviar-revisao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LaudoResponseDTO> enviarParaRevisao(@PathVariable Long id) {
        log.info("PATCH /api/laudos/{}/enviar-revisao - Sending laudo to review", id);
        LaudoResponseDTO response = laudoService.enviarParaRevisao(id);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/laudos/{id} - Deletes a report.
     *
     * @param id Report ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/laudos/{} - Deleting laudo", id);
        laudoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/laudos/count/status/{status} - Counts reports by status.
     *
     * @param status Report status
     * @return Count with HTTP 200
     */
    @GetMapping(value = "/count/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countByStatus(@PathVariable StatusLaudo status) {
        log.info("GET /api/laudos/count/status/{}", status);
        long count = laudoService.countByStatus(status);
        return ResponseEntity.ok(count);
    }
}
