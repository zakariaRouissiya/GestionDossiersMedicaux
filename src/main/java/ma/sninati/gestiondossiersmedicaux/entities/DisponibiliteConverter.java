package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Disponibilite;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Converter(autoApply = true)
public class DisponibiliteConverter implements AttributeConverter<Map<DayOfWeek, Disponibilite>, String> {

    @Override
    public String convertToDatabaseColumn(Map<DayOfWeek, Disponibilite> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.entrySet().stream()
                .map(e -> e.getKey().name() + ":" + e.getValue().name())
                .collect(Collectors.joining(","));
    }

    @Override
    public Map<DayOfWeek, Disponibilite> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashMap<>();
        }
        return Arrays.stream(dbData.split(","))
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(
                        e -> DayOfWeek.valueOf(e[0]),
                        e -> Disponibilite.valueOf(e[1])
                ));
    }

}
