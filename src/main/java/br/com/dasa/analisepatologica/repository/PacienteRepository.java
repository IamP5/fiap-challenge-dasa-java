package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.Paciente;
import br.com.dasa.analisepatologica.enums.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Paciente entity.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>, JpaSpecificationExecutor<Paciente> {

    /**
     * Finds a patient by CPF.
     */
    Optional<Paciente> findByCpf(String cpf);

    /**
     * Finds patients by name containing the given text (case insensitive).
     */
    List<Paciente> findByNomeCompletoContainingIgnoreCase(String nome);

    /**
     * Finds patients by gender.
     */
    List<Paciente> findBySexo(Sexo sexo);

    /**
     * Finds patients born between two dates.
     */
    List<Paciente> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);

    /**
     * Checks if a patient exists with the given CPF.
     */
    boolean existsByCpf(String cpf);

    /**
     * Finds patients with at least one sample.
     */
    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.amostras a")
    List<Paciente> findPacientesComAmostras();

    /**
     * Counts patients by gender.
     */
    long countBySexo(Sexo sexo);
}
