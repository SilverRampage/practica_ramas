package edu.utvt.attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utvt.attendance.entities.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNombre(String nombre);
}
