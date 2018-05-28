package it.uniroma3.CivitasProcuratio.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="presenze")
@Proxy(lazy = false)
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name="date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "attendance_sheet_id")
    private AttendanceSheet attendanceSheet;

    public Presence() {
    }

    public Presence(Date date, Guest guest) {
        this.date = date;
        this.guest = guest;
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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public AttendanceSheet getAttendanceSheet() {
        return attendanceSheet;
    }

    public void setAttendanceSheet(AttendanceSheet attendanceSheet) {
        this.attendanceSheet = attendanceSheet;
    }


}
