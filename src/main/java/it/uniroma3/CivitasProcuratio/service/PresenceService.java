package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.PresenceDAO;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Presence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PresenceService {

    @Autowired
    private PresenceDAO presenceDAO;

    @Transactional
    public void save(final Presence presence) {
        this.presenceDAO.save(presence);
    }

    @Transactional
    public Iterable<Presence> findAll() {
        return this.presenceDAO.findAll();
    }

    @Transactional
    public Presence findOne(Long id) {
        return this.presenceDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.presenceDAO.delete(id);
    }

    @Transactional
    public List<Presence> findByGuest(Guest guest) {
        return this.presenceDAO.findByGuest(guest);
    }

    @Transactional
    public List<Presence> findByDate(Date date) {
        return this.presenceDAO.findByDate(date);
    }

    @Transactional
    public List<Presence> getPresenceGuestsByDate(List<Guest> guests, Date date) {
        List<Presence> activities = new ArrayList<>();
        for (Presence a : this.findByDate(date)) {
            for (Guest g : guests) {
                if (g.getPresences().contains(a))
                    activities.add(a);
            }
        }
        return activities;
    }

    @Transactional
    public List<Presence> getPresenceBetweenPeriod(List<Guest> guests, Date from, Date to) {
        List<Presence> presences = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        while (calendar.getTime().before(to)) {
            presences.addAll(this.getPresenceGuestsByDate(guests, calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        calendar.setTime(to);
        presences.addAll(this.getPresenceGuestsByDate(guests, calendar.getTime()));
        return presences;
    }

    public boolean alreadyExists(Presence presence, Date date) {
        for (Presence p: this.presenceDAO.findByDate(date)) {
            if (p.getDate().equals(presence.getDate()))
                return true;
        }
        return false;
    }

}
