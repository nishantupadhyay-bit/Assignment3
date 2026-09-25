package thread;

import item.Items;
import output.ItemDisplay;
import tax.CalculateTaxOfItem;

import java.util.ArrayList;
import java.util.List;

public class ItemTaxThread extends Thread {
    private final ItemProcessingContext context;

    public ItemTaxThread(ItemProcessingContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (context.isThread1Running() || hasChangedItems()) {
            List<Items> tempItems = null;

            synchronized (context.getChangedItems()) {
                if (!context.getChangedItems().isEmpty()) {
                    tempItems = new ArrayList<>(context.getChangedItems());
                    context.getChangedItems().clear();
                }
            }

            if (tempItems != null) {
                for (Items item : tempItems) {
                    double[] result = CalculateTaxOfItem.calculateTaxOfItem(item);
                    ItemDisplay.display(item,result);
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private boolean hasChangedItems() {
        synchronized (context.getChangedItems()) {
            return !context.getChangedItems().isEmpty();
        }
    }
}