package core.io.serialization;

import java.io.IOException;

import models.person.Gender;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Generic {@link StaticList} serializer.
 * 
 * @author adericbourg
 */
public class StaticListSerializer extends JsonSerializer<StaticList> {

    @Override
    public void serialize(StaticList staticList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("key");
        jsonGenerator.writeString(staticList.getClass().getCanonicalName() + ":" + staticList.name());
        jsonGenerator.writeFieldName("value");
        jsonGenerator.writeString(staticList.getMeaning());
        // FIXME Replace that dirty thing by something smarter.
        if (staticList instanceof Gender) {
            addGenderFields(staticList, jsonGenerator);
        }
        jsonGenerator.writeEndObject();
    }

    private void addGenderFields(StaticList staticList, JsonGenerator jsonGenerator) throws IOException {
        Gender gender = Gender.class.cast(staticList);
        jsonGenerator.writeFieldName("isMale");
        jsonGenerator.writeBoolean(gender.isMale());
        jsonGenerator.writeFieldName("isFemale");
        jsonGenerator.writeBoolean(gender.isFemale());
    }
}
