package core.io.serialization;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author adericbourg
 */
public class StaticListSerializer extends JsonSerializer<StaticList> {

    @Override
    public void serialize(StaticList staticList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("key");
        jsonGenerator.writeString(staticList.name());
        jsonGenerator.writeFieldName("value");
        jsonGenerator.writeString(staticList.getMeaning());
        jsonGenerator.writeEndObject();
    }
}
