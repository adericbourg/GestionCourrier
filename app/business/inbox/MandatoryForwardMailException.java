package business.inbox;

import core.exception.BusinessException;

/**
 * Exception thrown when trying to register a mail into inbox while all recipient's mail should be forwarded.
 * 
 * @author adericbourg
 */
public class MandatoryForwardMailException extends BusinessException {

    public MandatoryForwardMailException() {
        this("Une adresse de suivi est définie pour cette personne. Merci d'y faire suivre tout le courrier.");
    }

    public MandatoryForwardMailException(String message) {
        super(message);
    }
}
