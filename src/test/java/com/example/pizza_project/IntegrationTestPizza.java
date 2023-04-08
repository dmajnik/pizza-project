package com.example.pizza_project;


import com.example.pizza_project.entity.Cafe;
import com.example.pizza_project.entity.Pizza;
import com.example.pizza_project.repository.CafeRepository;
import com.example.pizza_project.repository.PizzaRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestPizza {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private CafeRepository cafeRepository;


    // test get pizza by id
    @Test
    public void getPizzaById() throws Exception{

        Cafe cafe = new Cafe("1Cafe", "city", "address", "email@email.com", "12345678", "00:00", "00:00");
        Cafe resultCafe = cafeRepository.save(cafe);

        Pizza pizza = new Pizza();
        pizza.setName("Margarita");
        pizza.setId(1L);
        pizza.setSize("medium");
        pizza.setCafe(resultCafe);
        pizza.setPrice(BigDecimal.valueOf(8.50));
        pizza.setIngredients("tomato, mozzarella");
        Pizza resultPizza = pizzaRepository.save(pizza);

        ResponseEntity<Optional<Pizza>> response = restTemplate.exchange(
                "http://localhost:" + port + "/pizza/" + resultPizza.getId(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Optional<Pizza>>() {},
                resultPizza.getId()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Pizza> pizzaOptional = response.getBody();
        assertTrue(pizzaOptional.isPresent());
        Pizza returnedPizza = pizzaOptional.get();
        assertNotNull(returnedPizza);
        assertEquals(pizza.getName(), returnedPizza.getName());
        assertEquals(pizza.getSize(), returnedPizza.getSize());
        //assertEquals(pizza.getCafe(), returnedPizza.getCafe());
        assertEquals(pizza.getPrice().doubleValue(), returnedPizza.getPrice().doubleValue(), 0.001);
        assertEquals(pizza.getIngredients(), returnedPizza.getIngredients());
    }


    // test create a pizza

    @Test
    public void createPizza() throws Exception{
        assertEquals(pizzaRepository.count(),10L);

        String pizzaJson = "{\n" +
                "\t\"name\": \"Margarita\",\n" +
                "\t\"size\": \"Medium\",\n" +
                "\t\"ingredients\": \"tomato, mozzarella\",\n" +
                "\t\"price\": 8.5,\n" +
                //"\t\"cafe\": {\n" +
                "\t\t\"id\": 1\n" +
                "\t}\n" +
                "}";

        Pizza pizza = new Pizza(
                "Margarita","Medium","tomato, mozzarella", BigDecimal.valueOf(8.50),
                null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String plainCredits = "admin:admin";
        byte[] plainCreditsBytes = plainCredits.getBytes();
        byte[] base64CreditsBytes = Base64.encodeBase64(plainCreditsBytes, false);
        String base64Credits = new String(base64CreditsBytes);
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity<String> request = new HttpEntity<>(pizzaJson, headers);


        ResponseEntity<Pizza> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/pizza/create",
                request,
                Pizza.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Pizza createdPizza = response.getBody();
        assertNotNull(createdPizza);
        assertEquals(pizza.getName(), createdPizza.getName());
        assertEquals(pizza.getSize(), createdPizza.getSize());
        assertEquals(pizza.getPrice(), createdPizza.getPrice());
        assertEquals(pizza.getIngredients(), createdPizza.getIngredients());
        //assertEquals(pizza.getCafe(), createdPizza.getCafe());

        assertEquals(pizzaRepository.count(), 11L);

    }

    @Test
    public void deletePizzaById() {
       Cafe cafe =  new Cafe("1Cafe","city", "address","email@example.com","12345678",
                "00:00","00:00" );
       cafeRepository.save(cafe);

        Pizza pizza = new Pizza(
                "Margarita","medium","tomato, mozzarella", BigDecimal.valueOf(8.50),
                cafe);
        pizzaRepository.save(pizza);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String plainCredits = "admin:admin";
        byte[] plainCreditsBytes = plainCredits.getBytes();
        byte[] base64CreditsBytes = Base64.encodeBase64(plainCreditsBytes, false);
        String base64Credits = new String(base64CreditsBytes);
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity<Pizza> request = new HttpEntity<>(pizza, headers);

        restTemplate.delete("http://localhost:" + port + "/pizza/",
                pizza.getId());


        Optional<Pizza> deletedPizza = pizzaRepository.findById(pizza.getId());
        assertTrue(deletedPizza.isPresent());
    }

    @Test
    public void getAllPizzas() throws Exception{

        Cafe cafe = new Cafe("Cafe","city", "address","email2@email.com","123456789",
                "01:00","01:00" );
        cafeRepository.save(cafe);

        Pizza pizza2 = new Pizza(
                "Pepperoni","large","tomato, mozzarella, pepperoni", BigDecimal.valueOf(12.50),
               cafe );
        pizzaRepository.save(pizza2);

        Pizza pizza3= new Pizza(
                "Pepperoni","large","tomato, mozzarella, pepperoni", BigDecimal.valueOf(12.50),
                cafe);
        pizzaRepository.save(pizza3);

        ResponseEntity<List<Pizza>> response = restTemplate.exchange(
                "http://localhost:" + port + "/pizzas",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Pizza>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Pizza> pizzas = response.getBody();
        assertNotNull(pizzas);
        assertEquals(12, pizzas.size());

    }


}
