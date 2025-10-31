package br.com.dasa.analisepatologica.entity;

import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample entity representing a pathology sample in the system.
 */
@Entity
@Table(name = "AMOSTRA", uniqueConstraints = {
    @UniqueConstraint(name = "AMOSTRA_codigo_rastreio_UN", columnNames = "codigo_rastreio")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AmostraId.class)
public class Amostra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_amostra")
    @SequenceGenerator(name = "seq_amostra", sequenceName = "SEQ_AMOSTRA", allocationSize = 1)
    @Column(name = "amostra_id", nullable = false)
    private Long amostraId;

    @Id
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Id
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @NotBlank(message = "Código de rastreio é obrigatório")
    @Size(max = 50, message = "Código de rastreio deve ter no máximo 50 caracteres")
    @Column(name = "codigo_rastreio", nullable = false, unique = true, length = 50)
    private String codigoRastreio;

    @NotBlank(message = "Tipo de tecido é obrigatório")
    @Size(max = 100, message = "Tipo de tecido deve ter no máximo 100 caracteres")
    @Column(name = "tipo_tecido", nullable = false, length = 100)
    private String tipoTecido;

    @Size(max = 200, message = "Localização anatômica deve ter no máximo 200 caracteres")
    @Column(name = "localizacao_anatomica", length = 200)
    private String localizacaoAnatomica;

    @NotNull(message = "Data de coleta é obrigatória")
    @PastOrPresent(message = "Data de coleta não pode ser futura")
    @Column(name = "data_coleta", nullable = false)
    private LocalDate dataColeta;

    @PastOrPresent(message = "Data de recebimento não pode ser futura")
    @Column(name = "data_recebimento")
    private LocalDate dataRecebimento;

    @NotNull(message = "Status de processamento é obrigatório")
    @Column(name = "status_processamento", nullable = false, length = 50)
    @Builder.Default
    private StatusProcessamento statusProcessamento = StatusProcessamento.RECEBIDA;

    @Lob
    @Column(name = "observacoes")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotBlank(message = "Criado por é obrigatório")
    @Size(max = 100, message = "Criado por deve ter no máximo 100 caracteres")
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @MapsId("pacienteId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", referencedColumnName = "paciente_id", nullable = false)
    private Paciente paciente;

    @MapsId("medicoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", referencedColumnName = "medico_id", nullable = false)
    private Medico medicoSolicitante;

    @OneToMany(mappedBy = "amostra", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Medicao> medicoes = new ArrayList<>();

    @OneToMany(mappedBy = "amostra", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ImagemAmostra> imagens = new ArrayList<>();

    @OneToOne(mappedBy = "amostra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Laudo laudo;

    /**
     * Updates the sample status.
     */
    public void atualizarStatus(StatusProcessamento novoStatus) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }

        // Validate status transitions
        if (this.statusProcessamento == StatusProcessamento.CANCELADA) {
            throw new IllegalStateException("Não é possível alterar o status de uma amostra cancelada");
        }

        if (this.statusProcessamento == StatusProcessamento.LIBERADA &&
            novoStatus != StatusProcessamento.LIBERADA) {
            throw new IllegalStateException("Não é possível alterar o status de uma amostra já liberada");
        }

        this.statusProcessamento = novoStatus;
    }

    /**
     * Adds a measurement to the sample.
     */
    public void adicionarMedicao(Medicao medicao) {
        if (medicao != null) {
            medicoes.add(medicao);
            medicao.setAmostra(this);
        }
    }

    /**
     * Adds an image to the sample.
     */
    public void adicionarImagem(ImagemAmostra imagem) {
        if (imagem != null) {
            imagens.add(imagem);
            imagem.setAmostra(this);
        }
    }

    /**
     * Checks if the sample has a report.
     */
    public boolean temLaudo() {
        return laudo != null;
    }

    /**
     * Checks if the sample is ready for analysis.
     */
    public boolean estaProntaParaAnalise() {
        return medicoes != null && !medicoes.isEmpty() &&
               imagens != null && !imagens.isEmpty();
    }

    /**
     * Gets the total number of measurements.
     */
    public int getTotalMedicoes() {
        return medicoes != null ? medicoes.size() : 0;
    }

    /**
     * Gets the total number of images.
     */
    public int getTotalImagens() {
        return imagens != null ? imagens.size() : 0;
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (dataRecebimento != null && dataColeta != null) {
            if (dataRecebimento.isBefore(dataColeta)) {
                throw new IllegalStateException("Data de recebimento não pode ser anterior à data de coleta");
            }
        }
    }
}
