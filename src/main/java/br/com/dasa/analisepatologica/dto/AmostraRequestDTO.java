package br.com.dasa.analisepatologica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO for creating or updating a sample using Java record.
 * Records work seamlessly with Bean Validation annotations.
 */
@Schema(description = "Dados para criação ou atualização de uma amostra patológica")
public record AmostraRequestDTO(
    @Schema(description = "Código único de rastreamento da amostra",
            example = "AMST-2024-001234",
            required = true,
            maxLength = 50)
    @NotBlank(message = "Código de rastreio é obrigatório")
    @Size(max = 50, message = "Código de rastreio deve ter no máximo 50 caracteres")
    String codigoRastreio,

    @Schema(description = "Identificador do paciente ao qual a amostra pertence",
            example = "1",
            required = true)
    @NotNull(message = "ID do paciente é obrigatório")
    Long pacienteId,

    @Schema(description = "Identificador do médico que solicitou a análise",
            example = "2",
            required = true)
    @NotNull(message = "ID do médico solicitante é obrigatório")
    Long medicoSolicitanteId,

    @Schema(description = "Tipo de tecido coletado para análise",
            example = "Tecido mamário",
            required = true,
            maxLength = 100)
    @NotBlank(message = "Tipo de tecido é obrigatório")
    @Size(max = 100, message = "Tipo de tecido deve ter no máximo 100 caracteres")
    String tipoTecido,

    @Schema(description = "Localização anatômica de onde a amostra foi coletada",
            example = "Mama esquerda, quadrante superior externo",
            maxLength = 200)
    @Size(max = 200, message = "Localização anatômica deve ter no máximo 200 caracteres")
    String localizacaoAnatomica,

    @Schema(description = "Data em que a amostra foi coletada do paciente",
            example = "2024-01-15",
            required = true,
            type = "string",
            format = "date")
    @NotNull(message = "Data de coleta é obrigatória")
    @PastOrPresent(message = "Data de coleta não pode ser futura")
    LocalDate dataColeta,

    @Schema(description = "Data em que a amostra foi recebida no laboratório",
            example = "2024-01-16",
            type = "string",
            format = "date")
    @PastOrPresent(message = "Data de recebimento não pode ser futura")
    LocalDate dataRecebimento,

    @Schema(description = "Observações adicionais sobre a amostra",
            example = "Amostra coletada em bom estado de conservação")
    String observacoes
) {
}
