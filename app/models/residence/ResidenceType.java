package models.residence;

import com.avaje.ebean.annotation.EnumValue;
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
public enum ResidenceType implements StaticList {

    @EnumValue("AME")
    STATE_MEDICAL_SUPPORT("AME"),
    @EnumValue("ADM")
    ADMINISTRATIVE("Administrative");

    private final String meaning;

    private ResidenceType(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
