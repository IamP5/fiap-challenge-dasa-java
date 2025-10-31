package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.MedicaoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicaoResponseDTO;
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
 * OpenAPI specification for Measurement (Medicao) operations.
 */
@Tag(name = "Medições", description = "API para gerenciamento de medições de amostras")
public interface MedicaoControllerOpenApi {

    @Operation(
            summary = "Criar nova medição",
            description = "Cria uma nova medição para uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Medição criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicaoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de validação de negócio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<MedicaoResponseDTO> create(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @RequestBody(description = "Dados da medição a ser criada")
            @Valid MedicaoRequestDTO requestDTO
    );

    @Operation(
            summary = "Listar medições de uma amostra",
            description = "Retorna todas as medições (versões) associadas a uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de medições retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicaoResponseDTO.class))
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
    ResponseEntity<List<MedicaoResponseDTO>> findByAmostra(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Buscar medição ativa",
            description = "Retorna a medição ativa (versão atual) de uma amostra"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Medição ativa encontrada",
                    content = @Content(schema = @Schema(implementation = MedicaoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra ou medição não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<MedicaoResponseDTO> findMedicaoAtiva(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Ativar versão de medição",
            description = "Ativa uma versão específica de medição, tornando-a a versão corrente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Versão ativada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicaoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra ou versão não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de validação de negócio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<MedicaoResponseDTO> ativarVersao(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @Parameter(description = "Número da versão a ser ativada", required = true)
            Integer versao
    );
}
