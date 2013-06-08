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
abstract class StaticListDeserializer<T extends StaticList> extends
        JsonDeserializer<StaticList> {

    @Override
    public final T deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);

        String nameValue = getNameValue(node);
        return valueFor(nameValue);
    }

    private static String getNameValue(JsonNode node) {
        String nameValue;
        if (node.has("key")) {
            // { key: "name", value: "" }
            nameValue = node.get("key").getTextValue();
        } else {
            // { "name" }
            nameValue = node.getTextValue();
        }
        return nameValue;
    }

    abstract T valueFor(String value);
}
