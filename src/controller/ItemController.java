package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ItemProcessingService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemProcessingService itemProcessingService;

    public ItemController(ItemProcessingService itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }

    @PostMapping("/process")
    public String processItems() throws InterruptedException {
        return itemProcessingService.processItems();
    }
}