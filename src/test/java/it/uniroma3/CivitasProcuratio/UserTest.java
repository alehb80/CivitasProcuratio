package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.UserDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.User;
import it.uniroma3.CivitasProcuratio.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    @Before
    public void setUp() {
        User user = new User();
        user.setId(new Long(1));

        Mockito.when(userDAO.findOne(new Long(1)))
                .thenReturn(user);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        Long id = new Long(1);
        User found = userService.findOne(id);

        assertThat(found.getId())
                .isEqualTo(id);
    }

    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        User user = new User();
        String username = "username";
        user.setUsername(username);
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.findByUsername(username)).thenReturn(user);
        User found = userServiceMock.findByUsername(username);

        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    public void whenValidStructure_thenUserShouldBeFound() {
        Cas cas = new Cas();
        User user = new User();
        user.setCas(cas);
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.findByCas(cas)).thenReturn(user);
        User founds = userServiceMock.findByCas(cas);

        assertThat(founds.getCas()).isEqualTo(cas);
    }

}
