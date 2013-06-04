package models.residence;

import core.io.serialization.StaticList;

/**
 * @author adericbourg
 */
public enum Sex implements StaticList {

    MALE("H"),
    FEMALE("F");

    private final String meaning;

    private Sex(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
