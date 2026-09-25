package repository;

import model.ItemEntity;

import java.util.List;

public interface ItemRepository {
    List<ItemEntity> findAll();
}