package config;

import repository.CustomItemRepository;
import repository.ItemRepository;
import repository.MySqlItemRepository;

public class RepositoryConfig {
    private static final boolean USE_CUSTOM_DATA = true;

    public static ItemRepository getItemRepository() {
        if (USE_CUSTOM_DATA) {
            return new CustomItemRepository();
        }

        return new MySqlItemRepository();
    }
}