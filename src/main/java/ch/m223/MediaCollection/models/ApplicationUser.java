package ch.m223.MediaCollection.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ApplicationUser {
    public ApplicationUser() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

   @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference("media-collection")
   private List<Media> collections = new ArrayList<>(); 
}
