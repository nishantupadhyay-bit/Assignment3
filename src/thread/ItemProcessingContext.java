package thread;

import item.Items;

import java.util.ArrayList;
import java.util.List;

public class ItemProcessingContext {
    private final List<Items> changedItems = new ArrayList<>();
    private volatile boolean thread1Running = true;

    public List<Items> getChangedItems() {
        return changedItems;
    }

    public boolean isThread1Running() {
        return thread1Running;
    }

    public void setThread1Running(boolean thread1Running) {
        this.thread1Running = thread1Running;
    }
}