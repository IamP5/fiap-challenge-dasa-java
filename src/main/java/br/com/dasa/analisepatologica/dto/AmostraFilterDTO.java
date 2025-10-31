package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO for filtering samples with multiple criteria using Java record.
 */
@Schema(description = "Critérios de filtro para busca de amostras")
public record AmostraFilterDTO(
    @Schema(description = "ID do paciente para filtrar amostras", example = "1")
    Long pacienteId,

    @Schema(description = "ID do médico solicitante para filtrar amostras", example = "2")
    Long medicoId,

    @Schema(description = "Status de processamento da amostra")
    StatusProcessamento status,

    @Schema(description = "Tipo de tecido para filtrar", example = "Tecido mamário")
    String tipoTecido,

    @Schema(description = "Data inicial da coleta para filtro de período", example = "2023-10-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataColetaInicio,

    @Schema(description = "Data final da coleta para filtro de período", example = "2023-10-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataColetaFim,

    @Schema(description = "Filtrar apenas amostras prontas para análise", example = "true")
    Boolean prontaParaAnalise,

    @Schema(description = "Filtrar apenas amostras sem laudo", example = "false")
    Boolean semLaudo
) {
    /**
     * Checks if any filter criteria is set.
     */
    public boolean hasAnyFilter() {
        return pacienteId != null
            || medicoId != null
            || status != null
            || tipoTecido != null
            || dataColetaInicio != null
            || dataColetaFim != null
            || prontaParaAnalise != null
            || semLaudo != null;
    }
}
