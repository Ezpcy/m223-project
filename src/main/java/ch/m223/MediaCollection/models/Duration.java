package ch.m223.MediaCollection.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minutes;

    @Column(columnDefinition = "integer CHECK (seconds >= 0 AND seconds <= 59)")
    private Integer seconds;
    
    // getters and setters

    public Long getId() {
        return id;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
}
