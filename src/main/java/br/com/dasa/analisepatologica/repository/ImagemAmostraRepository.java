package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.ImagemAmostra;
import br.com.dasa.analisepatologica.enums.TipoArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ImagemAmostra entity.
 */
@Repository
public interface ImagemAmostraRepository extends JpaRepository<ImagemAmostra, Long> {

    /**
     * Finds all images for a sample.
     * Using derived query method - Spring Data JPA automatically generates the query.
     */
    List<ImagemAmostra> findByAmostra(Amostra amostra);

    /**
     * Finds active images for a sample.
     * Using derived query method with nested property.
     */
    List<ImagemAmostra> findByAmostraAndAtiva(Amostra amostra, Character ativa);

    /**
     * Finds all images for a sample by composite key components.
     * Useful when you have the IDs but not the entity.
     */
    List<ImagemAmostra> findByAmostra_AmostraIdAndAmostra_PacienteIdAndAmostra_MedicoId(
            Long amostraId, Long pacienteId, Long medicoId);

    /**
     * Finds active images for a sample by composite key components.
     */
    List<ImagemAmostra> findByAmostra_AmostraIdAndAmostra_PacienteIdAndAmostra_MedicoIdAndAtiva(
            Long amostraId, Long pacienteId, Long medicoId, Character ativa);
}
