package models.residence;

import com.avaje.ebean.annotation.EnumValue;

/**
 * @author adericbourg
 */
public enum ResidenceType {

    @EnumValue("AME")
    STATE_MEDICAL_SUPPORT,
    @EnumValue("ADM")
    ADMINISTRATIVE;
}
