package com.example.pizza_project.repository;

import com.example.pizza_project.entity.Cafe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeRepository extends CrudRepository<Cafe,Long> {
    // List<Cafe> findCafesByPizzaId(Long pizzaId);
    Iterable<Cafe> findByAddressContaining(String substring);


}
