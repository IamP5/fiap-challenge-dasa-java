package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.LaudoRequestDTO;
import br.com.dasa.analisepatologica.dto.LaudoResponseDTO;
import br.com.dasa.analisepatologica.enums.StatusLaudo;
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
 * OpenAPI specification for Medical Report (Laudo) operations.
 */
@Tag(name = "Laudos", description = "API para gerenciamento de laudos médicos")
public interface LaudoControllerOpenApi {

    @Operation(
            summary = "Criar novo laudo",
            description = "Cria um novo laudo médico para uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Laudo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
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
    ResponseEntity<LaudoResponseDTO> create(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo,
            @RequestBody(description = "Dados do laudo a ser criado")
            @Valid LaudoRequestDTO requestDTO
    );

    @Operation(
            summary = "Buscar laudo por ID",
            description = "Retorna os detalhes de um laudo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo encontrado",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<LaudoResponseDTO> findById(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Buscar laudo por amostra",
            description = "Retorna o laudo associado a uma amostra específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo encontrado",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<LaudoResponseDTO> findByAmostra(
            @Parameter(description = "Código de rastreio da amostra", required = true)
            String codigo
    );

    @Operation(
            summary = "Listar todos os laudos",
            description = "Retorna a lista completa de laudos cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de laudos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<LaudoResponseDTO>> findAll();

    @Operation(
            summary = "Buscar laudos por status",
            description = "Retorna todos os laudos com um determinado status"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de laudos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<LaudoResponseDTO>> findByStatus(
            @Parameter(description = "Status do laudo", required = true)
            StatusLaudo status
    );

    @Operation(
            summary = "Buscar laudos por patologista",
            description = "Retorna todos os laudos emitidos por um patologista específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de laudos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patologista não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<LaudoResponseDTO>> findByPatologista(
            @Parameter(description = "ID do patologista", required = true)
            Long id
    );

    @Operation(
            summary = "Buscar laudos pendentes de revisão",
            description = "Retorna todos os laudos que estão aguardando revisão"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de laudos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<LaudoResponseDTO>> findPendentesRevisao();

    @Operation(
            summary = "Buscar laudos prontos para liberação",
            description = "Retorna todos os laudos que estão prontos para serem liberados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de laudos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<List<LaudoResponseDTO>> findProntosParaLiberacao();

    @Operation(
            summary = "Atualizar laudo",
            description = "Atualiza os dados de um laudo existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
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
    ResponseEntity<LaudoResponseDTO> update(
            @Parameter(description = "ID do laudo", required = true)
            Long id,
            @RequestBody(description = "Dados atualizados do laudo")
            @Valid LaudoRequestDTO requestDTO
    );

    @Operation(
            summary = "Emitir laudo",
            description = "Marca o laudo como emitido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo emitido com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
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
    ResponseEntity<LaudoResponseDTO> emitir(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Liberar laudo",
            description = "Marca o laudo como liberado para o paciente/médico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo liberado com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
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
    ResponseEntity<LaudoResponseDTO> liberar(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Cancelar laudo",
            description = "Cancela um laudo existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo cancelado com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
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
    ResponseEntity<LaudoResponseDTO> cancelar(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Enviar laudo para revisão",
            description = "Envia o laudo para revisão por outro patologista"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laudo enviado para revisão com sucesso",
                    content = @Content(schema = @Schema(implementation = LaudoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
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
    ResponseEntity<LaudoResponseDTO> enviarParaRevisao(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Excluir laudo",
            description = "Remove um laudo do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Laudo excluído com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laudo não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Laudo não pode ser excluído",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do laudo", required = true)
            Long id
    );

    @Operation(
            summary = "Contar laudos por status",
            description = "Retorna a contagem de laudos com um determinado status"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Long> countByStatus(
            @Parameter(description = "Status do laudo", required = true)
            StatusLaudo status
    );
}
