package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.CasDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.service.CasService;
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
public class CasTest {

    @Autowired
    private CasService casService;


    @MockBean
    private CasDAO casDAO;

    @Before
    public void setUp() {
        Cas cas = new Cas();
        cas.setId(new Long(1));

        Mockito.when(casDAO.findOne(new Long(1)))
                .thenReturn(cas);
    }

    @Test
    public void whenValidId_thenStructureShouldBeFound() {
        Long id = new Long(1);
        Cas found = casService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

}
