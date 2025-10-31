package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.MedicoFilterDTO;
import br.com.dasa.analisepatologica.dto.MedicoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicoResponseDTO;
import br.com.dasa.analisepatologica.enums.TipoMedico;
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
 * OpenAPI specification for Doctor (Medico) operations.
 */
@Tag(name = "Médicos", description = "API para gerenciamento de médicos e patologistas")
public interface MedicoControllerOpenApi {

    @Operation(
            summary = "Criar novo médico",
            description = "Cadastra um novo médico ou patologista no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Médico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
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
    ResponseEntity<MedicoResponseDTO> create(
            @RequestBody(description = "Dados do médico a ser criado")
            @Valid MedicoRequestDTO requestDTO
    );

    @Operation(
            summary = "Buscar médico por ID",
            description = "Retorna os detalhes de um médico específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico encontrado",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<MedicoResponseDTO> findById(
            @Parameter(description = "ID do médico", required = true)
            Long id
    );

    @Operation(
            summary = "Buscar médico por CRM e UF",
            description = "Retorna os detalhes de um médico pelo número do CRM e UF"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico encontrado",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<MedicoResponseDTO> findByCrm(
            @Parameter(description = "Número do CRM", required = true)
            String crm,
            @Parameter(description = "UF do CRM (sigla do estado)", required = true)
            String uf
    );

    @Operation(
            summary = "Listar médicos com filtros",
            description = "Retorna uma lista de médicos aplicando filtros opcionais (nome, CRM, especialidade, tipo, ativo)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de médicos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
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
    ResponseEntity<List<MedicoResponseDTO>> findAll(
            @Parameter(description = "Critérios de filtro para busca de médicos")
            MedicoFilterDTO filter
    );

    @Operation(
            summary = "Atualizar médico",
            description = "Atualiza os dados de um médico existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
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
    ResponseEntity<MedicoResponseDTO> update(
            @Parameter(description = "ID do médico", required = true)
            Long id,
            @RequestBody(description = "Dados atualizados do médico")
            @Valid MedicoRequestDTO requestDTO
    );

    @Operation(
            summary = "Ativar médico",
            description = "Ativa um médico que estava desativado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico ativado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
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
    ResponseEntity<MedicoResponseDTO> ativar(
            @Parameter(description = "ID do médico", required = true)
            Long id
    );

    @Operation(
            summary = "Desativar médico",
            description = "Desativa um médico ativo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico desativado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
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
    ResponseEntity<MedicoResponseDTO> desativar(
            @Parameter(description = "ID do médico", required = true)
            Long id
    );

    @Operation(
            summary = "Excluir médico",
            description = "Remove um médico do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Médico não pode ser excluído",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do médico", required = true)
            Long id
    );

    @Operation(
            summary = "Contar médicos",
            description = "Retorna a contagem de médicos, opcionalmente filtrados por tipo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tipo inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Map<String, Long>> countByTipo(
            @Parameter(description = "Tipo do médico (SOLICITANTE, PATOLOGISTA) - opcional")
            TipoMedico tipo
    );
}
