package com.example.pizza_project.repository;

import com.example.pizza_project.entity.Cafe;
import com.example.pizza_project.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza,Long> {
    // public List<Pizza> findByCafe(Cafe cafe);

    Iterable<Pizza> findByNameContaining(String substring);

    List<Pizza> findByCafe(Cafe cafe);
}

