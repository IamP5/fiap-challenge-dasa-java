package br.com.dasa.analisepatologica.enums;

import lombok.Getter;

/**
 * Enum representing biological sex/gender.
 */
@Getter
public enum Sexo {
    MASCULINO('M', "Masculino"),
    FEMININO('F', "Feminino"),
    OUTRO('O', "Outro");

    private final char codigo;
    private final String descricao;

    Sexo(char codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Sexo fromCodigo(char codigo) {
        for (Sexo sexo : values()) {
            if (sexo.codigo == codigo) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Código de sexo inválido: " + codigo);
    }
}
