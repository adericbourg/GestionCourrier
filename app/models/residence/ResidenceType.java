package models.residence;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.avaje.ebean.annotation.EnumValue;

import core.io.serialization.ResidenceTypeDeserializer;
import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
@JsonDeserialize(using = ResidenceTypeDeserializer.class)
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
