package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.ActivityDAO;
import it.uniroma3.CivitasProcuratio.model.Activity;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.ActivityService;
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
public class ActivityTest {

    @Autowired
    private ActivityService activityService;

    @MockBean
    private ActivityDAO activityDAO;

    @Before
    public void setUp() {
        Structure structure = new Structure();
        structure.setId(new Long(1));

        Guest guest = new Guest();
        guest.setId(new Long(1));
        guest.setStructure(structure);

        Activity activity = new Activity();
        activity.setId(new Long(1));
        activity.setGuest(guest);

        Mockito.when(this.activityDAO.findOne(new Long(1)))
                .thenReturn(activity);
    }

    @Test
    public void whenValidId_thenGuestShouldBeFound() {
        Long id = new Long(1);
        Activity found = activityService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

}
