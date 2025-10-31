package br.com.dasa.analisepatologica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a measurement using Java record.
 */
@Schema(description = "Dados para criação ou atualização de medições de uma amostra")
public record MedicaoRequestDTO(
    @Schema(description = "Largura da amostra em milímetros",
            example = "15.50",
            required = true,
            minimum = "0.01",
            type = "number",
            format = "decimal")
    @NotNull(message = "Largura é obrigatória")
    @DecimalMin(value = "0.01", message = "Largura deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Largura deve ter no máximo 6 dígitos inteiros e 2 decimais")
    BigDecimal larguraMm,

    @Schema(description = "Altura da amostra em milímetros",
            example = "12.30",
            required = true,
            minimum = "0.01",
            type = "number",
            format = "decimal")
    @NotNull(message = "Altura é obrigatória")
    @DecimalMin(value = "0.01", message = "Altura deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Altura deve ter no máximo 6 dígitos inteiros e 2 decimais")
    BigDecimal alturaMm,

    @Schema(description = "Profundidade da amostra em milímetros",
            example = "8.75",
            minimum = "0.01",
            type = "number",
            format = "decimal")
    @DecimalMin(value = "0.01", message = "Profundidade deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Profundidade deve ter no máximo 6 dígitos inteiros e 2 decimais")
    BigDecimal profundidadeMm,

    @Schema(description = "Método utilizado para realizar a medição",
            example = "Paquímetro digital",
            required = true,
            maxLength = 100)
    @NotBlank(message = "Método de medição é obrigatório")
    @Size(max = 100, message = "Método de medição deve ter no máximo 100 caracteres")
    String metodoMedicao,

    @Schema(description = "Nome ou modelo do equipamento utilizado",
            example = "Paquímetro Digital Mitutoyo CD-6 CSX",
            maxLength = 200)
    @Size(max = 200, message = "Equipamento utilizado deve ter no máximo 200 caracteres")
    String equipamentoUtilizado,

    @Schema(description = "Nome do profissional responsável pela medição",
            example = "Dr. Roberto Ferreira",
            required = true,
            maxLength = 255)
    @NotBlank(message = "Responsável pela medição é obrigatório")
    @Size(max = 255, message = "Responsável pela medição deve ter no máximo 255 caracteres")
    String responsavelMedicao,

    @Schema(description = "Observações adicionais sobre a medição",
            example = "Medição realizada com amostra em temperatura ambiente",
            maxLength = 100)
    @Size(max = 100, message = "Observações devem ter no máximo 100 caracteres")
    String observacoes
) {
}
