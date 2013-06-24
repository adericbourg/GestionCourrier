package core.controller;

/**
 * @author adericbourg
 */
public interface WorkWrapper<T> {

    /**
     * Wrap work to be done into the function.
     * 
     * @return Json work result.
     */
    T execute();
}
