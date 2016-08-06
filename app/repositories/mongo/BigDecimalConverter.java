package repositories.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalConverter extends TypeConverter implements SimpleValueConverter {

    public BigDecimalConverter() {
        super(scala.math.BigDecimal.class);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        BigDecimal bigDecimalValue = value instanceof scala.math.BigDecimal ?
                ((scala.math.BigDecimal) value).bigDecimal() : (BigDecimal) value;

        if (bigDecimalValue.scale() > 18) {
            bigDecimalValue = bigDecimalValue.setScale(18, BigDecimal.ROUND_DOWN);
        }

        DBObject dbo = new BasicDBObject();

        dbo.put("unscaled", bigDecimalValue.unscaledValue().longValue());
        dbo.put("scale", bigDecimalValue.scale());

        return dbo;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object decode(Class targetClass, Object fromDBObject, MappedField field) throws MappingException {
        DBObject dbo = (DBObject) fromDBObject;
        if (dbo == null) {
            return null;
        }

        BigDecimal bigDecimal = null;

        Long unscaled = (Long) dbo.get("unscaled");
        Integer scale = (Integer) dbo.get("scale");

        if (unscaled != null && scale != null) {
            bigDecimal = new BigDecimal(new BigInteger(unscaled.toString()), scale);
        }

        if (targetClass.equals(scala.math.BigDecimal.class))
            return new scala.math.BigDecimal(bigDecimal);
        else
            return bigDecimal;
    }

}