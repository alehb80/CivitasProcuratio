package it.uniroma3.CivitasProcuratio;

import it.uniroma3.CivitasProcuratio.dao.*;
import it.uniroma3.CivitasProcuratio.model.*;
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

    private MigrantDAO migrantDAO;

    private PersonalRegisterDAO personalRegisterDAO;

    @Autowired
    public DataLoader(UserDAO userDAO, CasDAO casDAO, GuestDAO guestDAO, MigrantDAO migrantDAO, PersonalRegisterDAO personalRegisterDAO) {
        this.userDAO = userDAO;
        this.casDAO = casDAO;
        this.guestDAO = guestDAO;
        this.migrantDAO = migrantDAO;
        this.personalRegisterDAO = personalRegisterDAO;
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
            Cas cas = new Cas("CAS Anzio Portofino", "RTI Tre Fontane/Senis Hospes", "Anzio", "06-9898813", 139);
            this.casDAO.save(cas);
            // Username = "admin1", Password = "admin1"
            if (this.userDAO.findByUsername("admin1") == null) {
                User admin = new User("admin1", "admin1@admin.it", "$2a$10$KhM/5cFcLc9jrBLd4.MsMe7ref/xdsxmcmqR98rhoR2F8sYGROqAa", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
        }
        // Nome = "CAS Pietralata", Categoria = "Croce Rossa", Ubicazione = "Roma", Numero Telefonico = "06-2262585"
        if (this.casDAO.findByNameAndCategoryAndSite("CAS Pietralata", "Croce Rossa", "Roma") == null) {
            Cas cas = new Cas("CAS Pietralata", "Croce Rossa", "Roma", "06-2262585", 150);
            this.casDAO.save(cas);
            // Username = "admin2", Password = "admin2"
            if (this.userDAO.findByUsername("admin2") == null) {
                User admin = new User("admin2", "admin2@admin.it", "$2a$10$PYkfJDI3Ebta5nEm94u/SOgcNCKPaPg5f/ctU9XaIom7jpiTUOzr.", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
        }
        // Nome = "Parrocchia S.Leone I", Categoria = "Caritas", Ubicazione = "Roma", Numero Telefonico = "06-5298986"
        if (this.casDAO.findByNameAndCategoryAndSite("Parrocchia S.Leone I", "Caritas", "Roma") == null) {
            Cas cas = new Cas("Parrocchia S.Leone I", "Caritas", "Roma", "06-5298986", 2);
            this.casDAO.save(cas);
            // Username = "admin3", Password = "admin3"
            if (this.userDAO.findByUsername("admin3") == null) {
                User admin = new User("admin3", "admin3@admin.it", "$2a$10$78rwtF1qQemSka4yYpeTq.2NbUH3.1r8Ud6kEocrEC1wJZU3DTbEG", "ROLE_ADMIN");
                admin.setCas(cas);
                this.userDAO.save(admin);
            }
        }
        // FirstName = "Dame", LastName = "Diop", DateOfBirth = "1987-04-09", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-03-06"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Dame", "Diop", "M", "Senegal") == null) {
            String myDate = "1987-04-09";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate));
            PersonalRegister personalRegister = new PersonalRegister("Dame", "Diop", "Dame Diop", dateOfBirth, "M", "Senegal");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Dame Diop");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-03-02";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Celestine", LastName = "Babayaro", DateOfBirth = "1992-03-09", Gender = "F", Nationality = "Nigeria", CheckInDate = "2018-04-06"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Celestine", "Babayaro", "F", "Nigeria") == null) {
            String myDate = "1992-03-09";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate));
            PersonalRegister personalRegister = new PersonalRegister("Celestine", "Babayaro", "Celestine Babayaro", dateOfBirth, "F", "Nigeria");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Celestine Babayaro");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-04-02";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Karim", LastName = "Essediri", DateOfBirth = "1990-06-07", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-02-18"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Karim", "Essediri", "M", "Tunisia") == null) {
            String myDate = "1990-06-07";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate));
            PersonalRegister personalRegister = new PersonalRegister("Karim", "Essediri", "Karim Essediri", dateOfBirth, "M", "Tunisia");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Karim Essediri");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-02-13";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        /*
        // FirstName = "Emmanuel", LastName = "Mendy", DateOfBirth = "1995-11-26", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-02-02"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Emmanuel", "Mendy", "M", "Senegal") == null) {
            String myDate1 = "1995-11-26";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Emmanuel","Mendy", "Emmanuel Mendy", dateOfBirth, "M", "Senegal");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Emmanuel Mendy");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-01-27";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Hassen", LastName = "Gabsi", DateOfBirth = "1985-03-16", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-05-03"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Hassen", "Gabsi", "M", "Tunisia") == null) {
            String myDate1 = "1985-03-16";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Hassen", "Gabsi", "Hassen Gabsi", dateOfBirth, "M", "Tunisia");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Hassen Gabsi");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-04-29";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Fabien", LastName = "Camus", DateOfBirth = "1997-08-27", Gender = "M", Nationality = "Tunisia", CheckInDate = "2018-05-02"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Fabien", "Camus", "M", "Tunisia") == null) {
            String myDate1 = "1997-08-27";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Fabien", "Camus", "Fabien Camus", dateOfBirth, "M", "Tunisia");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Fabien Camus");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-04-29";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Alfred", LastName = "Gomis", DateOfBirth = "1997-01-26", Gender = "M", Nationality = "Senegal", CheckInDate = "2018-04-22"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Alfred", "Gomis", "M", "Senegal") == null) {
            String myDate1 = "1997-01-26";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Alfred", "Gomis", "Alfred Gomis", dateOfBirth, "M", "Senegal");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Alfred Gomis");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-04-15";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Isah", LastName = "Eliakwu", DateOfBirth = "1996-12-22", Gender = "F", Nationality = "Nigeria", CheckInDate = "2018-01-22"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Isah", "Eliakwu", "F", "Nigeria") == null) {
            String myDate1 = "1996-12-22";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Isah", "Eliakwu", "Isah Eliakwu", dateOfBirth, "F", "Nigeria");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Isah Eliakwu");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-01-14";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        // FirstName = "Anice", LastName = "Badri", DateOfBirth = "1997-09-12", Gender = "F", Nationality = "Tunisia", CheckInDate = "2018-03-18"
        if (this.personalRegisterDAO.findByFirstNameAndLastNameAndGenderAndNationality("Anice", "Badri", "F", "Tunisia") == null) {
            String myDate1 = "1997-09-12";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf1.parse(String.valueOf(myDate1));
            PersonalRegister personalRegister = new PersonalRegister("Anice", "Badri", "Anice Badri", dateOfBirth, "F", "Tunisia");
            this.personalRegisterDAO.save(personalRegister);
            Migrant migrant = new Migrant();
            migrant.setFullName("Anice Badri");
            migrant.setPersonalRegister(personalRegister);
            String myDate3 = "2018-05-28";
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate2 = sdf3.parse(String.valueOf(myDate3));
            migrant.setCheckInDate(checkInDate2);
            this.migrantDAO.save(migrant);
        }
        */
    }
}
