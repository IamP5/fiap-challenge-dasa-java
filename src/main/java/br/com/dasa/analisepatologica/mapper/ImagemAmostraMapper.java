package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.ImagemAmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.ImagemAmostraResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.ImagemAmostra;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper for converting between ImagemAmostra entity and DTOs.
 */
@Component
public class ImagemAmostraMapper {

    public ImagemAmostra toEntity(ImagemAmostraRequestDTO dto, Amostra amostra) {
        ImagemAmostra imagem = ImagemAmostra.builder()
                .nomeArquivo(dto.getNomeArquivo())
                .caminhoArquivo(dto.getCaminhoArquivo())
                .urlAcesso(dto.getUrlAcesso())
                .tipoArquivo(dto.getTipoArquivo())
                .tamanhoBytes(dto.getTamanhoBytes())
                .descricao(dto.getDescricao())
                .equipamentoCaptura(dto.getEquipamentoCaptura())
                .resolucao(dto.getResolucao())
                .dataCaptura(dto.getDataCaptura() != null ? dto.getDataCaptura() : LocalDateTime.now())
                .ativa('S')
                .build();

        // Set relationship - JPA will derive the IDs automatically
        // from @JoinColumn mapping (insertable=false, updatable=false)
        imagem.setAmostra(amostra);

        return imagem;
    }

    public ImagemAmostraResponseDTO toResponseDTO(ImagemAmostra entity) {
        return ImagemAmostraResponseDTO.builder()
                .imagemId(entity.getImagemId())
                .codigoRastreioAmostra(entity.getAmostra() != null ? entity.getAmostra().getCodigoRastreio() : null)
                .nomeArquivo(entity.getNomeArquivo())
                .caminhoArquivo(entity.getCaminhoArquivo())
                .urlAcesso(entity.getUrlAcesso())
                .tipoArquivo(entity.getTipoArquivo())
                .tamanhoBytes(entity.getTamanhoBytes())
                .tamanhoFormatado(entity.obterTamanhoFormatado())
                .descricao(entity.getDescricao())
                .dataCaptura(entity.getDataCaptura())
                .equipamentoCaptura(entity.getEquipamentoCaptura())
                .resolucao(entity.getResolucao())
                .ativa(entity.isAtiva())
                .altaResolucao(entity.isAltaResolucao())
                .qualidadeValida(entity.validarQualidade())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public void updateEntityFromDTO(ImagemAmostraRequestDTO dto, ImagemAmostra entity) {
        entity.setNomeArquivo(dto.getNomeArquivo());
        entity.setCaminhoArquivo(dto.getCaminhoArquivo());
        entity.setUrlAcesso(dto.getUrlAcesso());
        entity.setTipoArquivo(dto.getTipoArquivo());
        entity.setTamanhoBytes(dto.getTamanhoBytes());
        entity.setDescricao(dto.getDescricao());
        entity.setEquipamentoCaptura(dto.getEquipamentoCaptura());
        entity.setResolucao(dto.getResolucao());
        if (dto.getDataCaptura() != null) {
            entity.setDataCaptura(dto.getDataCaptura());
        }
    }
}
