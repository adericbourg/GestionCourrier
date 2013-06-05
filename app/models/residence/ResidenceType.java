package models.residence;

import core.io.serialization.StaticListSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.avaje.ebean.annotation.EnumValue;

import core.io.serialization.ResidenceTypeDeserializer;
import core.io.serialization.StaticList;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author adericbourg
 */
@JsonSerialize(using = StaticListSerializer.class)
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
