package repositories.mongo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.util.Date;

public final class JodaDateTimeConverter extends TypeConverter implements SimpleValueConverter {

    protected JodaDateTimeConverter() {
        super(DateTime.class);
    }

    @Override
    public DateTime decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
        if (fromDBObject == null) {
            return null;
        }

        if (fromDBObject instanceof Number) {
            return new DateTime(((Number) fromDBObject).longValue(), DateTimeZone.UTC);
        }

        if (fromDBObject instanceof Date) {
            return new DateTime(((Date) fromDBObject).getTime(), DateTimeZone.UTC);
        }

        throw new MappingException("Unable to convert " + fromDBObject.getClass().getName());
    }

    @Override
    public final Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof DateTime)) {
            throw new MappingException("Unable to convert " + value.getClass().getName());
        }
        return ((DateTime) value).toDateTime(DateTimeZone.UTC).toDate();
    }
}
