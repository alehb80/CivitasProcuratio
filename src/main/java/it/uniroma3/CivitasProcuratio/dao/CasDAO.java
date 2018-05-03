package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CasDAO extends CrudRepository<Cas, Long> {

    Cas findByNameAndCategoryAndSite(String name, String category, String site);

}
