package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="migranti")
@Proxy(lazy = false)
public class Migrant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="fullName")
    private String fullName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="checkInDate")
    private Date checkInDate;

    @Column(nullable = false, name="assigned")
    private boolean assigned;

    @OneToMany(mappedBy = "migrant", cascade = CascadeType.ALL)
    private List<Guest> guests;

    @OneToOne
    @JoinColumn(name = "personal_register_id")
    private PersonalRegister personalRegister;

    public Migrant() {
        this.guests = new ArrayList<>();
    }

    public Migrant(Date checkInDate, boolean assigned, List<Guest> guests) {
        this.checkInDate = checkInDate;
        this.assigned = assigned;
        this.guests = guests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    /*public Optional getOptionalPersonalRegister() {
        return Optional.ofNullable(personalRegister);
    }*/

    public PersonalRegister getPersonalRegister() {
        return personalRegister;
    }

    public void setPersonalRegister(PersonalRegister personalRegister) {
        this.personalRegister = personalRegister;
    }

}