package com.example.pizza_project.controller;

import com.example.pizza_project.entity.Cafe;
import com.example.pizza_project.entity.Pizza;
import com.example.pizza_project.repository.CafeRepository;
import com.example.pizza_project.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PizzaController {

    private PizzaRepository repository;

    private CafeRepository cafeRepository;

    @Autowired
    public PizzaController(PizzaRepository repository){
        this.repository=repository;
    }

    //  all pizzas from database
    @GetMapping("/pizzas")
    public ResponseEntity<Iterable<Pizza>> getAll()
    {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

/*
    @PostMapping("/pizza/create")
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) {
        try {
            // First, save the cafe associated with the pizza
            cafeRepository.save(pizza.getCafe());

            // Then, save the pizza
            Pizza savedPizza = repository.save(pizza);

            // Return a response with the saved pizza and a 201 Created status code
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPizza);
        } catch (Exception e) {
            // If an error occurs, return a 500 Internal Server Error status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

*/
    @PostMapping("/pizza/create")
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizzaRequest)
    {
        pizzaRequest = repository.save(pizzaRequest);
        return new ResponseEntity<>(pizzaRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/pizza/{pizzaId}")
    public ResponseEntity<HttpStatus> deletePizzaById(
            @PathVariable(name = "pizzaId") Long pizzaId
    )
    {
        // удаляем продукт
        repository.deleteById(pizzaId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Basic search by pizza name
    @GetMapping("/pizza")
    public Iterable<Pizza> findByName(
            @RequestParam (name = "name") String substring
    )
    {
        return repository.findByNameContaining(substring);
    }
    // Update pizza details (by pizza id)
    @PutMapping("/pizza/{id}")
    public ResponseEntity<Pizza> updatePizzaById(
            @PathVariable Long pizzaId,
            @RequestBody Pizza pizzaRequest
    )
    {
        Pizza pizza = repository.findById(pizzaId).orElse(null);
        if(pizza != null && pizzaRequest != null) {
            pizza.setName(pizzaRequest.getName());
            pizza.setPrice(pizzaRequest.getPrice());
            pizza.setCafe(pizzaRequest.getCafe());
            pizza.setSize(pizzaRequest.getSize());
            pizza.setIngredients(pizzaRequest.getIngredients());

        }
        return ResponseEntity.ok(pizza);
    }


    // Get specific pizza details

    @GetMapping("/pizza/{pizzaId}")
    public ResponseEntity<Pizza> getPizzaById(
            @PathVariable(name = "pizzaId") Long pizzaId
    )
    {
        return ResponseEntity.ok(repository.findById(pizzaId).get());
    }


}

