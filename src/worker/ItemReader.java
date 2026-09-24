package worker;

import model.Item;
import store.ItemStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Reads Item snapshots in a separate thread.
// In PR-2, we will connect the same reader flow to MySQL.
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
            // Keep checking for new or changed Items until the retry limit is reached.
            while (retry < MAX_RETRY) {
                List<Item> currentItems = itemSource.getItems();

                // Compare the current snapshot with the previous snapshot.
                List<Item> changedItems =
                        findChangedItems(previousItems,currentItems);

                if (!changedItems.isEmpty()) {
                    // Store only Items that are new or have changed.
                    itemStore.updateItems(changedItems);

                    // Save the current snapshot for the next comparison.
                    previousItems = copyItems(currentItems);

                    // A change resets the retry counter.
                    retry = 0;

                    System.out.println(
                            "New or changed items detected: "
                                    + changedItems.size());
                } else {
                    // No changes were found, so increase the retry counter.
                    retry++;

                    System.out.println(
                            "No changes detected. Retry: "
                                    + retry);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // Restore the interrupted status when the thread is interrupted.
            Thread.currentThread().interrupt();
            System.out.println("ItemReader thread interrupted.");
        } finally {
            // Inform other threads that ItemReader has finished.
            itemStore.setCompleted(true);
            System.out.println("ItemReader completed.");
        }
    }

    // Finds Items that are new or different from the previous snapshot.
    private List<Item> findChangedItems(
            List<Item> previousItems,
            List<Item> currentItems) {

        Map<Integer,Item> previousMap = new HashMap<>();

        // Use Item ID to quickly find the previous version of an Item.
        for (Item item : previousItems) {
            previousMap.put(item.getId(),item);
        }

        List<Item> changedItems = new ArrayList<>();

        for (Item currentItem : currentItems) {
            Item previousItem =
                    previousMap.get(currentItem.getId());

            // If there is no previous Item, it is new.Otherwise, check whether any relevant field changed.
            if (currentItem.hasChanged(previousItem)) {
                changedItems.add(currentItem);
            }
        }

        return changedItems;
    }

    // Creates a snapshot so later changes do not modify the previous snapshot.
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