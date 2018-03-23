package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Structure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface GuestDAO extends CrudRepository<Guest, Long> {

    List<Guest> findByStructure(Structure structure);

}
