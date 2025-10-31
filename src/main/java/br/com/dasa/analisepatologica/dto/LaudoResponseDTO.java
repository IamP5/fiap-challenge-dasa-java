package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.StatusLaudo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for medical report response using Java record.
 */
@Schema(description = "Resposta contendo os dados completos de um laudo médico")
public record LaudoResponseDTO(
    @Schema(description = "Identificador único do laudo", example = "1")
    Long laudoId,

    @Schema(description = "Código de rastreio da amostra associada", example = "AMOSTRA-2023-001234")
    String codigoRastreioAmostra,

    @Schema(description = "Dados básicos do patologista responsável")
    MedicoBasicDTO patologista,

    @Schema(description = "Diagnóstico principal da análise", example = "Carcinoma ductal invasivo")
    String diagnosticoPrincipal,

    @Schema(description = "Diagnósticos secundários identificados", example = "Metaplasia escamosa focal")
    String diagnosticosSecundarios,

    @Schema(description = "Conclusão do laudo médico", example = "Lesão maligna confirmada, recomenda-se tratamento oncológico")
    String conclusao,

    @Schema(description = "Recomendações do patologista", example = "Realizar imunohistoquímica complementar")
    String recomendacoes,

    @Schema(description = "Status atual do laudo")
    StatusLaudo statusLaudo,

    @Schema(description = "Data de emissão do laudo", example = "2023-10-20")
    LocalDate dataEmissao,

    @Schema(description = "Data de liberação do laudo para visualização", example = "2023-10-21")
    LocalDate dataLiberacao,

    @Schema(description = "Código CID-10 do diagnóstico", example = "C50.9")
    String codigoCid,

    @Schema(description = "Indica se o laudo está completo", example = "true")
    boolean completo,

    @Schema(description = "Indica se o laudo pode ser editado", example = "false")
    boolean podeSerEditado,

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-20T09:30:00")
    LocalDateTime createdAt,

    @Schema(description = "Data e hora da última atualização do registro", example = "2023-10-21T11:00:00")
    LocalDateTime updatedAt
) {
}
