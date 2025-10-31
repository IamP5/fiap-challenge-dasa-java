package br.com.dasa.analisepatologica.converter;

import br.com.dasa.analisepatologica.enums.StatusProcessamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA Converter for StatusProcessamento enum.
 * Converts between database format (with spaces) and Java enum format (with underscores).
 */
@Converter(autoApply = true)
public class StatusProcessamentoConverter implements AttributeConverter<StatusProcessamento, String> {

    @Override
    public String convertToDatabaseColumn(StatusProcessamento attribute) {
        if (attribute == null) {
            return null;
        }
        // Convert Java enum name (EM_PROCESSAMENTO) to database format (EM PROCESSAMENTO)
        return attribute.name().replace("_", " ");
    }

    @Override
    public StatusProcessamento convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        // Convert database format (EM PROCESSAMENTO) to Java enum name (EM_PROCESSAMENTO)
        String enumName = dbData.trim().replace(" ", "_");

        try {
            return StatusProcessamento.valueOf(enumName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid StatusProcessamento value in database: " + dbData, e);
        }
    }
}
