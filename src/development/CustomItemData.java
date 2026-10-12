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

        ItemEntity rice2 = new ItemEntity();
        rice2.setName("Rice");
        rice2.setPrice(22);
        rice2.setQuantity(1000);
        rice2.setType(Type.Raw);
        rice2.setCreatedAt(now);
        rice2.setUpdatedAt(now);

        ItemEntity car2 = new ItemEntity();
        car2.setName("Car");
        car2.setPrice(3542);
        car2.setQuantity(33);
        car2.setType(Type.Manufactured);
        car2.setCreatedAt(now);
        car2.setUpdatedAt(now);

        items.add(rice);
        items.add(car);
        items.add(rice2);
        items.add(car2);

        return items;
    }
}