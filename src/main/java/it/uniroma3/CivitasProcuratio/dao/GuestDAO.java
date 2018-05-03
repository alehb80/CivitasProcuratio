package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface GuestDAO extends CrudRepository<Guest, Long> {

    Guest findByFirstNameAndLastNameAndGenderAndNationality(String firstName, String lastName, String gender, String nationality);

    List<Guest> findByCas(Cas cas);

}
