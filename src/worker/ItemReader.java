package worker;

import model.Item;
import store.ItemStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemReader implements Runnable {
    private static final int MAX_RETRY = 10;

    private final ItemStore itemStore;
    private final ItemSource itemSource;

    public ItemReader(ItemStore itemStore,ItemSource itemSource) {
        this.itemStore = itemStore;
        this.itemSource = itemSource;
    }

    @Override
    public void run() {
        List<Item> previousItems = new ArrayList<>();
        int retry = 0;

        try {
            while (retry <= MAX_RETRY) {
                List<Item> currentItems = itemSource.getItems();
                List<Item> changedItems = findChangedItems(previousItems,currentItems);

                if (!changedItems.isEmpty()) {
                    itemStore.updateItems(changedItems);
                    previousItems = copyItems(currentItems);
                    retry = 0;
                    System.out.println("New or changed items detected: " + changedItems.size());
                } else {
                    retry++;
                    System.out.println("No changes detected. Retry: " + retry);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("ItemReader thread interrupted.");
        } finally {
            itemStore.setCompleted(true);
            System.out.println("ItemReader completed.");
        }
    }

    private List<Item> findChangedItems(List<Item> previousItems,List<Item> currentItems) {
        Map<Integer,Item> previousMap = new HashMap<>();

        for (Item item : previousItems) {
            previousMap.put(item.getId(),item);
        }

        List<Item> changedItems = new ArrayList<>();

        for (Item currentItem : currentItems) {
            Item previousItem = previousMap.get(currentItem.getId());

            if (currentItem.hasChanged(previousItem)) {
                changedItems.add(currentItem);
            }
        }

        return changedItems;
    }

    private List<Item> copyItems(List<Item> items) {
        List<Item> copy = new ArrayList<>();

        for (Item item : items) {
            copy.add(new Item(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getType()
            ));
        }

        return copy;
    }
}