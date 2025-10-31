package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoArquivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for sample image response.
 */
@Schema(description = "Resposta contendo os dados completos de uma imagem de amostra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemAmostraResponseDTO {
    @Schema(description = "Identificador único da imagem", example = "1")
    private Long imagemId;

    @Schema(description = "Código de rastreio da amostra associada", example = "AMOSTRA-2023-001234")
    private String codigoRastreioAmostra;

    @Schema(description = "Nome do arquivo da imagem", example = "amostra_001234_img01.tiff")
    private String nomeArquivo;

    @Schema(description = "Caminho do arquivo no sistema", example = "/storage/amostras/2023/10/amostra_001234_img01.tiff")
    private String caminhoArquivo;

    @Schema(description = "URL de acesso à imagem", example = "https://api.dasa.com/imagens/amostra_001234_img01.tiff")
    private String urlAcesso;

    @Schema(description = "Tipo do arquivo de imagem")
    private TipoArquivo tipoArquivo;

    @Schema(description = "Tamanho do arquivo em bytes", example = "15728640")
    private Long tamanhoBytes;

    @Schema(description = "Tamanho formatado do arquivo", example = "15.0 MB")
    private String tamanhoFormatado;

    @Schema(description = "Descrição da imagem", example = "Imagem microscópica da seção histológica, coloração H&E")
    private String descricao;

    @Schema(description = "Data e hora da captura da imagem", example = "2023-10-15T14:30:00")
    private LocalDateTime dataCaptura;

    @Schema(description = "Equipamento utilizado para captura", example = "Microscópio Zeiss Axio Imager")
    private String equipamentoCaptura;

    @Schema(description = "Resolução da imagem", example = "4096x3072")
    private String resolucao;

    @Schema(description = "Indica se a imagem está ativa", example = "true")
    private boolean ativa;

    @Schema(description = "Indica se é uma imagem de alta resolução", example = "true")
    private boolean altaResolucao;

    @Schema(description = "Indica se a qualidade da imagem é válida", example = "true")
    private boolean qualidadeValida;

    @Schema(description = "Data e hora de criação do registro", example = "2023-10-15T14:35:00")
    private LocalDateTime createdAt;
}
