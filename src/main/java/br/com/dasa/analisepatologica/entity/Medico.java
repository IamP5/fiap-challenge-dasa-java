package br.com.dasa.analisepatologica.entity;

import br.com.dasa.analisepatologica.enums.TipoMedico;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor entity representing a medical professional in the system.
 */
@Entity
@Table(name = "MEDICO", uniqueConstraints = {
    @UniqueConstraint(name = "MEDICO_uf_crm_crm_UN", columnNames = {"uf_crm", "crm"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medico")
    @SequenceGenerator(name = "seq_medico", sequenceName = "SEQ_MEDICO", allocationSize = 1)
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @NotBlank(message = "CRM é obrigatório")
    @Size(max = 20, message = "CRM deve ter no máximo 20 caracteres")
    @Column(name = "crm", nullable = false, length = 20)
    private String crm;

    @NotBlank(message = "UF do CRM é obrigatória")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter 2 letras maiúsculas")
    @Column(name = "uf_crm", nullable = false, length = 2, columnDefinition = "CHAR(2)")
    private String ufCrm;

    @Size(max = 100, message = "Especialidade deve ter no máximo 100 caracteres")
    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @NotNull(message = "Tipo de médico é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medico", length = 20)
    private TipoMedico tipoMedico;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "ativo", length = 1)
    @Builder.Default
    private Character ativo = 'S';

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

    @OneToMany(mappedBy = "medicoSolicitante", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Amostra> amostrasSolicitadas = new ArrayList<>();

    @OneToMany(mappedBy = "patologista", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Laudo> laudosEmitidos = new ArrayList<>();

    /**
     * Checks if the doctor is active.
     */
    public boolean isAtivo() {
        return ativo != null && ativo == 'S';
    }

    /**
     * Activates the doctor.
     */
    public void ativar() {
        this.ativo = 'S';
    }

    /**
     * Deactivates the doctor.
     */
    public void desativar() {
        this.ativo = 'N';
    }

    /**
     * Basic CRM validation (checks if it's numeric and has valid length).
     */
    public boolean validarCrm() {
        if (crm == null || crm.trim().isEmpty()) {
            return false;
        }
        // CRM should be numeric and typically between 4-8 digits
        return crm.matches("\\d{4,8}");
    }

    /**
     * Gets the total number of samples requested (for SOLICITANTE).
     */
    public int getTotalAmostrasSolicitadas() {
        return amostrasSolicitadas != null ? amostrasSolicitadas.size() : 0;
    }

    /**
     * Gets the total number of reports issued (for PATOLOGISTA).
     */
    public int getTotalLaudosEmitidos() {
        return laudosEmitidos != null ? laudosEmitidos.size() : 0;
    }
}
