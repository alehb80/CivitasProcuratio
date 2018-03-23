package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import it.uniroma3.CivitasProcuratio.service.StructureService;
import it.uniroma3.CivitasProcuratio.util.DateUtils;
import it.uniroma3.CivitasProcuratio.util.GuestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GuestController {

    @Autowired
    private StructureService structureService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestValidator validator;

    @RequestMapping(value = "/user/guestList/{id}", method = RequestMethod.GET)
    public String guestList(@PathVariable("id") Long id, Model model) {
        Structure structure = this.structureService.findOne(id);
        Guest guest = new Guest();
        guest.setStructure(structure);
        model.addAttribute("guest", guest);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        return "user/guestList";
    }

    @RequestMapping(value = "/admin/guestForm/{id}", method = RequestMethod.GET)
    public String showForm(@PathVariable("id") Long id, Model model) {
        Guest guest = new Guest();
        Structure structure = structureService.findOne(id);
        guest.setStructure(structure);
        model.addAttribute("guest", guest);
        return "admin/guestAdd";
    }

    @RequestMapping(value = "/admin/guestAdd/{id}", method = RequestMethod.POST)
    public String newGuest(@PathVariable("id") Long id, @Valid @ModelAttribute("guest") Guest guest, Model model, BindingResult bindingResult) {
        this.validator.validate(guest, bindingResult);
        Structure structure = structureService.findOne(id);
        guest.setStructure(structure);
        guest.setAge(DateUtils.ageCalculator(guest.getDateOfBirth()));
        if (!DateUtils.dateValidation(guest.getDateOfBirth())) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "admin/guestAdd";
        }
        else {
            if (this.guestService.alreadyExists(guest)) {
                model.addAttribute("message", "ATTENZIONE: ospite inserito già esistente!");
                return "admin/guestAdd";
            }
            else {
                if (!bindingResult.hasErrors()) {
                    this.guestService.save(guest);
                    model.addAttribute("guest", guest);
                    model.addAttribute("structure", structure);
                    model.addAttribute("guests", this.guestService.findByStructure(structure));
                    return "admin/guests";
                }
            }
        }
        return "admin/guestAdd";
    }

    @RequestMapping(value = "/admin/updateGuest/{id}", method = RequestMethod.POST)
    public String updateGuest(@PathVariable("id") Long id, @Valid @ModelAttribute("guest") Guest guest, Model model, BindingResult bindingResult) {
        this.validator.validate(guest, bindingResult);
        Structure structure = structureService.findOne(this.guestService.findOne(id).getStructure().getId());
        guest.setStructure(structure);
        guest.setAge(DateUtils.ageCalculator(guest.getDateOfBirth()));
        if (!DateUtils.dateValidation(guest.getDateOfBirth())) {
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
                    model.addAttribute("structure", structure);
                    model.addAttribute("guests", this.guestService.findByStructure(structure));
                    return "admin/guests";
                }
            }
        }
        return "admin/updateGuest";
    }

    @RequestMapping(value = "/admin/guestDelete/{id}", method = RequestMethod.POST)
    public String deleteGuest(@PathVariable("id") Long id, Model model) {
        Guest guest = this.guestService.findOne(id);
        Structure structure = this.structureService.findOne(guest.getStructure().getId());
        this.guestService.deleteById(id);
        model.addAttribute("guest", guest);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        return "admin/guests";
        //return "redirect:/admin/guests";
    }

    @RequestMapping(value = "/admin/adminGuests/{id}", method = RequestMethod.GET)
    public String adminGuests(@PathVariable("id") Long id, Model model) {
        Structure structure = this.structureService.findOne(id);
        Guest guest = new Guest();
        guest.setStructure(structure);
        model.addAttribute("guest", guest);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        return "admin/guests";
    }

    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/admin/showUpdateGuest/{id}")
    public String showUpdate(@PathVariable("id") Long idGuest, Model model) {
        Guest guest = this.guestService.findOne(idGuest);
        model.addAttribute("structure", this.structureService.findOne(guest.getStructure().getId()));
        model.addAttribute("guest", guest);
        return "admin/updateGuest";
    }

}
