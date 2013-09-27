package models.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.io.serialization.StaticList;
import core.io.serialization.StaticListDeserializer;
import core.io.serialization.StaticListSerializer;

/**
 * @author adericbourg
 */
@JsonSerialize(using = StaticListSerializer.class)
@JsonDeserialize(using = StaticListDeserializer.class)
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

    public boolean isMale() {
        return MALE.equals(this);
    }

    public boolean isFemale() {
        return FEMALE.equals(this);
    }
}
