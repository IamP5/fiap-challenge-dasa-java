package br.com.dasa.analisepatologica.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Composite primary key for Medicao entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MedicaoId implements Serializable {
    private Long medicaoId;
    private Long medicoId;
}
