package br.com.dasa.analisepatologica.repository.specification;

import br.com.dasa.analisepatologica.dto.MedicoFilterDTO;
import br.com.dasa.analisepatologica.entity.Medico;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification builder for Medico entity filtering.
 */
public class MedicoSpecification {

    public static Specification<Medico> buildSpecification(MedicoFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nomeCompleto")),
                    "%" + filter.nome().toLowerCase() + "%"
                ));
            }

            if (filter.crm() != null && !filter.crm().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("crm"), filter.crm()));
            }

            if (filter.ufCrm() != null && !filter.ufCrm().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("ufCrm"), filter.ufCrm().toUpperCase()));
            }

            if (filter.especialidade() != null && !filter.especialidade().isBlank()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("especialidade")),
                    "%" + filter.especialidade().toLowerCase() + "%"
                ));
            }

            if (filter.tipo() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoMedico"), filter.tipo()));
            }

            if (filter.ativo() != null) {
                Character ativoChar = filter.ativo() ? 'S' : 'N';
                predicates.add(criteriaBuilder.equal(root.get("ativo"), ativoChar));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
