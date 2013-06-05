package models.residence;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.avaje.ebean.annotation.EnumValue;

import core.io.serialization.DepartmentDeserializer;
import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
@JsonDeserialize(using = DepartmentDeserializer.class)
public enum Department implements StaticList {

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
