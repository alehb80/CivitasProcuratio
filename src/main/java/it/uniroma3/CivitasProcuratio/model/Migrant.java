package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="migranti")
@Proxy(lazy = false)
public class Migrant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, name="firstName")
    private String firstName;

    @Column(nullable = false, name="lastName")
    private String lastName;

    @OneToMany(mappedBy = "migrant")
    private List<Guest> guests;

    public Migrant() {
        this.guests = new ArrayList<>();
    }

    public Migrant(String firstName, String lastName, List<Guest> guests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guests = guests;
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

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

}