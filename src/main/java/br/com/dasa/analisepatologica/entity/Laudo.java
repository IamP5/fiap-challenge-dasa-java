package br.com.dasa.analisepatologica.entity;

import br.com.dasa.analisepatologica.enums.StatusLaudo;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Medical report entity representing a pathology report.
 */
@Entity
@Table(name = "LAUDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laudo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_laudo")
    @SequenceGenerator(name = "seq_laudo", sequenceName = "SEQ_LAUDO", allocationSize = 1)
    @Column(name = "laudo_id", nullable = false)
    private Long laudoId;

    @NotBlank(message = "Diagnóstico principal é obrigatório")
    @Lob
    @Column(name = "diagnostico_principal", nullable = false)
    private String diagnosticoPrincipal;

    @Lob
    @Column(name = "diagnosticos_secundarios")
    private String diagnosticosSecundarios;

    @Lob
    @Column(name = "conclusao")
    private String conclusao;

    @Lob
    @Column(name = "recomendacoes")
    private String recomendacoes;

    @NotNull(message = "Status do laudo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_laudo", nullable = false, length = 50)
    @Builder.Default
    private StatusLaudo statusLaudo = StatusLaudo.RASCUNHO;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "data_liberacao")
    private LocalDate dataLiberacao;

    @NotBlank(message = "Código CID é obrigatório")
    @Size(max = 20, message = "Código CID deve ter no máximo 20 caracteres")
    @Column(name = "codigo_cid", nullable = false, length = 20)
    private String codigoCid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Size(max = 100, message = "Criado por deve ter no máximo 100 caracteres")
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(name = "amostra_id", referencedColumnName = "amostra_id", nullable = false),
        @JoinColumn(name = "paciente_id", referencedColumnName = "paciente_id", nullable = false),
        @JoinColumn(name = "medico_id", referencedColumnName = "medico_id", nullable = false)
    })
    private Amostra amostra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", referencedColumnName = "medico_id", insertable = false, updatable = false)
    private Medico patologista;

    /**
     * Emits the report, changing its status from RASCUNHO to EMITIDO.
     */
    public void emitir() {
        if (statusLaudo != StatusLaudo.RASCUNHO && statusLaudo != StatusLaudo.REVISAO) {
            throw new IllegalStateException("Apenas laudos em rascunho ou revisão podem ser emitidos");
        }

        this.statusLaudo = StatusLaudo.EMITIDO;
        this.dataEmissao = LocalDate.now();

        if (amostra != null) {
            amostra.atualizarStatus(StatusProcessamento.LAUDADA);
        }
    }

    /**
     * Releases the report for consultation.
     */
    public void liberar() {
        if (statusLaudo != StatusLaudo.EMITIDO) {
            throw new IllegalStateException("Apenas laudos emitidos podem ser liberados");
        }

        this.statusLaudo = StatusLaudo.LIBERADO;
        this.dataLiberacao = LocalDate.now();

        if (amostra != null) {
            amostra.atualizarStatus(StatusProcessamento.LIBERADA);
        }
    }

    /**
     * Cancels the report.
     */
    public void cancelar() {
        if (statusLaudo == StatusLaudo.LIBERADO) {
            throw new IllegalStateException("Laudos liberados não podem ser cancelados");
        }

        this.statusLaudo = StatusLaudo.CANCELADO;

        if (amostra != null) {
            amostra.atualizarStatus(StatusProcessamento.CANCELADA);
        }
    }

    /**
     * Sends the report back to review.
     */
    public void enviarParaRevisao() {
        if (statusLaudo != StatusLaudo.RASCUNHO) {
            throw new IllegalStateException("Apenas laudos em rascunho podem ser enviados para revisão");
        }

        this.statusLaudo = StatusLaudo.REVISAO;
    }

    /**
     * Checks if the report is complete (has all required fields).
     */
    public boolean isCompleto() {
        return diagnosticoPrincipal != null && !diagnosticoPrincipal.trim().isEmpty() &&
               conclusao != null && !conclusao.trim().isEmpty() &&
               codigoCid != null && !codigoCid.trim().isEmpty();
    }

    /**
     * Checks if the report can be edited.
     */
    public boolean podeSerEditado() {
        return statusLaudo == StatusLaudo.RASCUNHO || statusLaudo == StatusLaudo.REVISAO;
    }

    @PreUpdate
    private void preUpdate() {
        // Ensure dates are consistent
        if (dataLiberacao != null && dataEmissao != null) {
            if (dataLiberacao.isBefore(dataEmissao)) {
                throw new IllegalStateException("Data de liberação não pode ser anterior à data de emissão");
            }
        }
    }
}
