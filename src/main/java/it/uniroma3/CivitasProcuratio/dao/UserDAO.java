package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserDAO extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByStructure(Structure structure);

}
