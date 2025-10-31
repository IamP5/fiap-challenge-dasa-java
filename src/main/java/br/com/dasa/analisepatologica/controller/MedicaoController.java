package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.MedicaoControllerOpenApi;
import br.com.dasa.analisepatologica.dto.MedicaoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicaoResponseDTO;
import br.com.dasa.analisepatologica.service.MedicaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Measurement (Medicao) operations.
 * Base path: /api/medicoes
 */
@RestController
@RequestMapping(value = "/api/medicoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class MedicaoController implements MedicaoControllerOpenApi {

    private final MedicaoService medicaoService;

    /**
     * POST /api/medicoes/amostra/{codigo} - Creates a new measurement for a sample.
     *
     * @param codigo Sample tracking code
     * @param requestDTO Measurement data
     * @return Created measurement with HTTP 201
     */
    @PostMapping(value = "/amostra/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicaoResponseDTO> create(
            @PathVariable String codigo,
            @Valid @RequestBody MedicaoRequestDTO requestDTO) {
        log.info("POST /api/medicoes/amostra/{} - Creating new medicao", codigo);
        MedicaoResponseDTO response = medicaoService.create(codigo, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/medicoes/amostra/{codigo} - Retrieves all measurements for a sample.
     *
     * @param codigo Sample tracking code
     * @return List of measurements with HTTP 200
     */
    @GetMapping(value = "/amostra/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicaoResponseDTO>> findByAmostra(@PathVariable String codigo) {
        log.info("GET /api/medicoes/amostra/{} - Finding medicoes for amostra", codigo);
        List<MedicaoResponseDTO> response = medicaoService.findByAmostra(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/medicoes/amostra/{codigo}/ativa - Retrieves the active measurement for a sample.
     *
     * @param codigo Sample tracking code
     * @return Active measurement with HTTP 200
     */
    @GetMapping(value = "/amostra/{codigo}/ativa", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicaoResponseDTO> findMedicaoAtiva(@PathVariable String codigo) {
        log.info("GET /api/medicoes/amostra/{}/ativa - Finding active medicao", codigo);
        MedicaoResponseDTO response = medicaoService.findMedicaoAtiva(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/medicoes/amostra/{codigo}/versao/{versao}/ativar - Activates a specific measurement version.
     *
     * @param codigo Sample tracking code
     * @param versao Version number to activate
     * @return Activated measurement with HTTP 200
     */
    @PatchMapping(value = "/amostra/{codigo}/versao/{versao}/ativar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicaoResponseDTO> ativarVersao(
            @PathVariable String codigo,
            @PathVariable Integer versao) {
        log.info("PATCH /api/medicoes/amostra/{}/versao/{}/ativar - Activating version", codigo, versao);
        MedicaoResponseDTO response = medicaoService.ativarVersao(codigo, versao);
        return ResponseEntity.ok(response);
    }
}
