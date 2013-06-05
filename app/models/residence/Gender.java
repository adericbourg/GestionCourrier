package models.residence;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import core.io.serialization.GenderDeserializer;
import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender implements StaticList {

    MALE("H"),
    FEMALE("F");

    private final String meaning;

    private Gender(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
