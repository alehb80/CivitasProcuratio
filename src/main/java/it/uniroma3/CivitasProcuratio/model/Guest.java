package it.uniroma3.CivitasProcuratio.model;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name="checkInDate")
    private Date checkInDate;

    @ManyToOne
    @JoinColumn(name = "cas_id")
    private Cas cas;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
    private List<Presence> presences;

    @ManyToOne
    @JoinColumn(name = "migrant_id")
    private Migrant migrant;

    public Guest() {
        this.presences = new ArrayList<>();
    }

    public Guest(Date checkInDate, Cas cas, List<Presence> presences, Migrant migrant) {
        this.checkInDate = checkInDate;
        this.cas = cas;
        this.presences = presences;
        this.migrant = migrant;
    }

    public Guest(Date checkInDate) {
        this.checkInDate = checkInDate;
        this.presences = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Cas getCas() {
        return cas;
    }

    public void setCas(Cas cas) {
        this.cas = cas;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public Migrant getMigrant() {
        return migrant;
    }

    public void setMigrant(Migrant migrant) {
        this.migrant = migrant;
    }

    /*@Override
    public int hashCode() {
        return this.getMigrant().getPersonalRegister().getFirstName().hashCode() +
                this.getMigrant().getPersonalRegister().getLastName().hashCode() +
                this.getMigrant().getPersonalRegister().getAge() +
                this.getMigrant().getPersonalRegister().getGender().hashCode() +
                this.getMigrant().getPersonalRegister().getNationality().hashCode();
    }

    @Override
    public boolean equals(Object guest) {
        Guest that = (Guest) guest;
        return (this.getMigrant().getPersonalRegister().getFirstName().equals(that.getMigrant().getPersonalRegister().getFirstName()) &&
                this.getMigrant().getPersonalRegister().getLastName().equals(that.getMigrant().getPersonalRegister().getLastName()) &&
                this.getMigrant().getPersonalRegister().getDateOfBirth().equals(that.getMigrant().getPersonalRegister().getDateOfBirth()) &&
                this.getMigrant().getPersonalRegister().getGender().equals(that.getMigrant().getPersonalRegister().getGender()) &&
                this.getMigrant().getPersonalRegister().getNationality().equals(that.getMigrant().getPersonalRegister().getNationality()));
    }*/

}
