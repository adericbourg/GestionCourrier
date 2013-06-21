package business.inbox;

import core.exception.BusinessException;

/**
 * Exception thrown when trying to register a mail into inbox while all
 * recipient's mail should be forwarded.
 * 
 * @author adericbourg
 */
public class MandatoryForwardMailException extends BusinessException {
}
