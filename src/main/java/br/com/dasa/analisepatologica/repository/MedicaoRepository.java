package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Medicao;
import br.com.dasa.analisepatologica.entity.MedicaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Medicao entity.
 */
@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, MedicaoId> {

    /**
     * Finds all measurements for a sample.
     * Using derived query method - cleaner and more maintainable.
     */
    List<Medicao> findByAmostra(Amostra amostra);

    /**
     * Finds active measurement for a sample.
     */
    Optional<Medicao> findByAmostraAndAtiva(Amostra amostra, Character ativa);

    /**
     * Finds all measurements for a specific sample ordered by version descending.
     */
    List<Medicao> findByAmostraOrderByVersaoDesc(Amostra amostra);

    /**
     * Gets the maximum version number for a sample.
     * Custom query is justified here for aggregation function.
     */
    @Query("SELECT MAX(m.versao) FROM Medicao m WHERE m.amostra = :amostra")
    Integer findMaxVersaoByAmostra(@Param("amostra") Amostra amostra);

    /**
     * Alternative methods using composite key components (if needed when you don't have the entity).
     */
    List<Medicao> findByAmostra_AmostraIdAndAmostra_PacienteIdAndMedicoId(
            Long amostraId, Long pacienteId, Long medicoId);

    List<Medicao> findByAmostra_AmostraIdAndAmostra_PacienteIdAndMedicoIdOrderByVersaoDesc(
            Long amostraId, Long pacienteId, Long medicoId);

    @Query("SELECT MAX(m.versao) FROM Medicao m WHERE m.amostra.amostraId = :amostraId AND m.amostra.pacienteId = :pacienteId AND m.medicoId = :medicoId")
    Integer findMaxVersaoByAmostraComposta(@Param("amostraId") Long amostraId,
                                           @Param("pacienteId") Long pacienteId,
                                           @Param("medicoId") Long medicoId);
}
