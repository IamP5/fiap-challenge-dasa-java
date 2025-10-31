package br.com.dasa.analisepatologica.enums;

import lombok.Getter;

/**
 * Enum representing the processing status of a sample.
 */
@Getter
public enum StatusProcessamento {
    RECEBIDA("RECEBIDA", "Amostra recebida no laboratório"),
    EM_PROCESSAMENTO("EM_PROCESSAMENTO", "Em processamento"),
    MEDIDA("MEDIDA", "Medições realizadas"),
    ANALISADA("ANALISADA", "Análise concluída"),
    LAUDADA("LAUDADA", "Laudo emitido"),
    LIBERADA("LIBERADA", "Liberada para consulta"),
    CANCELADA("CANCELADA", "Processamento cancelado");

    private final String nome;
    private final String descricao;

    StatusProcessamento(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public static StatusProcessamento fromNome(String nome) {
        for (StatusProcessamento status : values()) {
            if (status.nome.equals(nome)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de processamento inválido: " + nome);
    }
}
