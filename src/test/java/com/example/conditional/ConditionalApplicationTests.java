package com.example.conditional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }
    @AfterAll
    public static void tearDown() {
        devapp.stop();
        prodapp.stop();
    }


    @Test
    void contextLoadsDevapp() {
        final String expected = "Current profile is dev";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }

    @Test
    void contextLoadsProdapp() {
        final String expected = "Current profile is production";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }
}

