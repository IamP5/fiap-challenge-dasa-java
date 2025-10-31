package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.enums.TipoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Medico entity.
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>, JpaSpecificationExecutor<Medico> {

    /**
     * Finds a doctor by CRM and UF.
     */
    Optional<Medico> findByCrmAndUfCrm(String crm, String ufCrm);

    /**
     * Finds doctors by name containing the given text (case insensitive).
     */
    List<Medico> findByNomeCompletoContainingIgnoreCase(String nome);

    /**
     * Finds doctors by specialty.
     */
    List<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade);

    /**
     * Finds doctors by type.
     */
    List<Medico> findByTipoMedico(TipoMedico tipoMedico);

    /**
     * Finds active doctors.
     */
    List<Medico> findByAtivo(Character ativo);

    /**
     * Checks if a doctor exists with the given CRM and UF.
     */
    boolean existsByCrmAndUfCrm(String crm, String ufCrm);

    /**
     * Counts doctors by type.
     */
    long countByTipoMedico(TipoMedico tipoMedico);
}
