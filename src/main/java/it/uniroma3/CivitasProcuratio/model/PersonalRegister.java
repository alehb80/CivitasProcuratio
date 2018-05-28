package it.uniroma3.CivitasProcuratio.model;

import it.uniroma3.CivitasProcuratio.util.DateUtils;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="anagrafica")
@Proxy(lazy = false)
public class PersonalRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, name="firstName")
    private String firstName;

    @Column(nullable = false, name="lastName")
    private String lastName;

    @Column(name="fullName")
    private String fullName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="dateOfBirth")
    private Date dateOfBirth;

    @Column(nullable = false, name="age")
    private Integer age;

    @Column(nullable = false, name="gender")
    private String gender;

    @Column(nullable = false, name="nationality")
    private String nationality;

    @OneToOne(mappedBy = "personalRegister", cascade = CascadeType.ALL)
    private Migrant migrant;

    public PersonalRegister() {
    }

    public PersonalRegister(String firstName, String lastName, String fullName, Date dateOfBirth, Integer age, String gender, String nationality, Migrant migrant) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = DateUtils.ageCalculator(dateOfBirth);
        this.gender = gender;
        this.nationality = nationality;
        this.migrant = migrant;
    }

    public PersonalRegister(String firstName, String lastName, String fullName, Date dateOfBirth, String gender, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = DateUtils.ageCalculator(dateOfBirth);
        this.gender = gender;
        this.nationality = nationality;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Migrant getMigrant() {
        return migrant;
    }

    public void setMigrant(Migrant migrant) {
        this.migrant = migrant;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.dateOfBirth + " "
                + DateUtils.ageCalculator(this.dateOfBirth) + " "
                + this.getGender() + " " + this.nationality;
    }

}
