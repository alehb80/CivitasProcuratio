package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.CasDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CasService {

    @Autowired
    private CasDAO casDAO;

    @Transactional
    public void save(final Cas cas) {
        this.casDAO.save(cas);
    }

    @Transactional
    public Iterable<Cas> findAll() {
        return this.casDAO.findAll();
    }

    @Transactional
    public Cas findOne(Long id) {
        return this.casDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.casDAO.delete(id);
    }

    @Transactional
    public Cas findByNameAndCategoryAndSite(String name, String category, String site) {
        return this.casDAO.findByNameAndCategoryAndSite(name, category, site);
    }

    public boolean alreadyExists(Cas cas) {
        List<Cas> casList = (List<Cas>) this.casDAO.findAll();
        for (Cas s : casList) {
            if (s.getName().equals(cas.getName()) && s.getCategory().equals(cas.getCategory()) &&
                    s.getSite().equals(cas.getSite()) && s.getPhoneNumber().equals(cas.getPhoneNumber()))
                return true;
        }
        return false;
    }

}
