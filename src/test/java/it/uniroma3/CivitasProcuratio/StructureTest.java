package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.StructureDAO;
import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.StructureService;
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
public class StructureTest {

    @Autowired
    private StructureService structureService;


    @MockBean
    private StructureDAO structureDAO;

    @Before
    public void setUp() {
        Structure structure = new Structure();
        structure.setId(new Long(1));

        Mockito.when(structureDAO.findOne(new Long(1)))
                .thenReturn(structure);
    }

    @Test
    public void whenValidId_thenStructureShouldBeFound() {
        Long id = new Long(1);
        Structure found = structureService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

}
