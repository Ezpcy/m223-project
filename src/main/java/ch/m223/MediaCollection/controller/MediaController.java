package ch.m223.MediaCollection.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.m223.MediaCollection.models.Media;
import ch.m223.MediaCollection.service.MediaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/media")
@Tag(name = "Media", description = "Handling of media")
public class MediaController {
  @Inject
  private MediaService mediaService;

  @Path("/sample")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Operation(summary = "Generates sample data")
  public String sample() {
    mediaService.createSampleData();
    return "OK";
  }

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
}
