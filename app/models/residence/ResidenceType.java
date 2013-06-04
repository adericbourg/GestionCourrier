package models.residence;

import com.avaje.ebean.annotation.EnumValue;
import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
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
