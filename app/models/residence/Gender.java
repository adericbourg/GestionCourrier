package models.residence;

import core.io.serialization.StaticListSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import core.io.serialization.GenderDeserializer;
import core.io.serialization.StaticList;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author adericbourg
 */
@JsonSerialize(using = StaticListSerializer.class)
@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender implements StaticList {

    MALE("Homme"),
    FEMALE("Femme");

    private final String meaning;

    private Gender(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
