package store;

import model.Item;

import java.util.ArrayList;
import java.util.List;

// Thread-safe shared collection
public class ItemStore {
    private final List<Item> items = new ArrayList<>();

    // completion state is visible between threads.
    private volatile boolean completed;

    // Replaces the current Items with newly detected Items.
    public synchronized void updateItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);
    }

    // Returns a copy to prevent external code from modifying the shared list.
    public synchronized List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Signals that ItemReader has finished its work.
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}