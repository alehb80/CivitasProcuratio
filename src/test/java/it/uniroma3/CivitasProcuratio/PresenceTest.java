package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.PresenceDAO;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Presence;
import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.PresenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresenceTest {

    @Autowired
    private PresenceService presenceService;

    @MockBean
    private PresenceDAO presenceDAO;

    @Before
    public void setUp() {
        Structure structure = new Structure();
        structure.setId(new Long(1));

        Guest guest = new Guest();
        guest.setId(new Long(1));
        guest.setStructure(structure);

        Presence presence = new Presence();
        presence.setId(new Long(1));
        presence.setGuest(guest);

        Mockito.when(this.presenceDAO.findOne(new Long(1)))
                .thenReturn(presence);
    }

    @Test
    public void whenValidId_thenGuestShouldBeFound() {
        Long id = new Long(1);
        Presence found = presenceService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

}
