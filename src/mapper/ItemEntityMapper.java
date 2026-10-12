package mapper;

import item.Items;
import model.ItemEntity;

public class ItemEntityMapper implements ItemMapper {

    @Override
    public Items map(ItemEntity entity) {
        return new Items(
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getType()
        );
    }
}