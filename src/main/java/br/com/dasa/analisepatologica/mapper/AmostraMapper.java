package br.com.dasa.analisepatologica.mapper;

import br.com.dasa.analisepatologica.dto.AmostraRequestDTO;
import br.com.dasa.analisepatologica.dto.AmostraResponseDTO;
import br.com.dasa.analisepatologica.dto.MedicoBasicDTO;
import br.com.dasa.analisepatologica.dto.PacienteBasicDTO;
import br.com.dasa.analisepatologica.entity.Amostra;
import br.com.dasa.analisepatologica.entity.Medico;
import br.com.dasa.analisepatologica.entity.Paciente;
import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Amostra entity and DTOs.
 */
@Component
public class AmostraMapper {

    public Amostra toEntity(AmostraRequestDTO dto, Paciente paciente, Medico medicoSolicitante) {
        Amostra amostra = Amostra.builder()
                .codigoRastreio(dto.codigoRastreio())
                .tipoTecido(dto.tipoTecido())
                .localizacaoAnatomica(dto.localizacaoAnatomica())
                .dataColeta(dto.dataColeta())
                .dataRecebimento(dto.dataRecebimento())
                .observacoes(dto.observacoes())
                .statusProcessamento(StatusProcessamento.RECEBIDA)
                .build();

        // Set relationships - JPA will derive the IDs automatically
        // from @JoinColumn mappings (pacienteId and medicoId are insertable=false, updatable=false)
        amostra.setPaciente(paciente);
        amostra.setMedicoSolicitante(medicoSolicitante);

        return amostra;
    }

    public AmostraResponseDTO toResponseDTO(Amostra entity) {
        // Map patient to basic DTO
        PacienteBasicDTO pacienteDTO = null;
        if (entity.getPaciente() != null) {
            Paciente p = entity.getPaciente();
            pacienteDTO = new PacienteBasicDTO(
                p.getPacienteId(),
                p.getNomeCompleto(),
                p.getDataNascimento(),
                p.calcularIdade(),
                p.getSexo(),
                p.getCpf()
            );
        }

        // Map doctor to basic DTO
        MedicoBasicDTO medicoDTO = null;
        if (entity.getMedicoSolicitante() != null) {
            Medico m = entity.getMedicoSolicitante();
            medicoDTO = new MedicoBasicDTO(
                m.getMedicoId(),
                m.getNomeCompleto(),
                m.getCrm(),
                m.getUfCrm(),
                m.getEspecialidade(),
                m.getTipoMedico()
            );
        }

        return new AmostraResponseDTO(
            entity.getAmostraId(),
            entity.getCodigoRastreio(),
            pacienteDTO,
            medicoDTO,
            entity.getTipoTecido(),
            entity.getLocalizacaoAnatomica(),
            entity.getDataColeta(),
            entity.getDataRecebimento(),
            entity.getStatusProcessamento(),
            entity.getObservacoes(),
            entity.getTotalMedicoes(),
            entity.getTotalImagens(),
            entity.temLaudo(),
            entity.estaProntaParaAnalise(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public void updateEntityFromDTO(AmostraRequestDTO dto, Amostra entity) {
        entity.setTipoTecido(dto.tipoTecido());
        entity.setLocalizacaoAnatomica(dto.localizacaoAnatomica());
        entity.setDataColeta(dto.dataColeta());
        entity.setDataRecebimento(dto.dataRecebimento());
        entity.setObservacoes(dto.observacoes());
    }
}
