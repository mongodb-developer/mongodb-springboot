package com.example.mdbspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import com.example.mdbspringboot.model.GroceryItem;
import com.example.mdbspringboot.repository.ItemRepository;
import com.example.mdbspringboot.repository.CustomItemRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroceryService {

    @Autowired
	ItemRepository groceryItemRepo;
	
	@Autowired
	CustomItemRepository customRepo;

    //CRUD  CREATE , READ , UPDATE , DELETE

    @PostMapping("/items")
    public GroceryItem addItem(@RequestBody GroceryItem item) {
        item.setId(UUID.randomUUID().toString().split("-")[0]);
        return groceryItemRepo.save(item);
    }
    @GetMapping("/items")
    public List<GroceryItem> findAllItems() {
	    try {
		    Thread.sleep(1000);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
        return groceryItemRepo.findAll();
    }
    @GetMapping("/items/{id}")
    public GroceryItem getItembyId(String itemId){
        return groceryItemRepo.findById(itemId).get();
    }

    public void updateItem(GroceryItem item){
        //get the existing document from DB
        // populate new value from request to existing object/entity/document
        customRepo.updateItemQuantity(item.getName(),item.getItemQuantity());
    }
    @DeleteMapping("/items/{id}")
    public String deleteTask(String itemId){
        groceryItemRepo.deleteById(itemId);
        return itemId+" Grocery Item deleted from dashboard ";
    }
}
