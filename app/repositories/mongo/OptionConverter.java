package repositories.mongo;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;
import scala.Option;
import scala.Some;

public class OptionConverter extends TypeConverter implements SimpleValueConverter {

    protected OptionConverter() {
        super(Option.class);
    }

    @Override
    public Option decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
        if (fromDBObject == null) {
            return Option.empty();
        }

        return Some.apply(optionalExtraInfo.getMapper().getConverters().decode(fromDBObject.getClass(), fromDBObject, optionalExtraInfo));
    }

    @Override
    public final Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        if (value.toString().equalsIgnoreCase("none"))
            return null;

        if (value instanceof Some)
            return optionalExtraInfo.getMapper().getConverters().encode(((Some) value).get());

        throw new MappingException("Unable to convert " + value.getClass().getName());
    }
}