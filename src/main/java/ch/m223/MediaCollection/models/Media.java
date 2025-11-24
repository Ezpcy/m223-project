package ch.m223.MediaCollection.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "media")
public class Media {

  public Media() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference("user-media")
  private ApplicationUser user;

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference("media-music")
  private List<Music> musicItems = new ArrayList<>();

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference("media-video")
  private List<Video> videos = new ArrayList<>();

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference("media-game")
  private List<Game> games = new ArrayList<>();

  @ManyToMany
  @JoinTable(
    name = "media_genre", 
    joinColumns = @JoinColumn(name = "media_id"), 
    inverseJoinColumns = @JoinColumn(name = "genre_id")
  )
  private Set<Genre> genres = new HashSet<>();

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ApplicationUser getUser() {
    return user;
  }

  public void setUser(ApplicationUser user) {
    this.user = user;
  }

  public List<Music> getMusicItems() {
    return musicItems;
  }

  public void setMusicItems(List<Music> musicItems) {
    this.musicItems = musicItems;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public void setVideos(List<Video> videos) {
    this.videos = videos;
  }

  public List<Game> getGames() {
    return games;
  }

  public void setGames(List<Game> games) {
    this.games = games;
  }

  public Set<Genre> getGenres() {
    return genres;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }
}
