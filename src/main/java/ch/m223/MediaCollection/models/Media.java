package ch.m223.MediaCollection.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Media {

  public Media() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;


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
  @JoinTable(name = "media_genre", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres = new HashSet<>();

  // getters and setters
  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public List<Music> getMusicItems() {
    return musicItems;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public List<Game> getGames() {
    return games;
  }

  public Set<Genre> getGenres() {
    return genres;
  }

}
