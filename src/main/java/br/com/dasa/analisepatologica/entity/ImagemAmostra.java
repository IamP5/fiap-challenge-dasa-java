package br.com.dasa.analisepatologica.entity;

import br.com.dasa.analisepatologica.enums.TipoArquivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Sample image entity representing an image associated with a pathology sample.
 */
@Entity
@Table(name = "IMAGEM_AMOSTRA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemAmostra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagem")
    @SequenceGenerator(name = "seq_imagem", sequenceName = "SEQ_IMAGEM_AMOSTRA", allocationSize = 1)
    @Column(name = "imagem_id", nullable = false)
    private Long imagemId;

    @NotBlank(message = "Nome do arquivo é obrigatório")
    @Size(max = 255, message = "Nome do arquivo deve ter no máximo 255 caracteres")
    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Size(max = 500, message = "Caminho do arquivo deve ter no máximo 500 caracteres")
    @Column(name = "caminho_arquivo", length = 500)
    private String caminhoArquivo;

    @Size(max = 1000, message = "URL de acesso deve ter no máximo 1000 caracteres")
    @Column(name = "url_acesso", length = 1000)
    private String urlAcesso;

    @NotNull(message = "Tipo de arquivo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_arquivo", nullable = false, length = 10)
    private TipoArquivo tipoArquivo;

    @NotNull(message = "Tamanho do arquivo é obrigatório")
    @Min(value = 1, message = "Tamanho do arquivo deve ser maior que zero")
    @Column(name = "tamanho_bytes", nullable = false)
    private Long tamanhoBytes;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao", length = 500)
    private String descricao;

    @NotNull(message = "Data de captura é obrigatória")
    @PastOrPresent(message = "Data de captura não pode ser futura")
    @Column(name = "data_captura", nullable = false)
    @Builder.Default
    private LocalDateTime dataCaptura = LocalDateTime.now();

    @Size(max = 200, message = "Equipamento de captura deve ter no máximo 200 caracteres")
    @Column(name = "equipamento_captura", length = 200)
    private String equipamentoCaptura;

    @Size(max = 50, message = "Resolução deve ter no máximo 50 caracteres")
    @Pattern(regexp = "\\d+x\\d+", message = "Resolução deve estar no formato: larguraxaltura (ex: 1920x1080)")
    @Column(name = "resolucao", length = 50)
    private String resolucao;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(name = "amostra_id", referencedColumnName = "amostra_id", nullable = false),
        @JoinColumn(name = "paciente_id", referencedColumnName = "paciente_id", nullable = false),
        @JoinColumn(name = "medico_id", referencedColumnName = "medico_id", nullable = false)
    })
    private Amostra amostra;

    /**
     * Validates image quality based on size and resolution.
     */
    public boolean validarQualidade() {
        // Minimum size: 100KB
        if (tamanhoBytes < 100_000) {
            return false;
        }

        // Maximum size: 50MB
        if (tamanhoBytes > 50_000_000) {
            return false;
        }

        // Check resolution if available
        if (resolucao != null && !resolucao.isEmpty()) {
            String[] dimensions = resolucao.split("x");
            if (dimensions.length == 2) {
                try {
                    int width = Integer.parseInt(dimensions[0]);
                    int height = Integer.parseInt(dimensions[1]);

                    // Minimum resolution: 640x480
                    return width >= 640 && height >= 480;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the image is high resolution.
     */
    public boolean isAltaResolucao() {
        if (resolucao != null && !resolucao.isEmpty()) {
            String[] dimensions = resolucao.split("x");
            if (dimensions.length == 2) {
                try {
                    int width = Integer.parseInt(dimensions[0]);
                    int height = Integer.parseInt(dimensions[1]);

                    // High resolution: >= 1920x1080
                    return width >= 1920 && height >= 1080;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Gets formatted file size (KB, MB).
     */
    public String obterTamanhoFormatado() {
        if (tamanhoBytes == null) {
            return "0 B";
        }

        if (tamanhoBytes < 1024) {
            return tamanhoBytes + " B";
        } else if (tamanhoBytes < 1024 * 1024) {
            return String.format("%.2f KB", tamanhoBytes / 1024.0);
        } else {
            return String.format("%.2f MB", tamanhoBytes / (1024.0 * 1024.0));
        }
    }

    /**
     * Checks if this image is active.
     */
    public boolean isAtiva() {
        return ativa != null && ativa == 'S';
    }

    /**
     * Activates this image.
     */
    public void ativar() {
        this.ativa = 'S';
    }

    /**
     * Deactivates this image.
     */
    public void desativar() {
        this.ativa = 'N';
    }

    @PrePersist
    private void prePersist() {
        if (dataCaptura == null) {
            dataCaptura = LocalDateTime.now();
        }
        if (ativa == null) {
            ativa = 'S';
        }
    }
}
