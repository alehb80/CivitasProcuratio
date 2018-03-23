package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="attivita")
@Proxy(lazy = false)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, name="name")
    private String name;

    @Column(nullable = false, name="value")
    private String value;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    public Activity() {
    }

    public Activity(String name, String value, Date date, Guest guest) {
        this.name = name;
        this.value = value;
        this.date = date;
        this.guest = guest;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

}
