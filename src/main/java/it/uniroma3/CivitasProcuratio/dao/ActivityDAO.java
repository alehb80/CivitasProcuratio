package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Activity;
import it.uniroma3.CivitasProcuratio.model.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface ActivityDAO extends CrudRepository<Activity, Long> {

    List<Activity> findByGuest(Guest guest);

    List<Activity> findByDate(Date date);

}
