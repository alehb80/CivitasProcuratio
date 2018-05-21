package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Migrant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MigrantDAO extends CrudRepository<Migrant, Long> {
}
