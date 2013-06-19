package controllers.model;

/**
 * @author adericbourg
 */
public class Item<T, U> {

    public final T key;
    public final U value;

    public Item(T key, U value) {
        this.key = key;
        this.value = value;
    }
}
