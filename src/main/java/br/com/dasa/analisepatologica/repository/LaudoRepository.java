package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Laudo;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.enums.StatusLaudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Laudo entity.
 */
@Repository
public interface LaudoRepository extends JpaRepository<Laudo, Long> {

    /**
     * Finds report by sample.
     * Using derived query method - cleaner approach.
     */
    Optional<Laudo> findByAmostra(Amostra amostra);

    /**
     * Finds reports by status.
     * Spring Data JPA derived query method.
     */
    List<Laudo> findByStatusLaudo(StatusLaudo status);

    /**
     * Finds reports by pathologist.
     * Using derived query method with relationship navigation.
     */
    List<Laudo> findByPatologista(Medico patologista);

    /**
     * Alternative: Find by sample using composite key components (if needed).
     */
    Optional<Laudo> findByAmostra_AmostraIdAndAmostra_PacienteIdAndAmostra_MedicoId(
            Long amostraId, Long pacienteId, Long medicoId);

    /**
     * Counts reports by status.
     */
    long countByStatusLaudo(StatusLaudo status);

    /**
     * Finds reports pending review (RASCUNHO or REVISAO).
     */
    @Query("SELECT l FROM Laudo l WHERE l.statusLaudo IN ('RASCUNHO', 'REVISAO')")
    List<Laudo> findLaudosPendentesRevisao();

    /**
     * Finds reports that can be released (EMITIDO).
     */
    @Query("SELECT l FROM Laudo l WHERE l.statusLaudo = 'EMITIDO'")
    List<Laudo> findLaudosProntosParaLiberacao();
}
