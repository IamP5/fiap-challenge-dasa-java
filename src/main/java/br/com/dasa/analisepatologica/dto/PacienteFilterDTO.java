package br.com.dasa.analisepatologica.dto;

import br.com.dasa.analisepatologica.enums.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO for filtering patients using Java record.
 */
@Schema(description = "Critérios de filtro para busca de pacientes")
public record PacienteFilterDTO(
    @Schema(description = "Nome do paciente (busca parcial, case insensitive)", example = "Maria Silva")
    String nome,

    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    String cpf,

    @Schema(description = "Sexo do paciente")
    Sexo sexo,

    @Schema(description = "Data inicial de nascimento para filtro de período", example = "1980-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimentoInicio,

    @Schema(description = "Data final de nascimento para filtro de período", example = "1990-12-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimentoFim,

    @Schema(description = "Filtrar apenas pacientes com amostras cadastradas", example = "true")
    Boolean comAmostras
) {
    public boolean hasAnyFilter() {
        return nome != null || cpf != null || sexo != null
            || dataNascimentoInicio != null || dataNascimentoFim != null || comAmostras != null;
    }
}
