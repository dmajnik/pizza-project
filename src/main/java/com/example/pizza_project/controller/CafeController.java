package com.example.pizza_project.controller;

import com.example.pizza_project.entity.Cafe;
import com.example.pizza_project.entity.Pizza;
import com.example.pizza_project.repository.CafeRepository;
import com.example.pizza_project.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CafeController {

    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    public CafeController(CafeRepository repository){
        this.cafeRepository = repository;
    }

    @GetMapping("/cafes")
    public ResponseEntity<Iterable<Cafe>> getAll()
    {
        return new ResponseEntity<>(cafeRepository.findAll(), HttpStatus.OK);
    }

    // add a new Café
    @PostMapping("/cafe")
    public ResponseEntity<Cafe> createCafe(@RequestBody Cafe cafeRequest)
    {
        cafeRequest = cafeRepository.save(cafeRequest);
        return new ResponseEntity<>(cafeRequest, HttpStatus.CREATED);
    }

    // update Cafe details

    @PutMapping("/cafe/{cafeId}")
    public ResponseEntity<Cafe> updateCafeById(
            @PathVariable Long cafeId,
            @RequestBody Cafe cafeRequest
    )
    {
        Cafe cafe = cafeRepository.findById(cafeId).orElse(null);
        if(cafe != null && cafeRequest != null) {
            cafe.setName(cafeRequest.getName());
            cafe.setCity(cafeRequest.getCity());
            cafe.setEmail(cafeRequest.getEmail());
            cafe.setAddress(cafeRequest.getAddress());
            cafe.setPhone(cafeRequest.getPhone());
            cafe.setPizza_menu(cafeRequest.getPizza_menu());
            cafe.setOpen_at(cafeRequest.getOpen_at());
            cafe.setClose_at(cafeRequest.getClose_at());
            cafe = cafeRepository.save(cafe);
        }
        return ResponseEntity.ok(cafe);
    }

    // delete by id

    @DeleteMapping("/cafes/{cafeId}")
    public ResponseEntity<HttpStatus> deleteCafeById(
            @PathVariable (name = "cafeId") Long cafeId
    )
    {
        cafeRepository.deleteById(cafeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // search by cafe address

    @GetMapping("/cafes?address={cafe_address}")
    public Iterable<Cafe> findByAddress(
            @RequestParam (name = "address") String substring
    )
    {
        return cafeRepository.findByAddressContaining(substring);
    }

    // Get cafe by id with all pizza details listed

    @GetMapping("/cafe/full/{id}")
    public ResponseEntity<Map<String, Object>> cafeByIdWithPizzaDetails(
            @PathVariable("id") Long cafeId
    ) {
        Optional<Cafe> cafeOptional = cafeRepository.findById(cafeId);
        if (cafeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cafe cafe = cafeOptional.get();
        List<Pizza> pizzas = pizzaRepository.findByCafe(cafe);

        Map<String, Object> response = new HashMap<>();
        response.put("cafe", cafe);
        response.put("pizzas", pizzas);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/cafe/{id}/pizzas")
    public ResponseEntity<Collection<Pizza>> getCafePizzas(@PathVariable Long id) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(id);

        if (!optionalCafe.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cafe cafe = optionalCafe.get();
        Set<Pizza> pizzas = cafe.getPizza_menu();

        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<Cafe> getCafeById(
            @PathVariable(name = "cafeId") Long cafeId
    )
    {
        return ResponseEntity.ok(cafeRepository.findById(cafeId).get());
    }



}

