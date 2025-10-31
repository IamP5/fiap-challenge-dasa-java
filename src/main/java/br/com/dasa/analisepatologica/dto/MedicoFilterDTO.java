package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoMedico;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for filtering doctors using Java record.
 */
@Schema(description = "Critérios de filtro para busca de médicos")
public record MedicoFilterDTO(
    @Schema(description = "Nome do médico (busca parcial, case insensitive)", example = "João Carlos")
    String nome,

    @Schema(description = "Número do CRM", example = "123456")
    String crm,

    @Schema(description = "UF do CRM (sigla do estado)", example = "SP")
    String ufCrm,

    @Schema(description = "Especialidade médica (busca parcial, case insensitive)", example = "Patologia")
    String especialidade,

    @Schema(description = "Tipo do médico (SOLICITANTE ou PATOLOGISTA)")
    TipoMedico tipo,

    @Schema(description = "Filtrar apenas médicos ativos no sistema", example = "true")
    Boolean ativo
) {
    public boolean hasAnyFilter() {
        return nome != null || crm != null || ufCrm != null
            || especialidade != null || tipo != null || ativo != null;
    }
}
