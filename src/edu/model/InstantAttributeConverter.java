package edu.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts the Instant dateTime to a timestamp for persistence and converts from the timestamp back
 * to an instant, all while maintaining the same UTC offset
 *
 * @author Joel Harris 
 */
@Converter
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {

    /**
     * Converts an instant to a database timestamp
     *
     * @param instant
     * @return
     */
    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        return (instant == null ? null : Timestamp.valueOf(LocalDateTime.ofInstant(instant, ZoneId.
                of("Z"))));
    }

    /**
     * Converts an Timestamp to an Instant
     *
     * @param sqlTimestamp
     * @return
     */
    @Override
    public Instant convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime().toInstant(
                ZoneOffset.UTC));
    }

}
