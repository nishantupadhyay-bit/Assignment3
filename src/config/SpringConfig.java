package config;

import mapper.ItemEntityMapper;
import mapper.ItemMapper;
import repository.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ItemRepository itemRepository() {
        return RepositoryConfig.getItemRepository();
    }

    @Bean
    public ItemMapper itemMapper() {
        return new ItemEntityMapper();
    }
}