package models.residence;

import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
public enum Gender implements StaticList {

    MALE("H"),
    FEMALE("F");

    private final String meaning;

    private Gender(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
