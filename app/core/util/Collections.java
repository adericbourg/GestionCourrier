package core.util;

/**
 * @author adericbourg
 */
public class Collections {

    public static <T> T first(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return iterable.iterator().next();
    }
}
