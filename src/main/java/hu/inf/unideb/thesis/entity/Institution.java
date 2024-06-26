package hu.inf.unideb.thesis.entity;

import jakarta.persistence.*;

/**
 * Represents an institution in the system.
 */
@Entity
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private JobType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;

    @OneToOne
    private User contact;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    private Description description;

    public Institution() {
    }

    public Institution(Long id,String name, JobType type, Location location, Description description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
