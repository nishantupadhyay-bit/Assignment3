package store;

import model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemStore {
    private final List<Item> items = new ArrayList<>();
    private volatile boolean completed;

    public synchronized void updateItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);
    }

    public synchronized List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public synchronized void addItem(Item item) {
        items.add(item);
    }

    public synchronized void clear() {
        items.clear();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}