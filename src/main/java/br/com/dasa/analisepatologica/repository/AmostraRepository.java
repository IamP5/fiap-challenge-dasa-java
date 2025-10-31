package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.AmostraId;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Amostra entity.
 * Extends JpaSpecificationExecutor for dynamic query support.
 */
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, AmostraId>, JpaSpecificationExecutor<Amostra> {

    /**
     * Finds a sample by its tracking code.
     */
    Optional<Amostra> findByCodigoRastreio(String codigoRastreio);

    /**
     * Checks if a sample exists with the given tracking code.
     */
    boolean existsByCodigoRastreio(String codigoRastreio);

    /**
     * Counts samples by status.
     */
    long countByStatusProcessamento(StatusProcessamento status);

    /**
     * Counts samples by patient.
     */
    long countByPacienteId(Long pacienteId);

    /**
     * Counts samples by doctor.
     */
    long countByMedicoId(Long medicoId);
}
