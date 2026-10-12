package repository;

import config.DatabaseConfig;
import database.DatabaseConnection;
import enums.Type;
import model.ItemEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlItemRepository implements ItemRepository {

    @Override
    public List<ItemEntity> findAll() {
        List<ItemEntity> items = new ArrayList<>();

        String query = "SELECT * FROM items";

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                ItemEntity item = new ItemEntity();

                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setQuantity(resultSet.getInt("quantity"));
                item.setType(Type.valueOf(resultSet.getString("type")));
                item.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                item.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch items from database",e);
        }

        return items;
    }
}