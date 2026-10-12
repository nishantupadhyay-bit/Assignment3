package development;

import enums.Type;
import model.ItemEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomItemData {

    public static List<ItemEntity> getItems() {
        List<ItemEntity> items = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        ItemEntity rice = new ItemEntity();
        rice.setName("Rice");
        rice.setPrice(22);
        rice.setQuantity(1000);
        rice.setType(Type.Raw);
        rice.setCreatedAt(now);
        rice.setUpdatedAt(now);

        ItemEntity car = new ItemEntity();
        car.setName("Car");
        car.setPrice(3542);
        car.setQuantity(33);
        car.setType(Type.Imported);
        car.setCreatedAt(now);
        car.setUpdatedAt(now);

        items.add(rice);
        items.add(car);

        return items;
    }
}