package br.com.dasa.analisepatologica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Audit log entity for tracking database operations.
 */
@Entity
@Table(name = "AUDIT_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_audit")
    @SequenceGenerator(name = "seq_audit", sequenceName = "SEQ_AUDIT_LOG", allocationSize = 1)
    @Column(name = "audit_id", nullable = false)
    private Long auditId;

    @NotBlank(message = "Tabela afetada é obrigatória")
    @Size(max = 100, message = "Tabela afetada deve ter no máximo 100 caracteres")
    @Column(name = "tabela_afetada", nullable = false, length = 100)
    private String tabelaAfetada;

    @NotNull(message = "ID do registro é obrigatório")
    @Column(name = "registro_id", nullable = false)
    private Long registroId;

    @NotBlank(message = "Ação é obrigatória")
    @Pattern(regexp = "INSERT|UPDATE|DELETE", message = "Ação deve ser INSERT, UPDATE ou DELETE")
    @Column(name = "acao", nullable = false, length = 20)
    private String acao;

    @Lob
    @Column(name = "valores_anteriores")
    private String valoresAnteriores;

    @Lob
    @Column(name = "valores_novos")
    private String valoresNovos;

    @NotBlank(message = "Usuário é obrigatório")
    @Size(max = 100, message = "Usuário deve ter no máximo 100 caracteres")
    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @NotNull(message = "Data/hora é obrigatória")
    @Column(name = "data_hora", nullable = false)
    @Builder.Default
    private LocalDateTime dataHora = LocalDateTime.now();

    @Size(max = 45, message = "IP de origem deve ter no máximo 45 caracteres")
    @Column(name = "ip_origem", length = 45)
    private String ipOrigem;

    @Size(max = 100, message = "Aplicação deve ter no máximo 100 caracteres")
    @Column(name = "aplicacao", length = 100)
    private String aplicacao;

    @Size(max = 255, message = "ID da sessão deve ter no máximo 255 caracteres")
    @Column(name = "sessao_id", length = 255)
    private String sessaoId;

    @PrePersist
    private void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
        if (aplicacao == null) {
            aplicacao = "ANALISE_PATOLOGICA";
        }
    }
}
