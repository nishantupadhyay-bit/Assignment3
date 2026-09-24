import model.Item;
import store.ItemStore;
import worker.InMemoryItemSource;
import worker.ItemReader;

public class Main {
    public static void main(String[] args) {
        // Shared store that will later be consumed by Worker rgards to tax in PR #2.
        ItemStore itemStore = new ItemStore();

        // PR-1 uses in-memory data instead of a database.
        InMemoryItemSource itemSource = new InMemoryItemSource();

        // ItemReader is responsible for detecting new or changed Items.
        ItemReader itemReader = new ItemReader(itemStore,itemSource);

        // Run ItemReader in a separate thread.
        Thread readerThread = new Thread(itemReader,"Item-Reader");
        readerThread.start();

        try {
            // Wait for ItemReader to finish before printing the result.
            readerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("\nItems in ItemStore:");

        for (Item item : itemStore.getItems()) {
            System.out.println(item);
        }

        System.out.println(
                "\nReader completed: "
                        + itemStore.isCompleted());
    }
}