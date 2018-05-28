package it.uniroma3.CivitasProcuratio.model;

import java.util.List;

public class PresenceForm {

    private List<Long> checkedGuests;

    public PresenceForm() { }

    public List<Long> getCheckedGuests() {
        return checkedGuests;
    }

    public void setCheckedGuests(List<Long> checkedGuests) {
        this.checkedGuests = checkedGuests;
    }

}