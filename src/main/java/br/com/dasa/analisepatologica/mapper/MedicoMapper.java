package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.MedicoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicoResponseDTO;
import br.com.dasa.analisepatologica.entity.Medico;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Medico entity and DTOs.
 */
@Component
public class MedicoMapper {

    public Medico toEntity(MedicoRequestDTO dto) {
        return Medico.builder()
                .nomeCompleto(dto.nomeCompleto())
                .crm(dto.crm())
                .ufCrm(dto.ufCrm())
                .especialidade(dto.especialidade())
                .tipoMedico(dto.tipoMedico())
                .telefone(dto.telefone())
                .email(dto.email())
                .ativo('S')
                .build();
    }

    public MedicoResponseDTO toResponseDTO(Medico entity) {
        return new MedicoResponseDTO(
                entity.getMedicoId(),
                entity.getNomeCompleto(),
                entity.getCrm(),
                entity.getUfCrm(),
                entity.getEspecialidade(),
                entity.getTipoMedico(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.isAtivo(),
                entity.getTotalAmostrasSolicitadas(),
                entity.getTotalLaudosEmitidos(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntityFromDTO(MedicoRequestDTO dto, Medico entity) {
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setCrm(dto.crm());
        entity.setUfCrm(dto.ufCrm());
        entity.setEspecialidade(dto.especialidade());
        entity.setTipoMedico(dto.tipoMedico());
        entity.setTelefone(dto.telefone());
        entity.setEmail(dto.email());
    }
}
