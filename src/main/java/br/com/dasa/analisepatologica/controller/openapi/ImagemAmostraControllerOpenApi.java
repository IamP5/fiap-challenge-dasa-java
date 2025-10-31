package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.ImagemAmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.ImagemAmostraResponseDTO;
import br.com.dasa.analisepatologica.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * OpenAPI specification for Sample Image (ImagemAmostra) operations.
 */
@Tag(name = "Imagens de Amostras", description = "API para gerenciamento de imagens de amostras")
public interface ImagemAmostraControllerOpenApi {

    @Operation(
            summary = "Criar nova imagem para amostra",
            description = "Adiciona uma nova imagem a uma amostra existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Imagem criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (erro de validação)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ImagemAmostraResponseDTO> create(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @RequestBody(description = "Dados da imagem a ser criada")
            @Valid ImagemAmostraRequestDTO requestDTO
    );

    @Operation(
            summary = "Buscar imagem por ID",
            description = "Retorna os detalhes de uma imagem específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imagem encontrada",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ImagemAmostraResponseDTO> findById(
            @Parameter(description = "ID da imagem", required = true)
            Long id
    );

    @Operation(
            summary = "Listar imagens de uma amostra",
            description = "Retorna todas as imagens associadas a uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de imagens retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<ImagemAmostraResponseDTO>> findByAmostra(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Listar imagens ativas de uma amostra",
            description = "Retorna apenas as imagens ativas de uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de imagens ativas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<ImagemAmostraResponseDTO>> findImagensAtivas(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Atualizar imagem",
            description = "Atualiza os dados de uma imagem existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imagem atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (erro de validação)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ImagemAmostraResponseDTO> update(
            @Parameter(description = "ID da imagem", required = true)
            Long id,
            @RequestBody(description = "Dados atualizados da imagem")
            @Valid ImagemAmostraRequestDTO requestDTO
    );

    @Operation(
            summary = "Ativar imagem",
            description = "Ativa uma imagem que estava desativada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imagem ativada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio (imagem já está ativa)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ImagemAmostraResponseDTO> ativar(
            @Parameter(description = "ID da imagem", required = true)
            Long id
    );

    @Operation(
            summary = "Desativar imagem",
            description = "Desativa uma imagem ativa"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imagem desativada com sucesso",
                    content = @Content(schema = @Schema(implementation = ImagemAmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio (imagem já está desativada)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ImagemAmostraResponseDTO> desativar(
            @Parameter(description = "ID da imagem", required = true)
            Long id
    );

    @Operation(
            summary = "Excluir imagem",
            description = "Remove uma imagem do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagem excluída com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Imagem não pode ser excluída",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da imagem", required = true)
            Long id
    );
}
