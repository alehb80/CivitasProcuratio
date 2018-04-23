package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.UserDAO;
import it.uniroma3.CivitasProcuratio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserDAO userDAO;

    @Autowired
    public DataLoader(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void run(ApplicationArguments args) {
        // Username = "superadmin", Password = "superadmin"
        if (this.userDAO.findByUsername("superadmin") == null) {
            User superadmin = new User("superadmin", "superadmin@superadmin.it", "$2a$10$w6vtZsbu/WqYzS8fisj9/eqdh3g7LE5vPoPn2gI9JbHYTSTQ8d6Qm", "ROLE_SUPERADMIN");
            userDAO.save(superadmin);
        }
        // Username = "user", Password = "user"
        if(this.userDAO.findByUsername("user") == null) {
            User user = new User("user", "user@user.it", "$2a$10$2eM9gsjg3PzuUuy.o.Cg3O6.N2zITz2rTynkQtldss.k.iFtyDUnW", "ROLE_USER");
            userDAO.save(user);
        }
    }
}
