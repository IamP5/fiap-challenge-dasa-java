package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO for creating or updating a patient using Java record.
 */
@Schema(description = "Dados para criação ou atualização de um paciente")
public record PacienteRequestDTO(
    @Schema(description = "Nome completo do paciente",
            example = "Maria da Silva Santos",
            required = true,
            minLength = 3,
            maxLength = 255)
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    String nomeCompleto,

    @Schema(description = "Data de nascimento do paciente",
            example = "1985-03-20",
            required = true,
            type = "string",
            format = "date")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    LocalDate dataNascimento,

    @Schema(description = "Sexo biológico do paciente",
            example = "FEMININO",
            required = true,
            allowableValues = {"MASCULINO", "FEMININO"})
    @NotNull(message = "Sexo é obrigatório")
    Sexo sexo,

    @Schema(description = "Número do CPF do paciente (somente dígitos)",
            example = "12345678901",
            pattern = "\\d{11}")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    String cpf,

    @Schema(description = "Número de telefone para contato",
            example = "(11) 98765-4321",
            maxLength = 20)
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    String telefone,

    @Schema(description = "Endereço de email do paciente",
            example = "maria.santos@email.com",
            format = "email",
            maxLength = 255)
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    String email,

    @Schema(description = "Endereço completo do paciente",
            example = "Rua das Flores, 123, Apto 45, Jardim Primavera, São Paulo - SP, 01234-567",
            maxLength = 500)
    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    String enderecoCompleto
) {
}
