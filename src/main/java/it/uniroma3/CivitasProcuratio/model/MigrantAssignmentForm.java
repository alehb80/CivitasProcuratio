package it.uniroma3.CivitasProcuratio.model;

import java.util.List;

public class MigrantAssignmentForm {
    private List<Long> checkedMigrants;
    private Long checkedCAS;

    public MigrantAssignmentForm() {
    }

    public List<Long> getCheckedMigrants() {
        return checkedMigrants;
    }

    public void setCheckedMigrants(List<Long> checkedMigrants) {
        this.checkedMigrants = checkedMigrants;
    }

    public Long getCheckedCAS() {
        return checkedCAS;
    }

    public void setCheckedCAS(Long checkedCAS) {
        this.checkedCAS = checkedCAS;
    }
}
