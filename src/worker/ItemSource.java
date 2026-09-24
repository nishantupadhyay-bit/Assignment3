package worker;

import model.Item;
import java.util.List;

public interface ItemSource {
    List<Item> getItems();
}