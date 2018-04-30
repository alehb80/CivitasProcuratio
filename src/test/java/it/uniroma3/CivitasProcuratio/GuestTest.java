package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.GuestDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestTest {

    @Autowired
    private GuestService guestService;

    @MockBean
    private GuestDAO guestDAO;

    @Before
    public void setUp() {
        Cas cas = new Cas();
        cas.setId(new Long(1));

        Guest guest = new Guest();
        guest.setId(new Long(1));
        guest.setCas(cas);

        Mockito.when(guestDAO.findOne(new Long(1)))
                .thenReturn(guest);
    }

    @Test
    public void whenValidId_thenGuestShouldBeFound() {
        Long id = new Long(1);
        Guest found = guestService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void whenValidStructure_thenGuestShouldBeFound() {

        Cas cas = new Cas();
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();

        GuestService guestServiceMock = mock(GuestService.class);
        when(guestServiceMock.findByCas(cas)).thenReturn(Arrays.asList(guest1, guest2));
        List<Guest> founds = guestServiceMock.findByCas(cas);

        assertThat(founds.size()).isEqualTo(2);
        assertThat(founds.contains(guest1)).isTrue();
        assertThat(founds.contains(guest2)).isTrue();
    }

}
