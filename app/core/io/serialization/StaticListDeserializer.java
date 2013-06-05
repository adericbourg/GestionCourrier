package core.io.serialization;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author adericbourg
 */
abstract class StaticListDeserializer<T extends StaticList> extends JsonDeserializer<StaticList> {
    @Override
    public final T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);
        return valueFor(node.get("key").getTextValue());
    }

    abstract T valueFor(String value);
}
