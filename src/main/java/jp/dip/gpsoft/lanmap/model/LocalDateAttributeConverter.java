package jp.dip.gpsoft.lanmap.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate ld) {
		return ld == null ? null : Timestamp.valueOf(ld.atTime(0, 0));
	}

	@Override
	public LocalDate convertToEntityAttribute(Timestamp ts) {
		return ts == null ? null : ts.toLocalDateTime().toLocalDate();
	}

}
