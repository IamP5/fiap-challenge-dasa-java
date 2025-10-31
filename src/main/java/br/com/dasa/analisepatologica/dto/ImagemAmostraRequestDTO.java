package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.TipoArquivo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for creating or updating a sample image.
 */
@Schema(description = "Dados para criação ou atualização de uma imagem de amostra patológica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemAmostraRequestDTO {

    @Schema(description = "Nome do arquivo de imagem",
            example = "amostra_mama_20240115_001.jpg",
            required = true,
            maxLength = 255)
    @NotBlank(message = "Nome do arquivo é obrigatório")
    @Size(max = 255, message = "Nome do arquivo deve ter no máximo 255 caracteres")
    private String nomeArquivo;

    @Schema(description = "Caminho do arquivo no sistema de armazenamento",
            example = "/storage/amostras/2024/01/amostra_mama_20240115_001.jpg",
            maxLength = 500)
    @Size(max = 500, message = "Caminho do arquivo deve ter no máximo 500 caracteres")
    private String caminhoArquivo;

    @Schema(description = "URL para acesso direto à imagem",
            example = "https://storage.dasa.com.br/amostras/2024/01/amostra_mama_20240115_001.jpg",
            maxLength = 1000)
    @Size(max = 1000, message = "URL de acesso deve ter no máximo 1000 caracteres")
    private String urlAcesso;

    @Schema(description = "Tipo de arquivo da imagem",
            example = "JPEG",
            required = true,
            allowableValues = {"JPEG", "PNG", "TIFF", "DICOM"})
    @NotNull(message = "Tipo de arquivo é obrigatório")
    private TipoArquivo tipoArquivo;

    @Schema(description = "Tamanho do arquivo em bytes",
            example = "2548736",
            required = true,
            minimum = "1")
    @NotNull(message = "Tamanho do arquivo é obrigatório")
    @Min(value = 1, message = "Tamanho do arquivo deve ser maior que zero")
    private Long tamanhoBytes;

    @Schema(description = "Descrição ou observações sobre a imagem",
            example = "Imagem microscópica com coloração H&E, aumento de 40x",
            maxLength = 500)
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Schema(description = "Nome do equipamento utilizado para captura da imagem",
            example = "Microscópio Olympus BX53",
            maxLength = 200)
    @Size(max = 200, message = "Equipamento de captura deve ter no máximo 200 caracteres")
    private String equipamentoCaptura;

    @Schema(description = "Resolução da imagem no formato larguraxaltura",
            example = "1920x1080",
            maxLength = 50,
            pattern = "\\d+x\\d+")
    @Size(max = 50, message = "Resolução deve ter no máximo 50 caracteres")
    @Pattern(regexp = "\\d+x\\d+", message = "Resolução deve estar no formato: larguraxaltura (ex: 1920x1080)")
    private String resolucao;

    @Schema(description = "Data e hora em que a imagem foi capturada",
            example = "2024-01-15T14:30:00",
            required = true,
            type = "string",
            format = "date-time")
    @NotNull(message = "Data de captura é obrigatória")
    @PastOrPresent(message = "Data de captura não pode ser futura")
    private LocalDateTime dataCaptura;
}
