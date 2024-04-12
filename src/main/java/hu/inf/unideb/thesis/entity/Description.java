package hu.inf.unideb.thesis.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a description used in institutions in the system.
 */
@Entity
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> links;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> pictures;

    public Description() {
        links = new ArrayList<>();
        pictures = new ArrayList<>();
    }

    public Description(Long id, String text, List<String> links, List<String> pictures) {
        this.id = id;
        this.text = text;
        this.links = links;
        this.pictures = pictures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}

