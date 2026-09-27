package thread;

import enums.Type;
import item.Items;
import mapper.ItemMapper;
import model.ItemEntity;
import repository.ItemRepository;
import validation.ValidateItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemReaderThread extends Thread {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemProcessingContext context;
    private final List<Items> previousItems;

    public ItemReaderThread(
            ItemRepository itemRepository,
            ItemMapper itemMapper,
            ItemProcessingContext context,
            List<Items> previousItems) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.context = context;
        this.previousItems = previousItems;
    }

    @Override
    public void run() {
        int retryCount = 0;

        try {
            while (retryCount < 10) {
                try {
                    System.out.println(
                            "Thread 1: Attempt " + (retryCount + 1) + " - Retrieving items"
                    );

                    List<ItemEntity> entities = itemRepository.findAll();
                    List<Items> currentItems = new ArrayList<>();

                    for (ItemEntity entity : entities) {
                        currentItems.add(itemMapper.map(entity));
                    }

                    List<Items> changedItems =
                            findChangedItems(previousItems,currentItems);

                    validateItems(changedItems);

                    synchronized (context.getChangedItems()) {
                        context.getChangedItems().addAll(changedItems);
                    }

                    previousItems.clear();
                    previousItems.addAll(currentItems);

                    if (!changedItems.isEmpty()) {
                        retryCount = 0;
                    } else {
                        retryCount++;
                    }

                } catch (Exception e) {
                    retryCount++;

                    if (retryCount == 10) {
                        throw new RuntimeException(
                                "Failed to retrieve items after 10 retries",e
                        );
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(
                                "Thread interrupted during retry",ex
                        );
                    }
                }
            }
        } finally {
            context.setThread1Running(false);
        }
    }

    private List<Items> findChangedItems(
            List<Items> previousItems,
            List<Items> currentItems) {

        List<Items> changedItems = new ArrayList<>();

        for (Items currentItem : currentItems) {
            boolean changed = true;

            for (Items previousItem : previousItems) {
                if (isSameItem(previousItem,currentItem)) {
                    changed = false;
                    break;
                }
            }

            if (changed) {
                changedItems.add(currentItem);
            }
        }

        return changedItems;
    }

    private boolean isSameItem(Items previousItem,Items currentItem) {
        return previousItem.getName().equals(currentItem.getName())
                && previousItem.getPrice() == currentItem.getPrice()
                && previousItem.getQuantity() == currentItem.getQuantity()
                && previousItem.getType() == currentItem.getType();
    }

    private void validateItems(List<Items> changedItems) {
        HashMap<String,ArrayList<Type>> itemMapWithType = new HashMap<>();
        ValidateItem validator = new ValidateItem();

        changedItems.removeIf(item -> {
            try {
                validator.validateItem(item,itemMapWithType);

                itemMapWithType
                        .computeIfAbsent(item.getName(),key -> new ArrayList<>())
                        .add(item.getType());

                return false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return true;
            }
        });
    }
}