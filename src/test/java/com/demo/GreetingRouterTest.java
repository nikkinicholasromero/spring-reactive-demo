package com.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingRouterTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GreetingService greetingService;

    @Test
    public void hello_whenOk() {
        when(greetingService.getGreeting("World!")).thenReturn(new Greeting("Hello, World!"));

        webTestClient
                .get().uri("/hello/World!")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Greeting.class).value(greeting -> {
                    assertThat(greeting.getMessage()).isEqualTo("Hello, World!");
                });
    }

    @Test
    public void hello_whenBadRequest() {
        doThrow(new RuntimeException()).when(greetingService).getGreeting(anyString());

        webTestClient
                .get().uri("/hello/World!")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
