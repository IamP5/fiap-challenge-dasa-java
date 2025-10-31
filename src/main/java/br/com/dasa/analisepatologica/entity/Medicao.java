package br.com.dasa.analisepatologica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Measurement entity representing dimensions of a pathology sample.
 */
@Entity
@Table(name = "MEDICAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MedicaoId.class)
public class Medicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medicao")
    @SequenceGenerator(name = "seq_medicao", sequenceName = "SEQ_MEDICAO", allocationSize = 1)
    @Column(name = "medicao_id", nullable = false)
    private Long medicaoId;

    @Id
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @NotNull(message = "Largura é obrigatória")
    @DecimalMin(value = "0.01", message = "Largura deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Largura deve ter no máximo 6 dígitos inteiros e 2 decimais")
    @Column(name = "largura_mm", nullable = false, precision = 8, scale = 2)
    private BigDecimal larguraMm;

    @NotNull(message = "Altura é obrigatória")
    @DecimalMin(value = "0.01", message = "Altura deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Altura deve ter no máximo 6 dígitos inteiros e 2 decimais")
    @Column(name = "altura_mm", nullable = false, precision = 8, scale = 2)
    private BigDecimal alturaMm;

    @DecimalMin(value = "0.01", message = "Profundidade deve ser maior que zero")
    @Digits(integer = 6, fraction = 2, message = "Profundidade deve ter no máximo 6 dígitos inteiros e 2 decimais")
    @Column(name = "profundidade_mm", precision = 8, scale = 2)
    private BigDecimal profundidadeMm;

    @NotBlank(message = "Método de medição é obrigatório")
    @Size(max = 100, message = "Método de medição deve ter no máximo 100 caracteres")
    @Column(name = "metodo_medicao", nullable = false, length = 100)
    private String metodoMedicao;

    @Size(max = 200, message = "Equipamento utilizado deve ter no máximo 200 caracteres")
    @Column(name = "equipamento_utilizado", length = 200)
    private String equipamentoUtilizado;

    @NotNull(message = "Versão é obrigatória")
    @Min(value = 1, message = "Versão deve ser maior que zero")
    @Column(name = "versao", nullable = false)
    @Builder.Default
    private Integer versao = 1;

    @NotBlank(message = "Responsável pela medição é obrigatório")
    @Size(max = 255, message = "Responsável pela medição deve ter no máximo 255 caracteres")
    @Column(name = "responsavel_medicao", nullable = false, length = 255)
    private String responsavelMedicao;

    @NotNull(message = "Data/hora da medição é obrigatória")
    @PastOrPresent(message = "Data/hora da medição não pode ser futura")
    @Column(name = "data_hora_medicao", nullable = false)
    @Builder.Default
    private LocalDateTime dataHoraMedicao = LocalDateTime.now();

    @Size(max = 100, message = "Observações devem ter no máximo 100 caracteres")
    @Column(name = "observacoes", length = 100)
    private String observacoes;

    @NotNull(message = "Status ativo é obrigatório")
    @Column(name = "ativa", nullable = false, length = 1)
    @Builder.Default
    private Character ativa = 'S';

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Size(max = 100, message = "Criado por deve ter no máximo 100 caracteres")
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "amostra_id", nullable = false)
    private Long amostraId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(name = "amostra_id", referencedColumnName = "amostra_id", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "paciente_id", referencedColumnName = "paciente_id", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "medico_id", referencedColumnName = "medico_id", nullable = false, insertable = false, updatable = false)
    })
    private Amostra amostra;

    /**
     * Calculates the volume of the sample in cubic millimeters.
     */
    public BigDecimal calcularVolume() {
        if (larguraMm == null || alturaMm == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal volume = larguraMm.multiply(alturaMm);

        if (profundidadeMm != null) {
            volume = volume.multiply(profundidadeMm);
        }

        return volume.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Checks if this measurement is active.
     */
    public boolean getAtiva() {
        return ativa != null && ativa == 'S';
    }

    /**
     * Activates this measurement.
     */
    public void ativar() {
        this.ativa = 'S';
    }

    /**
     * Deactivates this measurement.
     */
    public void desativar() {
        this.ativa = 'N';
    }

    @PrePersist
    private void prePersist() {
        if (dataHoraMedicao == null) {
            dataHoraMedicao = LocalDateTime.now();
        }
        if (versao == null) {
            versao = 1;
        }
        if (ativa == null) {
            ativa = 'S';
        }
    }
}
