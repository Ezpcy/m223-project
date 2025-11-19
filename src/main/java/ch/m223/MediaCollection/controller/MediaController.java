package ch.m223.MediaCollection.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.m223.MediaCollection.models.Media;
import ch.m223.MediaCollection.models.Music;
import ch.m223.MediaCollection.service.MediaService;
import jakarta.inject.Inject;
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
public class MediaController {
    @Inject
    private MediaService mediaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all Media")
    public List<Media> index() {
        return mediaService.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Media create(Media media) {
        return mediaService.create(media);
    }

    @DELETE
    @Path("/{id}")
    @Operation( summary = "Delete a Media Collection")
    public Response deleteMedia(@PathParam("id") Long id) {
        boolean deleted = mediaService.deleteMedia(id);
        if (deleted){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Music

    @GET
    @Path("/music")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all Music")
    public List<Music> getMusics() {
        return mediaService.getMusics();
    }

    @DELETE
    @Path("/music/{id}")
    @Operation(summary = "Delete a track")
    public Response deleteMusic(@PathParam("id") Long id) {
        boolean deleted = mediaService.deleteMusic(id);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/music/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Music by ID")
    public Music getMusic(@PathParam("id") Long id) {
        return mediaService.getMusic(id);
    }

    @POST
    @Path("/music")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add new Music")
    public Response addMusic(Music song) {
        try {
            Music created = mediaService.addMusic(song);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
}
