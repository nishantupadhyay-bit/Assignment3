package service;

import item.Items;
import mapper.ItemEntityMapper;
import mapper.ItemMapper;
import repository.ItemRepository;
import config.RepositoryConfig;
import thread.ItemProcessingContext;
import thread.ItemReaderThread;
import thread.ItemTaxThread;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ItemProcessingService {

    private final ItemRepository repository;
    private final ItemMapper itemMapper;

    public ItemProcessingService(
            ItemRepository repository,
            ItemMapper itemMapper) {
        this.repository = repository;
        this.itemMapper = itemMapper;
    }

    public String processItems() throws InterruptedException {
        List<Items> previousItems = new ArrayList<>();

        ItemProcessingContext context = new ItemProcessingContext();

        ItemReaderThread thread1 = new ItemReaderThread(
                repository,
                itemMapper,
                context,
                previousItems
        );

        ItemTaxThread thread2 = new ItemTaxThread(context);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        return "Item processing completed";
    }
}