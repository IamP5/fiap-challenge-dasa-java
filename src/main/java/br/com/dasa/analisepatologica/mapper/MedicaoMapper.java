package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.MedicaoRequestDTO;
import br.com.dasa.analisepatologica.dto.MedicaoResponseDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Medicao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper for converting between Medicao entity and DTOs.
 */
@Component
public class MedicaoMapper {

    public Medicao toEntity(MedicaoRequestDTO dto, Amostra amostra, Integer versao) {
        Medicao medicao = Medicao.builder()
                .larguraMm(dto.larguraMm())
                .alturaMm(dto.alturaMm())
                .profundidadeMm(dto.profundidadeMm())
                .metodoMedicao(dto.metodoMedicao())
                .equipamentoUtilizado(dto.equipamentoUtilizado())
                .responsavelMedicao(dto.responsavelMedicao())
                .observacoes(dto.observacoes())
                .versao(versao)
                .dataHoraMedicao(LocalDateTime.now())
                .ativa('S')
                .build();

        // Set relationship - JPA will derive the IDs automatically
        // from @JoinColumn mapping (insertable=false, updatable=false)
        medicao.setAmostra(amostra);

        return medicao;
    }

    public MedicaoResponseDTO toResponseDTO(Medicao entity) {
        return new MedicaoResponseDTO(
                entity.getMedicaoId(),
                entity.getAmostra() != null ? entity.getAmostra().getCodigoRastreio() : null,
                entity.getLarguraMm(),
                entity.getAlturaMm(),
                entity.getProfundidadeMm(),
                entity.calcularVolume(),
                entity.getMetodoMedicao(),
                entity.getEquipamentoUtilizado(),
                entity.getVersao(),
                entity.getResponsavelMedicao(),
                entity.getDataHoraMedicao(),
                entity.getObservacoes(),
                entity.getAtiva(),
                entity.getCreatedAt()
        );
    }
}
