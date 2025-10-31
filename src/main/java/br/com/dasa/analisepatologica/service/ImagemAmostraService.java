package br.com.dasa.analisepatologica.service;

import br.com.dasa.analisepatologica.dto.ImagemAmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.ImagemAmostraResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.ImagemAmostra;
import br.com.dasa.analisepatologica.exception.BusinessException;
import br.com.dasa.analisepatologica.exception.ResourceNotFoundException;
import br.com.dasa.analisepatologica.mapper.ImagemAmostraMapper;
import br.com.dasa.analisepatologica.repository.AmostraRepository;
import br.com.dasa.analisepatologica.repository.ImagemAmostraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing sample images (ImagemAmostra).
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImagemAmostraService {

    private final ImagemAmostraRepository imagemAmostraRepository;
    private final AmostraRepository amostraRepository;
    private final ImagemAmostraMapper imagemAmostraMapper;

    /**
     * Creates a new image for a sample.
     */
    public ImagemAmostraResponseDTO create(String codigoRastreio, ImagemAmostraRequestDTO requestDTO) {
        log.info("Creating new imagem for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        ImagemAmostra imagem = imagemAmostraMapper.toEntity(requestDTO, amostra);
        imagem.setCreatedBy("SYSTEM");

        // Validate image quality
        if (!imagem.validarQualidade()) {
            log.warn("Image quality validation failed for: {}", requestDTO.getNomeArquivo());
        }

        ImagemAmostra savedImagem = imagemAmostraRepository.save(imagem);
        log.info("ImagemAmostra created successfully with ID: {} for amostra: {}", savedImagem.getImagemId(), codigoRastreio);

        return imagemAmostraMapper.toResponseDTO(savedImagem);
    }

    /**
     * Retrieves an image by ID.
     */
    @Transactional(readOnly = true)
    public ImagemAmostraResponseDTO findById(Long id) {
        log.info("Finding imagem by ID: {}", id);
        ImagemAmostra imagem = imagemAmostraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImagemAmostra", "ID", id));
        return imagemAmostraMapper.toResponseDTO(imagem);
    }

    /**
     * Retrieves all images for a sample.
     */
    @Transactional(readOnly = true)
    public List<ImagemAmostraResponseDTO> findByAmostra(String codigoRastreio) {
        log.info("Finding imagens for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        return imagemAmostraRepository.findByAmostra(amostra)
                .stream()
                .map(imagemAmostraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves active images for a sample.
     */
    @Transactional(readOnly = true)
    public List<ImagemAmostraResponseDTO> findImagensAtivas(String codigoRastreio) {
        log.info("Finding active imagens for amostra: {}", codigoRastreio);

        Amostra amostra = amostraRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ResourceNotFoundException("Amostra", "codigo_rastreio", codigoRastreio));

        return imagemAmostraRepository.findByAmostraAndAtiva(amostra, 'S')
                .stream()
                .map(imagemAmostraMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an image.
     */
    public ImagemAmostraResponseDTO update(Long id, ImagemAmostraRequestDTO requestDTO) {
        log.info("Updating imagem with ID: {}", id);

        ImagemAmostra imagem = imagemAmostraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImagemAmostra", "ID", id));

        imagemAmostraMapper.updateEntityFromDTO(requestDTO, imagem);

        ImagemAmostra updatedImagem = imagemAmostraRepository.save(imagem);
        log.info("ImagemAmostra updated successfully with ID: {}", updatedImagem.getImagemId());

        return imagemAmostraMapper.toResponseDTO(updatedImagem);
    }

    /**
     * Activates an image.
     */
    public ImagemAmostraResponseDTO ativar(Long id) {
        log.info("Activating imagem with ID: {}", id);

        ImagemAmostra imagem = imagemAmostraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImagemAmostra", "ID", id));

        imagem.ativar();
        ImagemAmostra updatedImagem = imagemAmostraRepository.save(imagem);

        return imagemAmostraMapper.toResponseDTO(updatedImagem);
    }

    /**
     * Deactivates an image.
     */
    public ImagemAmostraResponseDTO desativar(Long id) {
        log.info("Deactivating imagem with ID: {}", id);

        ImagemAmostra imagem = imagemAmostraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImagemAmostra", "ID", id));

        imagem.desativar();
        ImagemAmostra updatedImagem = imagemAmostraRepository.save(imagem);

        return imagemAmostraMapper.toResponseDTO(updatedImagem);
    }

    /**
     * Deletes an image.
     */
    public void delete(Long id) {
        log.info("Deleting imagem with ID: {}", id);

        ImagemAmostra imagem = imagemAmostraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ImagemAmostra", "ID", id));

        imagemAmostraRepository.delete(imagem);
        log.info("ImagemAmostra deleted successfully with ID: {}", id);
    }
}
