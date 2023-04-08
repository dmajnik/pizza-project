package com.example.pizza_project;

import com.example.pizza_project.entity.Cafe;
import com.example.pizza_project.entity.Pizza;
import com.example.pizza_project.repository.CafeRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestCafe {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    public void getCafeById() throws Exception {

        Cafe cafe = new Cafe();

        cafe.setName("Cafe1");
        cafe.setCity("City1");
        cafe.setAddress("Address1");
        cafe.setEmail("cafe1@example.com");
        cafe.setPhone("123456789");
        cafe.setOpen_at("08:00");
        cafe.setClose_at("22:00");

        Cafe resultCafe = cafeRepository.save(cafe);

        ResponseEntity<Optional<Cafe>> response = restTemplate.exchange(
                "http://localhost:" + port + "/cafe/" + resultCafe.getId(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Optional<Cafe>>() {},
                resultCafe.getId()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Cafe> cafeOptional = response.getBody();
        Cafe returnedCafe = cafeOptional.get();
        assertNotNull(returnedCafe);
        assertEquals(cafe.getName(), returnedCafe.getName());
        assertEquals(cafe.getCity(), returnedCafe.getCity());
        assertEquals(cafe.getAddress(), returnedCafe.getAddress());
        assertEquals(cafe.getEmail(), returnedCafe.getEmail());
        assertEquals(cafe.getPhone(), returnedCafe.getPhone());
        assertEquals(cafe.getOpen_at(), returnedCafe.getOpen_at());
        assertEquals(cafe.getClose_at(), returnedCafe.getClose_at());

    }


    @Test
    public void createCafe() throws Exception {
        assertEquals(cafeRepository.count(), 4L);

        Cafe cafe = new Cafe("MyCafe", "MyCity",
                "MyAddress", "email@example.com", "1234567890",
                "08:00", "20:00");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String plainCredits = "admin:admin";
        byte[] plainCreditsBytes = plainCredits.getBytes();
        byte[] base64CreditsBytes = Base64.encodeBase64(plainCreditsBytes, false);
        String base64Credits = new String(base64CreditsBytes);
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity<Cafe> request = new HttpEntity<>(cafe, headers);

        ResponseEntity<Cafe> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/cafe",
                request,
                Cafe.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Cafe createdCafe = response.getBody();
        assertNotNull(createdCafe);
        assertEquals(cafe.getName(), createdCafe.getName());
        assertEquals(cafe.getCity(), createdCafe.getCity());
        assertEquals(cafe.getAddress(), createdCafe.getAddress());
        assertEquals(cafe.getEmail(), createdCafe.getEmail());
        assertEquals(cafe.getPhone(), createdCafe.getPhone());
        assertEquals(cafe.getOpen_at(), createdCafe.getOpen_at());
        assertEquals(cafe.getClose_at(), createdCafe.getClose_at());
        assertEquals(cafeRepository.count(), 5L);
    }

    @Test
    public void deleteCafeById() {
        Cafe cafe = new Cafe();
        cafe.setName("Cafe Test");
        cafe.setCity("Test City");
        cafe.setAddress("Test Address");
        cafe.setEmail("test@test.com");
        cafe.setPhone("1234567890");
        cafe.setOpen_at("09:00");
        cafe.setClose_at("21:00");
        cafeRepository.save(cafe);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String plainCredits = "admin:admin";
        byte[] plainCreditsBytes = plainCredits.getBytes();
        byte[] base64CreditsBytes = Base64.encodeBase64(plainCreditsBytes, false);
        String base64Credits = new String(base64CreditsBytes);
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity<Cafe> request = new HttpEntity<>(cafe, headers);

        Long id = cafe.getId();

        restTemplate.delete("http://localhost:" + port + "/cafe/", id);

        assertTrue(cafeRepository.findById(id).isPresent());
    }
    @Test
    public void getAllCafes() throws Exception {
        Cafe cafe1 = new Cafe("Cafe1", "City1", "Address1", "email1@test.com", "12345678", "08:00", "22:00");
        Cafe cafe2 = new Cafe("Cafe2", "City2", "Address2", "email2@test.com", "87654321", "07:00", "20:00");
        cafeRepository.saveAll(Arrays.asList(cafe1, cafe2));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String plainCredits = "admin:admin";
        byte[] plainCreditsBytes = plainCredits.getBytes();
        byte[] base64CreditsBytes = Base64.encodeBase64(plainCreditsBytes, false);
        String base64Credits = new String(base64CreditsBytes);
        headers.add("Authorization", "Basic " + base64Credits);


        ResponseEntity<List<Cafe>> response = restTemplate.exchange(
                "http://localhost:" + port + "/cafes",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Cafe>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Cafe> cafes = response.getBody();
        assertEquals(6, cafes.size());
        assertEquals(cafe1.getName(), cafes.get(4).getName());
        assertEquals(cafe2.getName(), cafes.get(5).getName());
    }
}



