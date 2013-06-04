package core.util;

/**
 * @author adericbourg
 */
public class Collections {

    public static <T> T first(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        for (T item : iterable) {
            return item;
        }
        return null;
    }
}
