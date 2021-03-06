package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Migrant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface GuestDAO extends CrudRepository<Guest, Long> {

    List<Guest> findByCas(Cas cas);

    List<Guest> findAllByCasAndMigrantArrived(Cas cas, boolean arrived);

    Guest findByMigrant(Migrant migrant);

}
