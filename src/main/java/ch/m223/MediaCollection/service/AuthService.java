package ch.m223.MediaCollection.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import ch.m223.MediaCollection.models.ApplicationUser;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {

    @Inject
    EntityManager em;

    public String generateToken(ApplicationUser user) {
        return Jwt
                .issuer("https://example.com/issuer")
                .upn(user.getEmail())
                .groups(new HashSet<>(Arrays.asList(user.getRole())))
                .claim("userId", user.getId())
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    @Transactional
    public Response login(String email, String password) {
        try {
            ApplicationUser user = em.createQuery(
                    "SELECT u FROM ApplicationUser u WHERE u.email = :email",
                    ApplicationUser.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (user != null && BcryptUtil.matches(password, user.getPassword())) {
                String token = generateToken(user);
                return Response.ok()
                        .entity("{\"token\": \"" + token + "\"}")
                        .build();
            }
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Invalid credentials\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Invalid credentials\"}")
                    .build();
        }
    }

    @Transactional
    public Response register(ApplicationUser user) {
        try {
            long count = em.createQuery(
                "SELECT COUNT(u) FROM ApplicationUser u WHERE u.email = :email", Long.class
            )
            .setParameter("email", user.getEmail())
            .getSingleResult();

            if (count > 0) {
                return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"User already exists\"}")
                    .build();
            }

            // Hash password
            user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));

            if (user.getRole() == null) {
                user.setRole("User");
            }

            em.persist(user);

            String token = generateToken(user);
            return Response.status(Response.Status.CREATED)
                .entity("{\"token\": \"" + token + "\"}")
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \""+ e.getMessage() + "\"}")
                .build();
        }
    }
}
