package ch.m223.MediaCollection.service;

import java.util.List;

import ch.m223.MediaCollection.models.ApplicationUser;
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

  public List<Media> getAllByUser(Long userId) {
    return em.createQuery(
        "SELECT m FROM Media m WHERE m.user.id = :userId", Media.class)
        .setParameter("userId", userId)
        .getResultList();
  }

  @Transactional
  public Media createForUser(Media media, Long userId) throws Exception {
    ApplicationUser user = em.find(ApplicationUser.class, userId);
    if (user == null) {
      throw new Exception("User not found");
    }
    media.setUser(user);
    em.persist(media);
    return media;
  }

  @Transactional
  public boolean deleteMediaForUser(Long mediaId, Long userId) {
    Media media = em.createQuery(
        "SELECT m FROM Media m WHERE m.id = :mediaId AND m.user.id = :userId", Media.class)
        .setParameter("mediaId", mediaId)
        .setParameter("userId", userId)
        .getResultStream()
        .findFirst()
        .orElse(null);

    if (media != null) {
      em.remove(media);
      return true;
    }
    return false;
  }

  // Music
  public List<Music> getMusicsForUser(Long userId) {
    return em.createQuery(
        "SELECT m FROM Music m WHERE m.media.user.id = :userId", Music.class)
        .setParameter("userId", userId)
        .getResultList();
  }

  public Music getMusicForUser(Long musicId, Long userId) {
    return em.createQuery(
        "SELECT m FROM Music m WHERE m.id = :musicId AND m.media.user.id = :userId", Music.class)
        .setParameter("musicId", musicId)
        .setParameter("userId", userId)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

   @Transactional
   public boolean deleteMusicForUser(Long musicId, Long userId) {
    Music music = getMusicForUser(musicId, userId);
    if (music != null) {
      em.remove(music);
      return true;
    }
    return false;
   }

   @Transactional
   public Music addMusicForUser(Music song, Long userId) throws Exception {
    try {
      if (song.getDuration() != null) {
        em.persist(song.getDuration());
      }

      if (song.getMedia() != null && song.getMedia().getId() != null) {
        Media media = em.createQuery(
          "SELECT m FROM Media m WHERE m.id = :mediaId AND m.user.id = :userId", Media.class)  // Fixed: removed space before userId
          .setParameter("mediaId", song.getMedia().getId())
          .setParameter("userId", userId)
          .getResultStream()
          .findFirst()
          .orElse(null);

        if (media == null) {
          throw new Exception("Media not found or does not belong to user");
        }
        song.setMedia(media);
      } else {
        throw new Exception("Media is required");
      }

      em.persist(song);
      return song;
    } catch (Exception e) {
      throw new Exception("Failed to add music: " + e.getMessage());
    }
   }
}
