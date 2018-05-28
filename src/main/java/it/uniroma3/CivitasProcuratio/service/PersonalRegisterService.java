package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.PersonalRegisterDAO;
import it.uniroma3.CivitasProcuratio.model.PersonalRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonalRegisterService {

    @Autowired
    private PersonalRegisterDAO personalRegisterDAO;

    @Transactional
    public void save(final PersonalRegister personalRegister) {
        this.personalRegisterDAO.save(personalRegister);
    }

    @Transactional
    public Iterable<PersonalRegister> findAll() {
        return this.personalRegisterDAO.findAll();
    }

    @Transactional
    public PersonalRegister findOne(Long id) {
        return this.personalRegisterDAO.findOne(id);
    }

    @Transactional
    public PersonalRegister findByFirstNameAndLastNameAndGenderAndNationality(String firstName, String lastName, String gender, String nationality) {
        return this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality(firstName, lastName, gender, nationality);
    }

    @Transactional
    public void deleteById(Long id) {
        this.personalRegisterDAO.delete(id);
    }

    public boolean alreadyExists(PersonalRegister personalRegister) {
        List<PersonalRegister> personalRegisterList = (List<PersonalRegister>) this.personalRegisterDAO.findAll();
        for (PersonalRegister p : personalRegisterList) {
            if (p.getFirstName().equals(personalRegister.getFirstName()) &&
                    p.getLastName().equals(personalRegister.getLastName()) &&
                    p.getNationality().equals(personalRegister.getNationality()) &&
                    p.getGender().equals(personalRegister.getGender()) &&
                    p.getDateOfBirth().compareTo(personalRegister.getDateOfBirth()) == 0)
                return true;
        }
        return false;
    }

}
