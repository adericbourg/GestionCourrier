package core.io.serialization;

import models.residence.Gender;

/**
 * @author adericbourg
 */
public class GenderDeserializer extends StaticListDeserializer<Gender> {
    @Override
    Gender valueFor(String value) {
        return Gender.valueOf(value);
    }
}
