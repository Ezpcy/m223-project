package ch.m223.MediaCollection.service;

import java.util.List;

import ch.m223.MediaCollection.models.Duration;
import ch.m223.MediaCollection.models.Genre;
import ch.m223.MediaCollection.models.Media;
import ch.m223.MediaCollection.models.Music;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MediaService {
  @Inject
  EntityManager em;

  @Transactional
  public void createSampleData() {
    // Check if sample data already exists
    var existingGenres = em.createQuery("FROM Genre WHERE name = :name", Genre.class)
        .setParameter("name", "Action")
        .getResultList();
    
    if (!existingGenres.isEmpty()) {
      return; // Sample data already exists
    }

    Genre action = new Genre();
    action.setName("Action");
    em.persist(action);

    Media media = new Media();
    media.setTitle("My Media Collection");
    media.getGenres().add(action);
    em.persist(media);
    
    action.getMediaItems().add(media);
    
    Duration duration = new Duration();
    duration.setMinutes(3);
    duration.setSeconds(45);
    em.persist(duration);
    
    Music track = new Music();
    track.setTitle("Back to black");
    track.setRating(5);
    track.setArtist("Amy Winehouse");
    track.setDuration(duration);
    em.persist(track);

    media.getMusicItems().add(track);
    track.setMedia(media);
  }

  @Transactional
  public Media create(Media media) {
    em.persist(media);
    return media;
  }

  public List<Media> getAll() {
    var query = em.createQuery("FROM Media", Media.class);
    return query.getResultList();
  }

  @Transactional
  public Music addMusic(Music song) {
    em.persist(song);
    return song; 
  }
}
