package it.uniroma3.CivitasProcuratio.dao;

import it.uniroma3.CivitasProcuratio.model.AttendanceSheet;
import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AttendanceSheetDAO extends CrudRepository<AttendanceSheet, Long> {

    List<AttendanceSheet> findByCas(Cas cas);

}
