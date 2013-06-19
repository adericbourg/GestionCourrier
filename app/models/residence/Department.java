package models.residence;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.avaje.ebean.annotation.EnumValue;

import core.io.serialization.DepartmentDeserializer;
import core.io.serialization.StaticList;
import core.io.serialization.StaticListSerializer;

/**
 * @author adericbourg
 */
@JsonSerialize(using = StaticListSerializer.class)
@JsonDeserialize(using = DepartmentDeserializer.class)
public enum Department implements StaticList {

    @EnumValue("PREC")
    PRECARITY("Précarité"),
    @EnumValue("PROS")
    PROSTITUTION("Prostitution");

    private final String meaning;

    private Department(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
