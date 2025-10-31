package br.com.dasa.analisepatologica.converter;

import br.com.dasa.analisepatologica.enums.Sexo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA Converter for Sexo enum.
 * Converts between database format (single character M/F/O) and Java enum.
 */
@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, String> {

    @Override
    public String convertToDatabaseColumn(Sexo attribute) {
        if (attribute == null) {
            return null;
        }
        // Convert Java enum to database character code
        return String.valueOf(attribute.getCodigo());
    }

    @Override
    public Sexo convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        // Convert database character code to Java enum
        char codigo = dbData.trim().charAt(0);

        try {
            return Sexo.fromCodigo(codigo);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Sexo value in database: " + dbData, e);
        }
    }
}
