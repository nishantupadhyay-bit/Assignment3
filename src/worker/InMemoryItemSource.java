package worker;

import model.Item;
import model.Type;

import java.util.ArrayList;
import java.util.List;

public class InMemoryItemSource implements ItemSource {
    private final List<Item> items = new ArrayList<>();

    public InMemoryItemSource() { // Dfeault values to test our current Chnages,
        // we will further replace this mechanism with SQL operation to retrieve data from DB
        items.add(new Item(1,"Rice",22.0,1000,Type.Raw));
        items.add(new Item(2,"Car",3542.0,33,Type.Imported));
        items.add(new Item(3,"Machine",1000.0,5,Type.Manufactured));
    }

    @Override
    public synchronized List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public synchronized void updateItemPrice(int itemId,double price) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                item.setPrice(price);
                return;
            }
        }
    }
}