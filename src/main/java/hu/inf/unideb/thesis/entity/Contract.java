package hu.inf.unideb.thesis.entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * Represents a contract in the system.
 */
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User student;

    @ManyToOne
    private Institution institution;

    private Date startDate;

    private Integer hours;

    private Date endDate;
    private Boolean completed;

    public Contract() {
    }

    public Contract(Long id, User student, Institution institution, Date startDate,Integer hours, Date endDate, Boolean completed) {
        this.id = id;
        this.student = student;
        this.institution = institution;
        this.startDate = startDate;
        this.hours = hours;
        this.endDate = endDate;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
