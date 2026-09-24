package worker;

import model.Item;
import model.Type;

import java.util.ArrayList;
import java.util.List;

// Temporary Item source for PR #1.
// It allows us to develop and test multithreading without a database.
public class InMemoryItemSource implements ItemSource {
    private final List<Item> items = new ArrayList<>();

    public InMemoryItemSource() {
        items.add(new Item(1,"Rice",22.0,1000,Type.Raw));
        items.add(new Item(2,"Car",3542.0,33,Type.Imported));
        items.add(new Item(3,"Machine",1000.0,5,Type.Manufactured));
    }

    // Returns a copy so callers cannot directly modify the source collection.
    @Override
    public synchronized List<Item> getItems() {
        return new ArrayList<>(items);
    }
}