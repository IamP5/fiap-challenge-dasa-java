package br.com.dasa.analisepatologica.controller;

import br.com.dasa.analisepatologica.controller.openapi.ImagemAmostraControllerOpenApi;
import br.com.dasa.analisepatologica.dto.ImagemAmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.ImagemAmostraResponseDTO;
import br.com.dasa.analisepatologica.service.ImagemAmostraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Sample Image (ImagemAmostra) operations.
 * Base path: /api/imagens
 */
@RestController
@RequestMapping(value = "/api/imagens", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ImagemAmostraController implements ImagemAmostraControllerOpenApi {

    private final ImagemAmostraService imagemAmostraService;

    /**
     * POST /api/imagens/amostra/{codigo} - Creates a new image for a sample.
     *
     * @param codigo Sample tracking code
     * @param requestDTO Image data
     * @return Created image with HTTP 201
     */
    @PostMapping(value = "/amostra/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagemAmostraResponseDTO> create(
            @PathVariable String codigo,
            @Valid @RequestBody ImagemAmostraRequestDTO requestDTO) {
        log.info("POST /api/imagens/amostra/{} - Creating new imagem", codigo);
        ImagemAmostraResponseDTO response = imagemAmostraService.create(codigo, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/imagens/{id} - Retrieves an image by ID.
     *
     * @param id Image ID
     * @return Image data with HTTP 200
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagemAmostraResponseDTO> findById(@PathVariable Long id) {
        log.info("GET /api/imagens/{} - Finding imagem by ID", id);
        ImagemAmostraResponseDTO response = imagemAmostraService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/imagens/amostra/{codigo} - Retrieves all images for a sample.
     *
     * @param codigo Sample tracking code
     * @return List of images with HTTP 200
     */
    @GetMapping(value = "/amostra/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ImagemAmostraResponseDTO>> findByAmostra(@PathVariable String codigo) {
        log.info("GET /api/imagens/amostra/{} - Finding imagens for amostra", codigo);
        List<ImagemAmostraResponseDTO> response = imagemAmostraService.findByAmostra(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/imagens/amostra/{codigo}/ativas - Retrieves active images for a sample.
     *
     * @param codigo Sample tracking code
     * @return List of active images with HTTP 200
     */
    @GetMapping(value = "/amostra/{codigo}/ativas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ImagemAmostraResponseDTO>> findImagensAtivas(@PathVariable String codigo) {
        log.info("GET /api/imagens/amostra/{}/ativas - Finding active imagens", codigo);
        List<ImagemAmostraResponseDTO> response = imagemAmostraService.findImagensAtivas(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/imagens/{id} - Updates an image.
     *
     * @param id Image ID
     * @param requestDTO Updated image data
     * @return Updated image with HTTP 200
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagemAmostraResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ImagemAmostraRequestDTO requestDTO) {
        log.info("PUT /api/imagens/{} - Updating imagem", id);
        ImagemAmostraResponseDTO response = imagemAmostraService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/imagens/{id}/ativar - Activates an image.
     * Uses PATCH since we're partially updating the resource (active status only).
     *
     * @param id Image ID
     * @return Updated image with HTTP 200
     */
    @PatchMapping(value = "/{id}/ativar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagemAmostraResponseDTO> ativar(@PathVariable Long id) {
        log.info("PATCH /api/imagens/{}/ativar - Activating imagem", id);
        ImagemAmostraResponseDTO response = imagemAmostraService.ativar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/imagens/{id}/desativar - Deactivates an image.
     * Uses PATCH since we're partially updating the resource (active status only).
     *
     * @param id Image ID
     * @return Updated image with HTTP 200
     */
    @PatchMapping(value = "/{id}/desativar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagemAmostraResponseDTO> desativar(@PathVariable Long id) {
        log.info("PATCH /api/imagens/{}/desativar - Deactivating imagem", id);
        ImagemAmostraResponseDTO response = imagemAmostraService.desativar(id);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/imagens/{id} - Deletes an image.
     *
     * @param id Image ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/imagens/{} - Deleting imagem", id);
        imagemAmostraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
