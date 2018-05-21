package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Migrant;
import it.uniroma3.CivitasProcuratio.service.MigrantService;
import it.uniroma3.CivitasProcuratio.util.DateUtils;
import it.uniroma3.CivitasProcuratio.util.MigrantValidator;
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
public class MigrantController {

    @Autowired
    private MigrantService migrantService;

    @Autowired
    private MigrantValidator validator;

    @RequestMapping("/arrivalsManager/addMigrant")
    public String addMigrant(Model model) {
        Migrant migrant = new Migrant();
        migrant.setAssigned(false);
        model.addAttribute("migrant", migrant);
        return "arrivalsManager/addMigrant";
    }

    @RequestMapping(value = "/arrivalsManager/addMigrant", method = RequestMethod.POST)
    public String newMigrant(@Valid @ModelAttribute("migrant") Migrant migrant, Model model, BindingResult bindingResult) {
        this.validator.validate(migrant, bindingResult);
        if (!DateUtils.dateValidation((migrant.getCheckInDate()))) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "arrivalsManager/addMigrant";
        }
        else {
            if (this.migrantService.alreadyExists(migrant)) {
                model.addAttribute("message", "*ATTENZIONE: migrante già esistente*");
                return "arrivalsManager/addMigrant";
            } else {
                if (!bindingResult.hasErrors()) {
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
        model.addAttribute("migrantsList", this.migrantService.findAll());
        return "superadmin/migrants";
    }

    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
