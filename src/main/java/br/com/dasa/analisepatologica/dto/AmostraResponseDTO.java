package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for sample response using Java record.
 * Provides immutability, automatic equals/hashCode, and concise syntax.
 */
@Schema(description = "Resposta contendo os dados completos de uma amostra patológica")
public record AmostraResponseDTO(
    @Schema(description = "Identificador único da amostra", example = "1")
    Long amostraId,

    @Schema(description = "Código único de rastreamento da amostra", example = "AMOSTRA-2023-001234")
    String codigoRastreio,

    @Schema(description = "Dados básicos do paciente")
    PacienteBasicDTO paciente,

    @Schema(description = "Dados básicos do médico solicitante")
    MedicoBasicDTO medicoSolicitante,

    @Schema(description = "Tipo de tecido coletado", example = "Tecido mamário")
    String tipoTecido,

    @Schema(description = "Localização anatômica da coleta", example = "Mama esquerda, quadrante superior externo")
    String localizacaoAnatomica,

    @Schema(description = "Data da coleta da amostra", example = "2023-10-15")
    LocalDate dataColeta,

    @Schema(description = "Data de recebimento da amostra no laboratório", example = "2023-10-16")
    LocalDate dataRecebimento,

    @Schema(description = "Status atual do processamento da amostra")
    StatusProcessamento statusProcessamento,

    @Schema(description = "Observações adicionais sobre a amostra", example = "Amostra bem preservada")
    String observacoes,

    @Schema(description = "Total de medições registradas para esta amostra", example = "3")
    int totalMedicoes,

    @Schema(description = "Total de imagens associadas a esta amostra", example = "5")
    int totalImagens,

    @Schema(description = "Indica se a amostra possui laudo médico", example = "true")
    boolean temLaudo,

    @Schema(description = "Indica se a amostra está pronta para análise", example = "true")
    boolean prontaParaAnalise,

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-15T10:30:00")
    LocalDateTime createdAt,

    @Schema(description = "Data e hora da última atualização do registro", example = "2023-10-16T14:45:00")
    LocalDateTime updatedAt
) {
}
