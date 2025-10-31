package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoMedico;

/**
 * Basic doctor information for nested use in other DTOs.
 * Uses Java record for immutability and conciseness.
 */
public record MedicoBasicDTO(
    Long medicoId,
    String nomeCompleto,
    String crm,
    String ufCrm,
    String especialidade,
    TipoMedico tipoMedico
) {
}
