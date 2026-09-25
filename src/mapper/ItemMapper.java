package mapper;
import item.Items;
import model.ItemEntity;

public interface ItemMapper {
    Items map(ItemEntity entity);
}