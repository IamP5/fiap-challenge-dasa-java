package br.com.dasa.analisepatologica.repository.specification;

import br.com.dasa.analisepatologica.dto.PacienteFilterDTO;
import br.com.dasa.analisepatologica.entity.Paciente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification builder for Paciente entity filtering.
 */
public class PacienteSpecification {

    public static Specification<Paciente> buildSpecification(PacienteFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nomeCompleto")),
                    "%" + filter.nome().toLowerCase() + "%"
                ));
            }

            if (filter.cpf() != null && !filter.cpf().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("cpf"), filter.cpf()));
            }

            if (filter.sexo() != null) {
                predicates.add(criteriaBuilder.equal(root.get("sexo"), filter.sexo()));
            }

            if (filter.dataNascimentoInicio() != null && filter.dataNascimentoFim() != null) {
                predicates.add(criteriaBuilder.between(
                    root.get("dataNascimento"),
                    filter.dataNascimentoInicio(),
                    filter.dataNascimentoFim()
                ));
            } else if (filter.dataNascimentoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("dataNascimento"),
                    filter.dataNascimentoInicio()
                ));
            } else if (filter.dataNascimentoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("dataNascimento"),
                    filter.dataNascimentoFim()
                ));
            }

            if (filter.comAmostras() != null && filter.comAmostras()) {
                // Only patients with at least one sample
                predicates.add(criteriaBuilder.isNotEmpty(root.get("amostras")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
