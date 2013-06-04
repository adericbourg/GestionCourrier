package models.residence;

/**
 * @author adericbourg
 */
public enum Sex {

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
