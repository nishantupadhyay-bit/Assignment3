import model.Item;
import store.ItemStore;
import worker.InMemoryItemSource;
import worker.ItemReader;

public class Main {
    public static void main(String[] args) {
        ItemStore itemStore = new ItemStore();
        InMemoryItemSource itemSource = new InMemoryItemSource();
        ItemReader itemReader = new ItemReader(itemStore,itemSource);

        Thread readerThread = new Thread(itemReader,"Item-Reader");
        readerThread.start();

        try {
            readerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("\nItems in ItemStore:");

        for (Item item : itemStore.getItems()) {
            System.out.println(item);
        }

        System.out.println("\nReader completed: " + itemStore.isCompleted());
    }
}