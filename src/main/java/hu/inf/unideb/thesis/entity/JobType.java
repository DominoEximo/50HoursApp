package hu.inf.unideb.thesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class JobType {

    @Id
    private Long id;

    private String name;

    public JobType() {
    }

    public JobType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
