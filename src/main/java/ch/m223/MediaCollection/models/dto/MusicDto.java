package ch.m223.MediaCollection.models.dto;

import ch.m223.MediaCollection.models.Music;

public class MusicDto {
    private String title;
    private String artist;
    private Integer rating;
    private Integer durationMinutes;
    private Integer durationSeconds;
    // getters and setters

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public MusicDto() {
    }

    public MusicDto(Music music) {
        this.title = music.getTitle();
        this.artist = music.getArtist();
        this.rating = music.getRating();
        if (music.getDuration() != null) {
            this.durationMinutes = music.getDuration().getMinutes();
            this.durationSeconds = music.getDuration().getSeconds();
        }
    }

    
}
