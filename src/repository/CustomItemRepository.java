package repository;

import development.CustomItemData;
import model.ItemEntity;

import java.util.List;

public class CustomItemRepository implements ItemRepository {

    @Override
    public List<ItemEntity> findAll() {
        return CustomItemData.getItems();
    }
}