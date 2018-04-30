package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.GuestDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestDAO guestDAO;

    @Transactional
    public void save(final Guest guest) {
        this.guestDAO.save(guest);
    }

    @Transactional
    public Iterable<Guest> findAll() {
        return this.guestDAO.findAll();
    }

    @Transactional
    public Guest findOne(Long id) {
        return this.guestDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.guestDAO.delete(id);
    }

    @Transactional
    public List<Guest> findByCas(Cas cas) {
        return this.guestDAO.findByCas(cas);
    }

    public boolean alreadyExists(Guest guest) {
        List<Guest> guests = (List<Guest>) this.guestDAO.findAll();
        for (Guest g : guests) {
            if (g.getFirstName().equals(guest.getFirstName()) && g.getLastName().equals(guest.getLastName()) &&
                    g.getDateOfBirth().compareTo(guest.getDateOfBirth()) == 0 &&
                    g.getGender().equals(guest.getGender()) && g.getNationality().equals(guest.getNationality()))
                return true;
        }
        return false;
    }

}
