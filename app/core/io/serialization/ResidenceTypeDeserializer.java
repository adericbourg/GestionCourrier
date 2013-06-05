package core.io.serialization;

import models.residence.ResidenceType;

/**
 * @author adericbourg
 */
public class ResidenceTypeDeserializer extends StaticListDeserializer<ResidenceType> {
    @Override
    ResidenceType valueFor(String value) {
        return ResidenceType.valueOf(value);
    }
}
