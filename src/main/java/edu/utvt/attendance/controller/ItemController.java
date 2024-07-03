package edu.utvt.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.utvt.attendance.entities.Item;
import edu.utvt.attendance.service.ItemService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
        return item.map(new Function<Item, ResponseEntity<Item>>() {
            @Override
            public ResponseEntity<Item> apply(Item i) {
                return ResponseEntity.ok(i);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Item> getItemByNombre(@PathVariable String nombre) {
        Optional<Item> item = itemService.findByNombre(nombre);
        return item.map(new Function<Item, ResponseEntity<Item>>() {
            @Override
            public ResponseEntity<Item> apply(Item i) {
                return ResponseEntity.ok(i);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item itemDetails) {
        Optional<Item> item = itemService.findById(id);
        return item.map(new Function<Item, ResponseEntity<Item>>() {
            @Override
            public ResponseEntity<Item> apply(Item i) {
                i.setNombre(itemDetails.getNombre());
                Item updatedItem = itemService.save(i);
                return ResponseEntity.ok(updatedItem);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
        return item.map(new Function<Item, ResponseEntity<Void>>() {
            @Override
            public ResponseEntity<Void> apply(Item i) {
                itemService.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
