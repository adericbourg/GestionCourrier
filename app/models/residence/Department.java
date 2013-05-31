package models.residence;

import com.avaje.ebean.annotation.EnumValue;

/**
 * @author adericbourg
 */
public enum Department {

    @EnumValue("PREC")
    PRECARITY("Précarité"),
    @EnumValue("PROS")
    PROSTITUTION("Prostitution");

    private String meaning;

    private Department(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
