package io;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ext.JodaDeserializers;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author adericbourg
 */
public class JodaLocalDateDeserializer extends JodaDeserializers.LocalDateDeserializer {

    private final static DateTimeFormatter _localDateTimeFormat = ISODateTimeFormat.localDateOptionalTimeParser();

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        {
            // We'll accept either long (timestamp) or array:
            if (jp.isExpectedStartArrayToken()) {
                jp.nextToken(); // VALUE_NUMBER_INT 
                int year = jp.getIntValue();
                jp.nextToken(); // VALUE_NUMBER_INT
                int month = jp.getIntValue();
                jp.nextToken(); // VALUE_NUMBER_INT
                int day = jp.getIntValue();
                if (jp.nextToken() != JsonToken.END_ARRAY) {
                    throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "after LocalDate ints");
                }
                return new LocalDate(year, month, day);
            }
            switch (jp.getCurrentToken()) {
            case VALUE_NUMBER_INT:
                return new LocalDate(jp.getLongValue());
            case VALUE_STRING:
                DateTime local = parseLocal(jp);
                if (local == null) {
                    return null;
                }
                return local.toLocalDate();
            }
            throw ctxt.wrongTokenException(jp, JsonToken.START_ARRAY, "expected JSON Array, String or Number");
        }
    }

    protected DateTime parseLocal(JsonParser jp) throws IOException {
        String str = jp.getText().trim();
        if (str.length() == 0) { // [JACKSON-360]
            return null;
        }
        str = str.split("T")[0];
        return _localDateTimeFormat.parseDateTime(str);
    }
}
