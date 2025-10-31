package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.AmostraFilterDTO;
import br.com.dasa.analisepatologica.dto.AmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.AmostraResponseDTO;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
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
import java.util.Map;

/**
 * OpenAPI specification for Sample (Amostra) operations.
 */
@Tag(name = "Amostras", description = "API para gerenciamento de amostras patológicas")
public interface AmostraControllerOpenApi {

    @Operation(
            summary = "Criar nova amostra",
            description = "Cria uma nova amostra com as informações fornecidas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Amostra criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (erro de validação)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio (regra de negócio violada)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<AmostraResponseDTO> create(
            @RequestBody(description = "Dados da amostra a ser criada")
            @Valid AmostraRequestDTO requestDTO
    );

    @Operation(
            summary = "Buscar amostra por código de rastreio",
            description = "Retorna os detalhes de uma amostra específica pelo código de rastreio"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Amostra encontrada",
                    content = @Content(schema = @Schema(implementation = AmostraResponseDTO.class))
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
    ResponseEntity<AmostraResponseDTO> findByCodigoRastreio(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Listar amostras com filtros",
            description = "Retorna uma lista de amostras aplicando filtros opcionais (pacienteId, medicoId, status, tipoTecido, dataColeta, etc)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de amostras retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de filtro inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<AmostraResponseDTO>> findAll(
            @Parameter(description = "Critérios de filtro para busca de amostras")
            AmostraFilterDTO filter
    );

    @Operation(
            summary = "Atualizar status da amostra",
            description = "Atualiza apenas o status de processamento de uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AmostraResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de negócio (transição de status inválida)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<AmostraResponseDTO> updateStatus(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @RequestBody(description = "Novo status da amostra")
            Map<String, String> body
    );

    @Operation(
            summary = "Atualizar amostra",
            description = "Atualiza todos os dados de uma amostra existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Amostra atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AmostraResponseDTO.class))
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
                    description = "Erro de negócio",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<AmostraResponseDTO> update(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @RequestBody(description = "Dados atualizados da amostra")
            @Valid AmostraRequestDTO requestDTO
    );

    @Operation(
            summary = "Excluir amostra",
            description = "Remove uma amostra do sistema pelo código de rastreio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Amostra excluída com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Amostra não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Amostra não pode ser excluída (possui dependências)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Contar amostras",
            description = "Retorna a contagem de amostras com filtros opcionais (status, pacienteId, medicoId)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Long> count(
            @Parameter(description = "Status de processamento para filtro (opcional)")
            StatusProcessamento status,
            @Parameter(description = "ID do paciente para filtro (opcional)")
            Long pacienteId,
            @Parameter(description = "ID do médico para filtro (opcional)")
            Long medicoId
    );
}
