package hu.inf.unideb.thesis.entity;


import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a user in the system.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date birthDate;

    private Character gender;

    private String password;

    private String OMID;

    private String schoolName;

    private String IKSZCoordinator;

    private String coordinatorEmail;

    private String coordinatorPhone;

    @OneToMany(fetch = FetchType.EAGER)
    private List<JobType> preferedJobs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;


    public User() {
        this.preferedJobs = new ArrayList<>();
    }

    public User( String username,String firstName,String lastName, String email, String phoneNumber, Date birthDate, Character gender, String password, String OMID,String schoolName, String IKSZCoordinator, String coordinatorEmail, String coordinatorPhone, List<Role> roles) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.OMID = OMID;
        this.schoolName = schoolName;
        this.IKSZCoordinator = IKSZCoordinator;
        this.coordinatorEmail = coordinatorEmail;
        this.coordinatorPhone = coordinatorPhone;
        this.roles = roles;
        this.preferedJobs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public List<JobType> getPreferedJobs() {
        return preferedJobs;
    }

    public void setPreferedJobs(List<JobType> preferedJobs) {
        this.preferedJobs = preferedJobs;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
