package ch.m223.MediaCollection.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.m223.MediaCollection.models.ApplicationUser;
import ch.m223.MediaCollection.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Tag(name = "Authentication", description = "User authentication and authorization")
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Login with email and password")
    public Response login(LoginRequest request) {
        return authService.login(request.email, request.password);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Register a new user")
    public Response register(ApplicationUser user) {
        return authService.register(user);
    }

    // DTO for login request
    public static class LoginRequest {
        public String email;
        public String password;
    }
}
