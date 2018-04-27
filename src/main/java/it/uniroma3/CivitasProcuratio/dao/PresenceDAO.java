package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Presence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface PresenceDAO extends CrudRepository<Presence, Long> {

    List<Presence> findByGuest(Guest guest);

    List<Presence> findByDate(Date date);

}
