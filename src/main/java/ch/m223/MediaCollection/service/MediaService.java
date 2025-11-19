package ch.m223.MediaCollection.service;

import java.util.List;

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
  public Media create(Media media) {
    em.persist(media);
    return media;
  }

  public List<Media> getAll() {
    var query = em.createQuery("FROM Media", Media.class);
    return query.getResultList();
  }

  @Transactional
  public boolean deleteMedia(Long id) {
    Media media = em.find(Media.class, id);
    if (media != null) {
      em.remove(media);
      return true;
    }

    return false;
  }


  // Music
  public List<Music> getMusics() {
    var query = em.createQuery("FROM Music", Music.class);
    return query.getResultList();
  }

  public Music getMusic(Long id) {
    return em.find(Music.class, id);
  } 

  @Transactional
  public boolean deleteMusic(Long id) {
    Music music = em.find(Music.class, id);
    if (music != null) {
      em.remove(music);
      return true;
    }

    return false;
  }
  

  @Transactional
  public Music addMusic(Music song) throws Exception {
    try {
        // Persist Duration first if it exists
        if (song.getDuration() != null) {
            em.persist(song.getDuration());
        }
        
        // If media is provided, merge it to attach to the persistence context
        if (song.getMedia() != null && song.getMedia().getId() != null) {
            Media media = em.find(Media.class, song.getMedia().getId());
            if (media == null) {
                throw new Exception("Media with ID " + song.getMedia().getId() + " not found");
            }
            song.setMedia(media);
        }
        
        em.persist(song);
        return song;
    } catch (Exception e) {
        throw new Exception("Failed to add music: " + e.getMessage());
    }
  }
}
