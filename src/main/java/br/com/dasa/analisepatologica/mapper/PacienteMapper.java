package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.PacienteRequestDTO;
import br.com.dasa.analisepatologica.dto.PacienteResponseDTO;
import br.com.dasa.analisepatologica.entity.Paciente;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Paciente entity and DTOs.
 */
@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteRequestDTO dto) {
        return Paciente.builder()
                .nomeCompleto(dto.nomeCompleto())
                .dataNascimento(dto.dataNascimento())
                .sexo(dto.sexo())
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .email(dto.email())
                .enderecoCompleto(dto.enderecoCompleto())
                .build();
    }

    public PacienteResponseDTO toResponseDTO(Paciente entity) {
        return new PacienteResponseDTO(
                entity.getPacienteId(),
                entity.getNomeCompleto(),
                entity.getDataNascimento(),
                entity.calcularIdade(),
                entity.getSexo(),
                entity.getCpf(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getEnderecoCompleto(),
                entity.getAmostras() != null ? entity.getAmostras().size() : 0,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntityFromDTO(PacienteRequestDTO dto, Paciente entity) {
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setSexo(dto.sexo());
        entity.setCpf(dto.cpf());
        entity.setTelefone(dto.telefone());
        entity.setEmail(dto.email());
        entity.setEnderecoCompleto(dto.enderecoCompleto());
    }
}
