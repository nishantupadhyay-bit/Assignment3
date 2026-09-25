package repository;

import model.ItemRecord;

import java.util.List;

public interface ItemRepository {
    List<ItemRecord> findAll();
}