package controller;

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

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ItemRepository repository = RepositoryConfig.getItemRepository();
        ItemMapper itemMapper = new ItemEntityMapper();

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

        System.out.println("Assignment 3 completed.");
    }
}