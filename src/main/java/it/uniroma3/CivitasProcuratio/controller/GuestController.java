package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Migrant;
import it.uniroma3.CivitasProcuratio.model.PersonalRegister;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import it.uniroma3.CivitasProcuratio.service.CasService;
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
public class GuestController {

    @Autowired
    private CasService casService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private PersonalRegisterService personalRegisterService;

    @Autowired
    private MigrantService migrantService;

    @Autowired
    private PersonalRegisterValidator validator;

    @RequestMapping("/user/guestList/{id}")
    public String guestList(@PathVariable("id") Long id, Model model) {
        Cas cas = this.casService.findOne(id);
        Guest guest = new Guest();
        guest.setCas(cas);
        model.addAttribute("guest", guest);
        model.addAttribute("cas", cas);
        model.addAttribute("guests", this.guestService.findByCas(cas));
        return "user/guestList";
    }

    @RequestMapping("/admin/guestForm")
    public String showForm(Model model) {
        PersonalRegister personalRegister = new PersonalRegister();
        model.addAttribute("personalRegister", personalRegister);
        return "admin/guestAdd";
    }

    @RequestMapping(value = "/admin/guestAdd/{id}", method = RequestMethod.POST)
    public String newGuest(@PathVariable("id") Long id,
                           @Valid @ModelAttribute("personalRegister") PersonalRegister personalRegister,
                           Model model,
                           BindingResult bindingResult) {

        this.validator.validate(personalRegister, bindingResult);
        personalRegister.setAge(DateUtils.ageCalculator(personalRegister.getDateOfBirth()));
        if (!DateUtils.dateValidation(personalRegister.getDateOfBirth())) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "admin/guestAdd";
        }
        else {
            if (this.personalRegisterService.alreadyExists(personalRegister)) {
                model.addAttribute("message", "ATTENZIONE: ospite già esistente!");
                return "admin/guestAdd";
            }
            else {
                if (!bindingResult.hasErrors()) {
                    //Problema con l'id del personalRegister (si setta in automatico)
                    personalRegister.setId(null);
                    Migrant migrant = new Migrant();
                    migrant.setAssigned(false);
                    migrant.setArrived(false);
                    migrant.setPersonalRegister(personalRegister);
                    Date checkInDate = Calendar.getInstance().getTime();
                    migrant.setCheckInDate(checkInDate);
                    this.personalRegisterService.save(personalRegister);
                    this.migrantService.save(migrant);
                    Guest guest = new Guest();
                    guest.setMigrant(migrant);
                    guest.setCas(this.casService.findOne(id));
                    guest.setCheckInDate(Calendar.getInstance().getTime());
                    this.guestService.save(guest);
                    model.addAttribute("guests", this.guestService.findByCas(this.casService.findOne(id)));
                    return "admin/guests";
                }
            }
        }
        return "admin/guestAdd";
    }
    /*
    @RequestMapping("/admin/showUpdateGuest/{id}")
    public String showUpdate(@PathVariable("id") Long idGuest, Model model) {
        Guest guest = this.guestService.findOne(idGuest);
        model.addAttribute("cas", this.casService.findOne(guest.getCas().getId()));
        model.addAttribute("guest", guest);
        return "admin/updateGuest";
    }
    */
    /*
    @RequestMapping(value = "/admin/updateGuest/{id}", method = RequestMethod.POST)
    public String updateGuest(@PathVariable("id") Long id, @Valid @ModelAttribute("guest") Guest guest, Model model, BindingResult bindingResult) {
        this.validator.validate(guest.getMigrant().getPersonalRegister(), bindingResult);
        Cas cas = casService.findOne(this.guestService.findOne(id).getCas().getId());
        guest.setCas(cas);
        guest.getMigrant().getPersonalRegister().setAge(DateUtils.ageCalculator(guest.getMigrant().getPersonalRegister().getDateOfBirth()));
        if (!DateUtils.dateValidation(guest.getMigrant().getPersonalRegister().getDateOfBirth()) || !DateUtils.dateValidation((guest.getCheckInDate()))) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "admin/updateGuest";
        }
        else {
            if (this.guestService.alreadyExists(guest)) {
                model.addAttribute("message", "ATTENZIONE: ospite inserito già esistente!");
                return "admin/updateGuest";
            }
            else {
                if (!bindingResult.hasErrors()) {
                    this.guestService.save(guest);
                    model.addAttribute("guest", guest);
                    model.addAttribute("cas", cas);
                    model.addAttribute("guests", this.guestService.findByCas(cas));
                    return "admin/guests";
                }
            }
        }
        return "admin/updateGuest";
    }
    */

    @RequestMapping(value = "/admin/guestDelete/{id}", method = RequestMethod.POST)
    public String deleteGuest(@PathVariable("id") Long id, Model model) {
        Guest guest = this.guestService.findOne(id);
        Cas cas = this.casService.findOne(guest.getCas().getId());
        this.guestService.deleteById(id);
        model.addAttribute("guest", guest);
        model.addAttribute("cas", cas);
        model.addAttribute("guests", this.guestService.findByCas(cas));
        return "admin/guests";
        //return "redirect:/admin/guests";
    }

    @RequestMapping(value = "/admin/adminGuests/{id}", method = RequestMethod.GET)
    public String adminGuests(@PathVariable("id") Long id, Model model) {
        Cas cas = this.casService.findOne(id);
        Guest guest = new Guest();
        guest.setCas(cas);
        model.addAttribute("guest", new Guest());
        model.addAttribute("cas", cas);
        model.addAttribute("guests", this.guestService.findByCas(cas));
        return "admin/guests";
    }

    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
