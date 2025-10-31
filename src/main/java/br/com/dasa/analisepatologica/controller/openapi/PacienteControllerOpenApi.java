package br.com.dasa.analisepatologica.controller.openapi;

import br.com.dasa.analisepatologica.dto.PacienteFilterDTO;
import br.com.dasa.analisepatologica.dto.PacienteRequestDTO;
import br.com.dasa.analisepatologica.dto.PacienteResponseDTO;
import br.com.dasa.analisepatologica.enums.Sexo;
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
 * OpenAPI specification for Patient (Paciente) operations.
 */
@Tag(name = "Pacientes", description = "API para gerenciamento de pacientes")
public interface PacienteControllerOpenApi {

    @Operation(
            summary = "Criar novo paciente",
            description = "Cadastra um novo paciente no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponseDTO.class))
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
    ResponseEntity<PacienteResponseDTO> create(
            @RequestBody(description = "Dados do paciente a ser criado")
            @Valid PacienteRequestDTO requestDTO
    );

    @Operation(
            summary = "Buscar paciente por ID",
            description = "Retorna os detalhes de um paciente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado",
                    content = @Content(schema = @Schema(implementation = PacienteResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<PacienteResponseDTO> findById(
            @Parameter(description = "ID do paciente", required = true)
            Long id
    );

    @Operation(
            summary = "Buscar paciente por CPF",
            description = "Retorna os detalhes de um paciente pelo número do CPF"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado",
                    content = @Content(schema = @Schema(implementation = PacienteResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<PacienteResponseDTO> findByCpf(
            @Parameter(description = "CPF do paciente", required = true)
            String cpf
    );

    @Operation(
            summary = "Listar pacientes com filtros",
            description = "Retorna uma lista de pacientes aplicando filtros opcionais (nome, CPF, sexo, data nascimento, com amostras)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pacientes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponseDTO.class))
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
    ResponseEntity<List<PacienteResponseDTO>> findAll(
            @Parameter(description = "Critérios de filtro para busca de pacientes")
            PacienteFilterDTO filter
    );

    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza os dados de um paciente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
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
    ResponseEntity<PacienteResponseDTO> update(
            @Parameter(description = "ID do paciente", required = true)
            Long id,
            @RequestBody(description = "Dados atualizados do paciente")
            @Valid PacienteRequestDTO requestDTO
    );

    @Operation(
            summary = "Excluir paciente",
            description = "Remove um paciente do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Paciente não pode ser excluído",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do paciente", required = true)
            Long id
    );

    @Operation(
            summary = "Contar pacientes",
            description = "Retorna a contagem de pacientes, opcionalmente filtrados por sexo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Sexo inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Map<String, Long>> countBySexo(
            @Parameter(description = "Sexo do paciente (MASCULINO, FEMININO, OUTRO) - opcional")
            Sexo sexo
    );
}
