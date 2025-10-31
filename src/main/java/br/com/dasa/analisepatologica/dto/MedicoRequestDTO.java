package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoMedico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO for creating or updating a doctor using Java record.
 */
@Schema(description = "Dados para criação ou atualização de um médico")
public record MedicoRequestDTO(
    @Schema(description = "Nome completo do médico",
            example = "Dr. João Carlos Oliveira",
            required = true,
            minLength = 3,
            maxLength = 255)
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    String nomeCompleto,

    @Schema(description = "Número do registro CRM do médico",
            example = "123456",
            required = true,
            maxLength = 20)
    @NotBlank(message = "CRM é obrigatório")
    @Size(max = 20, message = "CRM deve ter no máximo 20 caracteres")
    String crm,

    @Schema(description = "Unidade Federativa (estado) do CRM",
            example = "SP",
            required = true,
            pattern = "[A-Z]{2}")
    @NotBlank(message = "UF do CRM é obrigatória")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter 2 letras maiúsculas")
    String ufCrm,

    @Schema(description = "Especialidade médica",
            example = "Patologia Clínica",
            maxLength = 100)
    @Size(max = 100, message = "Especialidade deve ter no máximo 100 caracteres")
    String especialidade,

    @Schema(description = "Tipo de atuação do médico no sistema",
            example = "PATOLOGISTA",
            required = true,
            allowableValues = {"SOLICITANTE", "PATOLOGISTA"})
    @NotNull(message = "Tipo de médico é obrigatório")
    TipoMedico tipoMedico,

    @Schema(description = "Número de telefone para contato",
            example = "(11) 3456-7890",
            maxLength = 20)
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    String telefone,

    @Schema(description = "Endereço de email do médico",
            example = "dr.joao.oliveira@hospital.com.br",
            format = "email",
            maxLength = 255)
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    String email
) {
}
