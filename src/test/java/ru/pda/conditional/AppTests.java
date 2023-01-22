package ru.pda.conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTests {
    private static final int DEVPORT = 8080;
    private static final int PRODPORT = 8081;

    @Autowired
    TestRestTemplate restTemplate;
    private static final GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(DEVPORT);
    private static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(PRODPORT);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void devAppTest() {

        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:" + devapp.getMappedPort(DEVPORT)
                        + "/profile", String.class);
        System.out.println(forEntity.getBody());

        Assertions.assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void prodAppTest() {
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("http://localhost:" + prodapp.getMappedPort(PRODPORT)
                        + "/profile", String.class);
        System.out.println(forEntity.getBody());

        Assertions.assertEquals("Current profile is production", forEntity.getBody());
    }

}
