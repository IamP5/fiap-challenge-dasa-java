package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoMedico;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO for doctor response using Java record.
 */
@Schema(description = "Resposta contendo os dados completos de um médico ou patologista")
public record MedicoResponseDTO(
    @Schema(description = "Identificador único do médico", example = "1")
    Long medicoId,

    @Schema(description = "Nome completo do médico", example = "Dr. João Carlos Oliveira")
    String nomeCompleto,

    @Schema(description = "Número do CRM do médico", example = "123456")
    String crm,

    @Schema(description = "UF do CRM (sigla do estado)", example = "SP")
    String ufCrm,

    @Schema(description = "Especialidade médica", example = "Patologia")
    String especialidade,

    @Schema(description = "Tipo do médico (SOLICITANTE ou PATOLOGISTA)")
    TipoMedico tipoMedico,

    @Schema(description = "Telefone de contato do médico", example = "(11) 3456-7890")
    String telefone,

    @Schema(description = "E-mail do médico", example = "dr.joao@hospital.com")
    String email,

    @Schema(description = "Indica se o médico está ativo no sistema", example = "true")
    boolean ativo,

    @Schema(description = "Total de amostras solicitadas pelo médico", example = "15")
    int totalAmostrasSolicitadas,

    @Schema(description = "Total de laudos emitidos pelo patologista", example = "8")
    int totalLaudosEmitidos,

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-15T10:30:00")
    LocalDateTime createdAt,

    @Schema(description = "Data e hora da última atualização do registro", example = "2023-10-16T14:45:00")
    LocalDateTime updatedAt
) {
}
