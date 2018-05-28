package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Migrant;
import it.uniroma3.CivitasProcuratio.model.PersonalRegister;
import it.uniroma3.CivitasProcuratio.model.MigrantAssignmentForm;
import it.uniroma3.CivitasProcuratio.service.CasService;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import it.uniroma3.CivitasProcuratio.service.MigrantService;
import it.uniroma3.CivitasProcuratio.service.PersonalRegisterService;
import it.uniroma3.CivitasProcuratio.util.DateUtils;
import it.uniroma3.CivitasProcuratio.util.PersonalRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class MigrantController {

    @Autowired
    private MigrantService migrantService;

    @Autowired
    private CasService casService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private PersonalRegisterService personalRegisterService;

    @Autowired
    private PersonalRegisterValidator validator;

    @RequestMapping("/arrivalsManager/addMigrant")
    public String addMigrant(Model model) {
        PersonalRegister personalRegister = new PersonalRegister();
        model.addAttribute("personalRegister", personalRegister);
        return "arrivalsManager/addMigrant";
    }

    @RequestMapping(value = "/arrivalsManager/addMigrant", method = RequestMethod.POST)
    public String newMigrant(@Valid @ModelAttribute("personalRegister") PersonalRegister personalRegister,
                             Model model,
                             BindingResult bindingResult) {
        this.validator.validate(personalRegister, bindingResult);
        personalRegister.setAge(DateUtils.ageCalculator(personalRegister.getDateOfBirth()));
        if (!DateUtils.dateValidation(personalRegister.getDateOfBirth())) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "arrivalsManager/addMigrant";
        }
        else {
            if (this.personalRegisterService.alreadyExists(personalRegister)) {
                model.addAttribute("message", "*ATTENZIONE: migrante già esistente*");
                return "arrivalsManager/addMigrant";
            }
            else {
                if (!bindingResult.hasErrors()) {
                    Migrant migrant = new Migrant();
                    migrant.setAssigned(false);
                    migrant.setPersonalRegister(personalRegister);
                    Date checkInDate = Calendar.getInstance().getTime();
                    migrant.setCheckInDate(checkInDate);
                    this.personalRegisterService.save(personalRegister);
                    this.migrantService.save(migrant);
                    model.addAttribute("migrantsList", this.migrantService.findAll());
                    return "redirect:/arrivalsManager/migrantsList";
                }
            }
        }
        return "arrivalsManager/addMigrant";
    }

    @RequestMapping("/arrivalsManager/migrantsList")
    public String migrantsList(Model model) {
        model.addAttribute("migrantsList", this.migrantService.findAll());
        return "arrivalsManager/migrantsList";
    }

    @RequestMapping(value = "/arrivalsManager/migrantDelete/{id}", method = RequestMethod.POST)
    public String migrantDelete(@PathVariable("id") Long id, Model model) {
        this.migrantService.deleteById(id);
        model.addAttribute("migrantsList", this.migrantService.findAll());
        return "arrivalsManager/migrantsList";
    }

    @RequestMapping("/superadmin/migrants")
    public String migrants(Model model) {
        model.addAttribute("casList", this.casService.findAll());
        model.addAttribute("migrantsList", this.migrantService.findAll());
        model.addAttribute("migrantAssignmentForm", new MigrantAssignmentForm());
        return "superadmin/migrants";
    }

    @RequestMapping(value = "/superadmin/migrants", method = RequestMethod.POST)
    public String migrantsDone(@ModelAttribute("migrantAssignmentForm") MigrantAssignmentForm form,
                               Model model) {

        Long casId = form.getCheckedCAS();

        for (Long migrantId : form.getCheckedMigrants()) {
            Migrant migrant = this.migrantService.findOne(migrantId);
            migrant.setAssigned(true);

            Guest guest = new Guest();
            guest.setMigrant(this.migrantService.findOne(migrantId));
            guest.setCas(this.casService.findOne(casId));

            this.migrantService.save(migrant);
            this.guestService.save(guest);

            model.addAttribute("casList", this.casService.findAll());
            model.addAttribute("migrantsList", this.migrantService.findAll());
            model.addAttribute("migrantAssignmentForm", new MigrantAssignmentForm());
        }


        return "superadmin/migrants";
    }

    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
