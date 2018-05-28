package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.PersonalRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PersonalRegisterDAO extends CrudRepository<PersonalRegister, Long> {

    PersonalRegister findByFirstNameAndLastNameAndGenderAndNationality(String firstName, String lastName, String gender, String nationality);

}
