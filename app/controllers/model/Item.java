package controllers.model;

/**
 * @author adericbourg
 */
public class Item<T, U> {

    public T key;
    public U value;

    public Item(T key, U value) {
        this.key = key;
        this.value = value;
    }
}
