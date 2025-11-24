package ch.m223.MediaCollection.controller;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.m223.MediaCollection.models.Media;
import ch.m223.MediaCollection.models.Music;
import ch.m223.MediaCollection.service.MediaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.Table;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/media")
@Tag(name = "Media", description = "Handling of media")
@RolesAllowed({"User", "Admin"})
public class MediaController {
    @Inject
    private MediaService mediaService;

    @Inject
    JsonWebToken jwt; // Inject jwt

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all Media")
    public List<Media> index() {
        Long userId = Long.valueOf(jwt.getClaim("userId").toString());
        return mediaService.getAllByUser(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Media media) {
        try {
            Long userId = Long.valueOf(jwt.getClaim("userId").toString());
            Media created = mediaService.createForUser(media, userId);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation( summary = "Delete a Media Collection")
    public Response deleteMedia(@PathParam("id") Long id) {
        Long userId = Long.valueOf(jwt.getClaim("userId").toString());
        boolean deleted = mediaService.deleteMediaForUser(id, userId);
        if (deleted){
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Music

    @GET
    @Path("/music")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all music for current user")
    public List<Music> getMusics() {
        Long userId = Long.valueOf(jwt.getClaim("userId").toString());
        return mediaService.getMusicsForUser(userId);
    }

    @DELETE
    @Path("/music/{id}")
    @Operation(summary = "Delete a track")
    public Response deleteMusic(@PathParam("id") Long id) {
        Long userId = Long.valueOf(jwt.getClaim("userId").toString());
        boolean deleted = mediaService.deleteMusicForUser(id, userId);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/music/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Music by ID")
    public Response getMusic(@PathParam("id") Long id) {
        Long userId = Long.valueOf(jwt.getClaim("userId").toString());
        Music music = mediaService.getMusicForUser(id, userId);
        if (music != null) {
            return Response.ok(music).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/music")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add new Music")
    public Response addMusic(Music song) {
        try {
            Long userId = Long.valueOf(jwt.getClaim("userId").toString());
            Music created = mediaService.addMusicForUser(song, userId);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
}
