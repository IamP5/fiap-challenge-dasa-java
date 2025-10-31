package br.com.dasa.analisepatologica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO for creating or updating a medical report using Java record.
 */
@Schema(description = "Dados para criação ou atualização de um laudo patológico")
public record LaudoRequestDTO(
    @Schema(description = "Identificador do médico patologista responsável pelo laudo",
            example = "3",
            required = true)
    @NotNull(message = "ID do patologista é obrigatório")
    Long patologistaId,

    @Schema(description = "Diagnóstico principal identificado na análise patológica",
            example = "Carcinoma ductal invasivo, grau histológico II",
            required = true)
    @NotBlank(message = "Diagnóstico principal é obrigatório")
    String diagnosticoPrincipal,

    @Schema(description = "Diagnósticos secundários ou achados adicionais",
            example = "Presença de alterações fibrocísticas adjacentes")
    String diagnosticosSecundarios,

    @Schema(description = "Conclusão geral do laudo patológico",
            example = "O exame histopatológico confirma a presença de neoplasia maligna")
    String conclusao,

    @Schema(description = "Recomendações para seguimento clínico ou exames complementares",
            example = "Recomenda-se imunohistoquímica para caracterização do perfil molecular")
    String recomendacoes,

    @Schema(description = "Código da Classificação Internacional de Doenças (CID-10)",
            example = "C50.9",
            required = true,
            maxLength = 20)
    @NotBlank(message = "Código CID é obrigatório")
    @Size(max = 20, message = "Código CID deve ter no máximo 20 caracteres")
    String codigoCid
) {
}
