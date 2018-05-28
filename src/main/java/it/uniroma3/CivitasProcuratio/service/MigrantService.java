package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.MigrantDAO;
import it.uniroma3.CivitasProcuratio.model.Migrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MigrantService {

    @Autowired
    private MigrantDAO migrantDAO;

    @Transactional
    public void save(final Migrant migrant) {
        this.migrantDAO.save(migrant);
    }

    @Transactional
    public Iterable<Migrant> findAll() {
        return this.migrantDAO.findAll();
    }

    @Transactional
    public Migrant findOne(Long id) {
        return this.migrantDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.migrantDAO.delete(id);
    }

    public boolean alreadyExists(Migrant migrant) {
        List<Migrant> migrants = (List<Migrant>) this.migrantDAO.findAll();
        for (Migrant m : migrants) {
            if (m.getPersonalRegister().getFullName().equals(migrant.getPersonalRegister().getFullName()))
                return true;
        }
        return false;
    }

}
