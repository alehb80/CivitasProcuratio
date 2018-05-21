package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.CasDAO;
import it.uniroma3.CivitasProcuratio.dao.GuestDAO;
import it.uniroma3.CivitasProcuratio.dao.UserDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    private UserDAO userDAO;

    private CasDAO casDAO;

    private GuestDAO guestDAO;

    @Autowired
    public DataLoader(UserDAO userDAO, CasDAO casDAO, GuestDAO guestDAO) {
        this.userDAO = userDAO;
        this.casDAO = casDAO;
        this.guestDAO = guestDAO;
    }

    public void run(ApplicationArguments args) throws ParseException {
        // Username = "superadmin", Password = "superadmin"
        if (this.userDAO.findByUsername("superadmin") == null) {
            User superadmin = new User("superadmin", "superadmin@superadmin.it", "$2a$10$w6vtZsbu/WqYzS8fisj9/eqdh3g7LE5vPoPn2gI9JbHYTSTQ8d6Qm", "ROLE_SUPERADMIN");
            this.userDAO.save(superadmin);
        }
        // Username = "user", Password = "user"
        if(this.userDAO.findByUsername("user") == null) {
            User user = new User("user", "user@user.it", "$2a$10$2eM9gsjg3PzuUuy.o.Cg3O6.N2zITz2rTynkQtldss.k.iFtyDUnW", "ROLE_USER");
            this.userDAO.save(user);
        }
        // Username = "arrivalsManager", Password = "arrivalsManager"
        if(this.userDAO.findByUsername("arrivalsManager") == null) {
            User user = new User("arrivalsManager", "arrivalsManager@arrivalsManager.it", "$2a$10$fBXbURtq/RTHt5M1zlBBxeJaXMFSt0vAlHubqyCVbLlcphMuOTe6q", "ROLE_ARRIVALS_MANAGER");
            this.userDAO.save(user);
        }
        // Nome = "CAS Anzio Portofino", Categoria = "RTI Tre Fontane/Senis Hospes", Ubicazione = "Anzio", Numero Telefonico = "06-9898813"
        if (this.casDAO.findByNameAndCategoryAndSite("CAS Anzio Portofino", "RTI Tre Fontane/Senis Hospes", "Anzio") == null) {
            Cas cas = new Cas("CAS Anzio Portofino", "RTI Tre Fontane/Senis Hospes", "Anzio", "06-9898813");
            this.casDAO.save(cas);
            // Username = "admin1", Password = "admin1"
            if (this.userDAO.findByUsername("admin1") == null) {
                User admin = new User("admin1", "admin1@admin.it", "$2a$10$KhM/5cFcLc9jrBLd4.MsMe7ref/xdsxmcmqR98rhoR2F8sYGROqAa", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
            // FirstName = "Dame", LastName = "Diop", DateOfBirth = "1987-04-09", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-03-06"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Dame", "Diop", "M", "Senegal") == null) {
                String myDate1 = "1987-04-09";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-03-06";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Dame", "Diop", dateOfBirth, "M", "Senegal", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Celestine", LastName = "Babayaro", DateOfBirth = "1992-03-09", Gender = "F", Nationality = "Nigeria", CheckInDate = "2018-04-06"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Celestine", "Babayaro", "F", "Nigeria") == null) {
                String myDate1 = "1992-03-09";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-04-06";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Celestine", "Babayaro", dateOfBirth, "F", "Nigeria", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Karim", LastName = "Essediri", DateOfBirth = "1990-06-07", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-02-18"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Karim", "Essediri", "M", "Tunisia") == null) {
                String myDate1 = "1990-06-07";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-02-18";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Karim", "Essediri", dateOfBirth, "M", "Tunisia", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
        }
        // Nome = "CAS Pietralata", Categoria = "Croce Rossa", Ubicazione = "Roma", Numero Telefonico = "06-2262585"
        if (this.casDAO.findByNameAndCategoryAndSite("CAS Pietralata", "Croce Rossa", "Roma") == null) {
            Cas cas = new Cas("CAS Pietralata", "Croce Rossa", "Roma", "06-2262585");
            this.casDAO.save(cas);
            // Username = "admin2", Password = "admin2"
            if (this.userDAO.findByUsername("admin2") == null) {
                User admin = new User("admin2", "admin2@admin.it", "$2a$10$PYkfJDI3Ebta5nEm94u/SOgcNCKPaPg5f/ctU9XaIom7jpiTUOzr.", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
            // FirstName = "Emmanuel", LastName = "Mendy", DateOfBirth = "1995-11-26", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-02-02"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Emmanuel", "Mendy", "M", "Senegal") == null) {
                String myDate1 = "1995-11-26";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-02-02";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Emmanuel", "Mendy", dateOfBirth, "M", "Senegal", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Hassen", LastName = "Gabsi", DateOfBirth = "1985-03-16", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-05-03"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Hassen", "Gabsi", "M", "Tunisia") == null) {
                String myDate1 = "1985-03-16";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-05-03";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Hassen", "Gabsi", dateOfBirth, "M", "Tunisia", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Fabien", LastName = "Camus", DateOfBirth = "1997-08-27", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-05-02"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Fabien", "Camus", "M", "Tunisia") == null) {
                String myDate1 = "1997-08-27";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-05-02";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Fabien", "Camus", dateOfBirth, "M", "Tunisia", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
        }
        // Nome = "Parrocchia S.Leone I", Categoria = "Caritas", Ubicazione = "Roma", Numero Telefonico = "06-5298986"
        if (this.casDAO.findByNameAndCategoryAndSite("Parrocchia S.Leone I", "Caritas", "Roma") == null) {
            Cas cas = new Cas("Parrocchia S.Leone I", "Caritas", "Roma", "06-5298986");
            this.casDAO.save(cas);
            // Username = "admin3", Password = "admin3"
            if (this.userDAO.findByUsername("admin3") == null) {
                User admin = new User("admin3", "admin3@admin.it", "$2a$10$78rwtF1qQemSka4yYpeTq.2NbUH3.1r8Ud6kEocrEC1wJZU3DTbEG", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
            // FirstName = "Alfred", LastName = "Gomis", DateOfBirth = "1997-01-26", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-04-22"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Alfred", "Gomis", "M", "Senegal") == null) {
                String myDate1 = "1997-01-26";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-04-22";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Alfred", "Gomis", dateOfBirth, "M", "Senegal", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Isah", LastName = "Eliakwu", DateOfBirth = "1996-12-22", Gender = "F", Nationality = "Nigeria", CheckInDate = "2018-01-22"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Isah", "Eliakwu", "F", "Nigeria") == null) {
                String myDate1 = "1996-12-22";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-01-22";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Isah", "Eliakwu", dateOfBirth, "F", "Nigeria", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
            // FirstName = "Anice", LastName = "Badri", DateOfBirth = "1997-09-12", Gender = "F", Nationality = "Tunisia", CheckInDate = "2018-03-18"
            if (this.guestDAO.findByFirstNameAndLastNameAndGenderAndNationality("Anice", "Badri", "F", "Tunisia") == null) {
                String myDate1 = "1997-09-12";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
                String myDate2 = "2018-03-18";
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = sdf2.parse(String.valueOf(myDate2));
                Guest guest = new Guest("Anice", "Badri", dateOfBirth, "F", "Tunisia", checkInDate);
                guest.setCas(cas);
                this.guestDAO.save(guest);
            }
        }
    }
}
