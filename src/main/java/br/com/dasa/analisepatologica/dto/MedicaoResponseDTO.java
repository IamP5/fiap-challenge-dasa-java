package br.com.dasa.analisepatologica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for measurement response using Java record.
 */
@Schema(description = "Resposta contendo os dados completos de uma medição de amostra")
public record MedicaoResponseDTO(
    @Schema(description = "Identificador único da medição", example = "1")
    Long medicaoId,

    @Schema(description = "Código de rastreio da amostra associada", example = "AMOSTRA-2023-001234")
    String codigoRastreioAmostra,

    @Schema(description = "Largura da amostra em milímetros", example = "15.50", type = "number", format = "decimal")
    BigDecimal larguraMm,

    @Schema(description = "Altura da amostra em milímetros", example = "12.30", type = "number", format = "decimal")
    BigDecimal alturaMm,

    @Schema(description = "Profundidade da amostra em milímetros", example = "8.75", type = "number", format = "decimal")
    BigDecimal profundidadeMm,

    @Schema(description = "Volume calculado da amostra em milímetros cúbicos", example = "1668.56", type = "number", format = "decimal")
    BigDecimal volume,

    @Schema(description = "Método utilizado para medição", example = "Paquímetro digital")
    String metodoMedicao,

    @Schema(description = "Equipamento utilizado na medição", example = "Paquímetro digital Mitutoyo 500-196-30")
    String equipamentoUtilizado,

    @Schema(description = "Número da versão da medição", example = "1")
    Integer versao,

    @Schema(description = "Nome do responsável pela medição", example = "Dr. Carlos Mendes")
    String responsavelMedicao,

    @Schema(description = "Data e hora da realização da medição", example = "2023-10-16T10:15:00")
    LocalDateTime dataHoraMedicao,

    @Schema(description = "Observações sobre a medição", example = "Medição realizada com amostra em temperatura ambiente")
    String observacoes,

    @Schema(description = "Indica se esta é a versão ativa da medição", example = "true")
    boolean ativa,

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-16T10:20:00")
    LocalDateTime createdAt
) {
}
