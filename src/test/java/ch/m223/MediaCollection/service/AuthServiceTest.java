package ch.m223.MediaCollection.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import ch.m223.MediaCollection.models.ApplicationUser;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class AuthServiceTest {

    @Inject
    AuthService authService;

    @Inject
    EntityManager em;

    private ApplicationUser testUser;
    
    @BeforeEach
    @Transactional
    public void setup() {
        // Clean
        em.createQuery("DELETE FROM Media").executeUpdate();
        em.createQuery("DELETE FROM ApplicationUser").executeUpdate();

        // test user
        testUser = new ApplicationUser();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("User");
    }

    @Test
    public void testRegisterNewUser() {
        Response response = authService.register(testUser);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("token"));
    }

    @Test
    public void testRegisterDuplicateUser() {
        authService.register(testUser);

        // Try same email again
        ApplicationUser duplicate = new ApplicationUser();
        duplicate.setEmail("test@example.com");
        duplicate.setPassword("password321");

        Response response = authService.register(duplicate);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("already exists"));
    }

    @Test
    @Transactional
    public void testLoginWithInvalidPassword() {
        authService.register(testUser);

        Response response = authService.login("test@example.com", "wrongpassword");

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());


    }
}
