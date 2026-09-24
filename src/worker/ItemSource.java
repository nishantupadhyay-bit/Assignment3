package worker;

import model.Item;
import java.util.List;

// Defines how ItemReader obtains Items.
// PR-1 uses an in-memory implementation.
// PR-2 will provide a database implementation.
public interface ItemSource {
    List<Item> getItems();
}