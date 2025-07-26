package com.spring.gestiondestock;

import com.spring.gestiondestock.auth.AuthenticationRequest;
import com.spring.gestiondestock.auth.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void Login_succes() {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("voahary2025");
        authRequest.setPassword("voahary1234");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> request = new HttpEntity<>(authRequest, headers);

        ResponseEntity<AuthenticationResponse> response = template.postForEntity(
                "/api/auth/authenticate",
                request,
                AuthenticationResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
    }
    @Test
    public void Login_failed(){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("fkjqslkgldmsjkgj");
        authenticationRequest.setPassword("imnotauser");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> request = new HttpEntity<>(authenticationRequest, headers);

        ResponseEntity<AuthenticationResponse> response = template.postForEntity(
                "/api/auth/authenticate",
                request,
                AuthenticationResponse.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
