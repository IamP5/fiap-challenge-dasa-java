package br.com.dasa.analisepatologica.enums;

import lombok.Getter;

/**
 * Enum representing the status of a medical report (laudo).
 */
@Getter
public enum StatusLaudo {
    RASCUNHO("RASCUNHO", "Laudo em elaboração"),
    REVISAO("REVISAO", "Em revisão"),
    EMITIDO("EMITIDO", "Laudo emitido"),
    LIBERADO("LIBERADO", "Liberado para consulta"),
    CANCELADO("CANCELADO", "Laudo cancelado");

    private final String codigo;
    private final String descricao;

    StatusLaudo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusLaudo fromCodigo(String codigo) {
        for (StatusLaudo status : values()) {
            if (status.codigo.equals(codigo)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de laudo inválido: " + codigo);
    }
}
