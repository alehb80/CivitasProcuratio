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

    @Column(nullable = false, name="nationality")
    private String nationality;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="checkInDate")
    private Date checkInDate;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
    private List<Presence> presences;

    public Guest() {
        this.presences = new ArrayList<>();
    }

    public Guest(String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String nationality, Date checkInDate, Structure structure, List<Presence> presences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.checkInDate = checkInDate;
        this.structure = structure;
        this.presences = presences;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.dateOfBirth + " "
                + DateUtils.ageCalculator(this.dateOfBirth) + " "
                + this.getGender() + " " + this.nationality + " " + this.checkInDate;
    }

}
