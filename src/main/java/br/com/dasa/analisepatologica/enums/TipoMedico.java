package br.com.dasa.analisepatologica.enums;

import lombok.Getter;

/**
 * Enum representing the type of doctor in the system.
 */
@Getter
public enum TipoMedico {
    SOLICITANTE("SOLICITANTE", "Médico Solicitante"),
    PATOLOGISTA("PATOLOGISTA", "Patologista");

    private final String codigo;
    private final String descricao;

    TipoMedico(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoMedico fromCodigo(String codigo) {
        for (TipoMedico tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de médico inválido: " + codigo);
    }
}
