package it.uniroma3.CivitasProcuratio.model;

import it.uniroma3.CivitasProcuratio.util.DateUtils;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ospiti")
@Proxy(lazy = false)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, name="firstName")
    private String firstName;

    @Column(nullable = false, name="lastName")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="dateOfBirth")
    private Date dateOfBirth;

    @Column(nullable = false, name="age")
    private Integer age;

    @Column(nullable = false, name="gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
    private List<Activity> activities;

    public Guest() {
        this.activities = new ArrayList<>();
    }

    public Guest(String firstName, String lastName, Date dateOfBirth, Integer age, String gender, Structure structure, List<Activity> activities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.structure = structure;
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.dateOfBirth + " "
                + DateUtils.ageCalculator(this.dateOfBirth) + " "
                + this.getGender();
    }

}
