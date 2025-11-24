package ch.m223.MediaCollection.controller;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.m223.MediaCollection.models.ApplicationUser;
import ch.m223.MediaCollection.service.AuthService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class MediaControllerTest {

    @Inject
    EntityManager em;

    @Inject
    AuthService authService;

    private String authToken;
    private Long testUserId;

    @BeforeEach
    @Transactional
    public void setup() {
        em.createQuery("DELETE FROM Music").executeUpdate();
        em.createQuery("DELETE FROM Media").executeUpdate();
        em.createQuery("DELETE FROM ApplicationUser").executeUpdate();

        // Create test user and get token
        ApplicationUser user = new ApplicationUser();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("User");

        Response response = authService.register(user);
        String responseBody = response.getEntity().toString();
        authToken = responseBody.substring(
                responseBody.indexOf("\"token\": \"") + 10,
                responseBody.lastIndexOf("\""));

        // Get user ID
        testUserId = em.createQuery(
                "SELECT u.id from ApplicationUser u WHERE u.email = :email", Long.class)
                .setParameter("email", "test@example.com")
                .getSingleResult();
    }

    @Test
    public void testGetMediaWihtoutAuth() {
        given()
                .when().get("/media")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "test@example.com", roles = "User")
    @JwtSecurity(claims = {
        @Claim(key = "userId", value = "1")
    })
    public void testGetMediaWithAuth() {
        given()
            .header("Authorization", "Bearer " + authToken)
            .when().get("/media")
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON);
    }

}
