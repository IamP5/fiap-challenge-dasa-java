package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for patient response using Java record.
 */
@Schema(description = "Resposta contendo os dados completos de um paciente")
public record PacienteResponseDTO(
    @Schema(description = "Identificador único do paciente", example = "1")
    Long pacienteId,

    @Schema(description = "Nome completo do paciente", example = "Maria da Silva Santos")
    String nomeCompleto,

    @Schema(description = "Data de nascimento do paciente", example = "1985-03-15")
    LocalDate dataNascimento,

    @Schema(description = "Idade calculada do paciente em anos", example = "38")
    int idade,

    @Schema(description = "Sexo do paciente")
    Sexo sexo,

    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    String cpf,

    @Schema(description = "Telefone de contato do paciente", example = "(11) 98765-4321")
    String telefone,

    @Schema(description = "E-mail do paciente", example = "maria.silva@email.com")
    String email,

    @Schema(description = "Endereço completo do paciente", example = "Rua das Flores, 123, Apto 45, Centro, São Paulo - SP, 01234-567")
    String enderecoCompleto,

    @Schema(description = "Total de amostras coletadas do paciente", example = "2")
    int totalAmostras,

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-15T10:30:00")
    LocalDateTime createdAt,

    @Schema(description = "Data e hora da última atualização do registro", example = "2023-10-16T14:45:00")
    LocalDateTime updatedAt
) {
}
