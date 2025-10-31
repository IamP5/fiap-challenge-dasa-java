package br.com.dasa.analisepatologica.enums;

import lombok.Getter;

/**
 * Enum representing supported file types for sample images.
 */
@Getter
public enum TipoArquivo {
    JPG("jpg", "JPEG Image"),
    JPEG("jpeg", "JPEG Image"),
    PNG("png", "PNG Image"),
    TIFF("tiff", "TIFF Image"),
    BMP("bmp", "Bitmap Image"),
    GIF("gif", "GIF Image");

    private final String extensao;
    private final String descricao;

    TipoArquivo(String extensao, String descricao) {
        this.extensao = extensao;
        this.descricao = descricao;
    }

    public static TipoArquivo fromExtensao(String extensao) {
        for (TipoArquivo tipo : values()) {
            if (tipo.extensao.equalsIgnoreCase(extensao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de arquivo inválido: " + extensao);
    }
}
