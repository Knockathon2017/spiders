package repositories.mongo;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;
import scala.collection.immutable.Seq;

public class SeqConverter extends TypeConverter implements SimpleValueConverter {

    public SeqConverter() {
        super(Seq.class);
    }

    @Override
    public Seq decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
        return null;
    }

    @Override
    public final Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        if (value.toString().equalsIgnoreCase("nil"))
            return null;

        if (value instanceof Seq)
            return scala.collection.JavaConversions.seqAsJavaList((Seq) value);

        throw new MappingException("Unable to convert " + value.getClass().getName());
    }
}