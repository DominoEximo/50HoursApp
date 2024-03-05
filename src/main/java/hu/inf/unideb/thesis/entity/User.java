package hu.inf.unideb.thesis.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String email;

    private String phoneNumber;

    private Date birthDate;

    private Character gender;

    private String password;

    private String OMID;

    private String IKSZCoordinator;

    private String coordinatorEmail;

    private String coordinatorPhone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    public User() {
    }

    public User( String name, String email, String phoneNumber, Date birthDate, Character gender, String password, String OMID, String IKSZCoordinator, String coordinatorEmail, String coordinatorPhone, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.OMID = OMID;
        this.IKSZCoordinator = IKSZCoordinator;
        this.coordinatorEmail = coordinatorEmail;
        this.coordinatorPhone = coordinatorPhone;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getOMID() {
        return OMID;
    }

    public void setOMID(String OMID) {
        this.OMID = OMID;
    }

    public String getIKSZCoordinator() {
        return IKSZCoordinator;
    }

    public void setIKSZCoordinator(String IKSZCoordinator) {
        this.IKSZCoordinator = IKSZCoordinator;
    }

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }

    public String getCoordinatorPhone() {
        return coordinatorPhone;
    }

    public void setCoordinatorPhone(String coordinatorPhone) {
        this.coordinatorPhone = coordinatorPhone;
    }
}
