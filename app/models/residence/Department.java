package models.residence;

import com.avaje.ebean.annotation.EnumValue;

/**
 * @author adericbourg
 */
public enum Department {

    @EnumValue("PREC")
    PRECARITY,
    @EnumValue("PROS")
    PROSTITUTION;
}
