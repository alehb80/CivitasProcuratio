package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.AttendanceSheetDAO;
import it.uniroma3.CivitasProcuratio.model.AttendanceSheet;
import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceSheetService {

    @Autowired
    private AttendanceSheetDAO attendanceSheetDAO;

    @Transactional
    public void save(final AttendanceSheet attendanceSheet) {
        this.attendanceSheetDAO.save(attendanceSheet);
    }

    @Transactional
    public Iterable<AttendanceSheet> findAll() {
        return this.attendanceSheetDAO.findAll();
    }

    @Transactional
    public AttendanceSheet findOne(Long id) {
        return this.attendanceSheetDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.attendanceSheetDAO.delete(id);
    }

    @Transactional
    public List<AttendanceSheet> findByCas(Cas cas) {
        return this.attendanceSheetDAO.findByCas(cas);
    }

    public boolean alreadyExists(AttendanceSheet attendanceSheet) {
        List<AttendanceSheet> attendanceSheets = (List<AttendanceSheet>) this.attendanceSheetDAO.findAll();
        for (AttendanceSheet a : attendanceSheets) {
            if (a.getDate().equals(attendanceSheet.getDate()))
                return true;
        }
        return false;
    }

}
