package br.com.dasa.analisepatologica.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Composite primary key for Amostra entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AmostraId implements Serializable {
    private Long amostraId;
    private Long pacienteId;
    private Long medicoId;
}
