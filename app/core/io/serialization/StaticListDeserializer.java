package core.io.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Generic {@link StaticList} deserializer.
 * 
 * @author adericbourg
 */
public class StaticListDeserializer extends JsonDeserializer<StaticList> {

    @Override
    public final StaticList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);

        return getNameValue(node);
    }

    private static StaticList getNameValue(JsonNode node) {
        String key;
        if (node.has("key")) {
            // { key: "className:name", value: "" }
            key = node.get("key").textValue();
        } else {
            // { "name" }
            key = node.textValue();
        }
        return convertKeyToEnum(key);
    }

    private static StaticList convertKeyToEnum(String key) {
        String[] explodedKey = key.split(":");
        String className = explodedKey[0];
        String name = explodedKey[1];
        try {
            @SuppressWarnings("unchecked")
            Class<Enum> clazz = (Class<Enum>) Class.forName(className);
            Enum result = Enum.valueOf(clazz, name);
            return StaticList.class.cast(result);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
