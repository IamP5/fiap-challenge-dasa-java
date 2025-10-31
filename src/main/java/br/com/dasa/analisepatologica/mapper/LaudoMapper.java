package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.LaudoRequestDTO;
import br.com.dasa.analisepatologica.dto.LaudoResponseDTO;
import br.com.dasa.analisepatologica.dto.MedicoBasicDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Laudo;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.enums.StatusLaudo;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Laudo entity and DTOs.
 */
@Component
public class LaudoMapper {

    public Laudo toEntity(LaudoRequestDTO dto, Amostra amostra, Medico patologista) {
        Laudo laudo = Laudo.builder()
                .diagnosticoPrincipal(dto.diagnosticoPrincipal())
                .diagnosticosSecundarios(dto.diagnosticosSecundarios())
                .conclusao(dto.conclusao())
                .recomendacoes(dto.recomendacoes())
                .codigoCid(dto.codigoCid())
                .statusLaudo(StatusLaudo.RASCUNHO)
                .build();

        // Set relationships - JPA will derive the IDs automatically
        // from @JoinColumn mappings (insertable=false, updatable=false)
        laudo.setAmostra(amostra);
        laudo.setPatologista(patologista);

        return laudo;
    }

    public LaudoResponseDTO toResponseDTO(Laudo entity) {
        // Map pathologist to basic DTO
        MedicoBasicDTO patologistaDTO = null;
        if (entity.getPatologista() != null) {
            Medico m = entity.getPatologista();
            patologistaDTO = new MedicoBasicDTO(
                m.getMedicoId(),
                m.getNomeCompleto(),
                m.getCrm(),
                m.getUfCrm(),
                m.getEspecialidade(),
                m.getTipoMedico()
            );
        }

        return new LaudoResponseDTO(
            entity.getLaudoId(),
            entity.getAmostra() != null ? entity.getAmostra().getCodigoRastreio() : null,
            patologistaDTO,
            entity.getDiagnosticoPrincipal(),
            entity.getDiagnosticosSecundarios(),
            entity.getConclusao(),
            entity.getRecomendacoes(),
            entity.getStatusLaudo(),
            entity.getDataEmissao(),
            entity.getDataLiberacao(),
            entity.getCodigoCid(),
            entity.isCompleto(),
            entity.podeSerEditado(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public void updateEntityFromDTO(LaudoRequestDTO dto, Laudo entity) {
        entity.setDiagnosticoPrincipal(dto.diagnosticoPrincipal());
        entity.setDiagnosticosSecundarios(dto.diagnosticosSecundarios());
        entity.setConclusao(dto.conclusao());
        entity.setRecomendacoes(dto.recomendacoes());
        entity.setCodigoCid(dto.codigoCid());
    }
}
