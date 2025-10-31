package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.Sexo;

import java.time.LocalDate;

/**
 * Basic patient information for nested use in other DTOs.
 * Uses Java record for immutability and conciseness.
 */
public record PacienteBasicDTO(
    Long pacienteId,
    String nomeCompleto,
    LocalDate dataNascimento,
    int idade,
    Sexo sexo,
    String cpf
) {
}
