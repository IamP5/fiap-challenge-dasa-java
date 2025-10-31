package br.com.dasa.analisepatologica.repository.specification;

import br.com.dasa.analisepatologica.dto.AmostraFilterDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification builder for dynamic Amostra queries.
 * Provides type-safe, database-level filtering without loading all data into memory.
 */
public class AmostraSpecification {

    private AmostraSpecification() {
        // Private constructor to prevent instantiation
    }

    /**
     * Builds a Specification from AmostraFilterDTO.
     * Combines all filter criteria with AND logic.
     *
     * @param filter Filter criteria
     * @return Specification for querying Amostra entities
     */
    public static Specification<Amostra> buildSpecification(AmostraFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by patient ID
            if (filter.pacienteId() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("paciente").get("id"),
                    filter.pacienteId()
                ));
            }

            // Filter by doctor ID
            if (filter.medicoId() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("medicoSolicitante").get("id"),
                    filter.medicoId()
                ));
            }

            // Filter by status
            if (filter.status() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("statusProcessamento"),
                    filter.status()
                ));
            }

            // Filter by tissue type (partial match, case-insensitive)
            if (filter.tipoTecido() != null) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("tipoTecido")),
                    "%" + filter.tipoTecido().toLowerCase() + "%"
                ));
            }

            // Filter by collection date range
            if (filter.dataColetaInicio() != null && filter.dataColetaFim() != null) {
                predicates.add(criteriaBuilder.between(
                    root.get("dataColeta"),
                    filter.dataColetaInicio(),
                    filter.dataColetaFim()
                ));
            } else if (filter.dataColetaInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("dataColeta"),
                    filter.dataColetaInicio()
                ));
            } else if (filter.dataColetaFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("dataColeta"),
                    filter.dataColetaFim()
                ));
            }

            // Filter samples ready for analysis (has measurements and images)
            if (Boolean.TRUE.equals(filter.prontaParaAnalise())) {
                predicates.add(criteriaBuilder.and(
                    criteriaBuilder.isNotEmpty(root.get("medicoes")),
                    criteriaBuilder.isNotEmpty(root.get("imagens"))
                ));
            }

            // Filter samples without report
            if (Boolean.TRUE.equals(filter.semLaudo())) {
                predicates.add(criteriaBuilder.isNull(root.get("laudo")));
            }

            // Combine all predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Creates a Specification to find samples by patient ID.
     */
    public static Specification<Amostra> byPacienteId(Long pacienteId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("paciente").get("id"), pacienteId);
    }

    /**
     * Creates a Specification to find samples by doctor ID.
     */
    public static Specification<Amostra> byMedicoId(Long medicoId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("medicoSolicitante").get("id"), medicoId);
    }

    /**
     * Creates a Specification to find samples by status.
     */
    public static Specification<Amostra> byStatus(StatusProcessamento status) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("statusProcessamento"), status);
    }

    /**
     * Creates a Specification to find samples by tissue type.
     */
    public static Specification<Amostra> byTipoTecido(String tipoTecido) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("tipoTecido")),
                "%" + tipoTecido.toLowerCase() + "%"
            );
    }

    /**
     * Creates a Specification to find samples by collection date range.
     */
    public static Specification<Amostra> byDataColetaBetween(LocalDate inicio, LocalDate fim) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.between(root.get("dataColeta"), inicio, fim);
    }

    /**
     * Creates a Specification to find samples ready for analysis.
     */
    public static Specification<Amostra> prontasParaAnalise() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.and(
                criteriaBuilder.isNotEmpty(root.get("medicoes")),
                criteriaBuilder.isNotEmpty(root.get("imagens"))
            );
    }

    /**
     * Creates a Specification to find samples without report.
     */
    public static Specification<Amostra> semLaudo() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.isNull(root.get("laudo"));
    }
}
