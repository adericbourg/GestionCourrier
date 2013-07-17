package business.residence;

import core.exception.BusinessException;

/**
 * Exception thrown when a residence is already defined for the time lapse of the new one.
 * 
 * @author adericbourg
 */
public class ResidenceAlreadyDefinedException extends BusinessException {

    public ResidenceAlreadyDefinedException() {
        super("Une domiciliation a déjà été enregistrée sur cette période.");
    }
}
