package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="strutture")
@Proxy(lazy = false)
public class Structure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, name="name")
    private String name;

    @Column(nullable = false, name="category")
    private String category;

    @Column(nullable = false, name="site")
    private String site;

    @Column(nullable = false, name="phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "structure", fetch = FetchType.EAGER)
    private List<Guest> guests;

    @OneToOne(mappedBy="structure", cascade = CascadeType.REMOVE)
    private User user;

    public Structure() {
        this.guests = new ArrayList<>();
    }

    public Structure(String name, String category, String site, String phoneNumber, List<Guest> guests) {
        this.name = name;
        this.category = category;
        this.site = site;
        this.guests = guests;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "NOME: " + this.name + " " + "CATEGORIA: " + this.category + " " +
                "UBICAZIONE: " + this.site + " " + "NUMERO TELEFONICO: " +
                this.phoneNumber;
    }
}
