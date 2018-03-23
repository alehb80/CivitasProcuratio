package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.StructureDAO;
import it.uniroma3.CivitasProcuratio.model.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StructureService {

    @Autowired
    private StructureDAO structureDAO;

    @Transactional
    public void save(final Structure structure) { this.structureDAO.save(structure); }

    @Transactional
    public Iterable<Structure> findAll() {
        return this.structureDAO.findAll();
    }

    @Transactional
    public Structure findOne(Long id) {
        return this.structureDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) { this.structureDAO.delete(id); }

    public boolean alreadyExists(Structure structure) {
        List<Structure> structures = (List<Structure>) this.structureDAO.findAll();
        for (Structure s : structures) {
            if (s.getName().equals(structure.getName()) && s.getCategory().equals(structure.getCategory()) &&
                    s.getSite().equals(structure.getSite()) && s.getPhoneNumber().equals(structure.getPhoneNumber()))
                return true;
        }
        return false;
    }

}
