package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="foglio_presenze")
@Proxy(lazy = false)
public class AttendanceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="date")
    private Date date;

    @OneToMany(mappedBy = "attendanceSheet", cascade = CascadeType.REMOVE)
    private List<Presence> presences;

    @ManyToOne
    @JoinColumn(name = "cas_id")
    private Cas cas;

    public AttendanceSheet() {
        this.presences = new ArrayList<>();
    }

    public AttendanceSheet(Date date, List<Presence> presences, Cas cas) {
        this.date = date;
        this.presences = presences;
        this.cas = cas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public Cas getCas() {
        return cas;
    }

    public void setCas(Cas cas) {
        this.cas = cas;
    }

}
