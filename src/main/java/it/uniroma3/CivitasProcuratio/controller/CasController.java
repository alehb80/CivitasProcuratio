package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.service.CasService;
import it.uniroma3.CivitasProcuratio.util.CasValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CasController {

    @Autowired
    private CasService casService;

    @Autowired
    private CasValidator validator;

    @RequestMapping("/user/structureList")
    public String casList(Model model) {
        model.addAttribute("casList", this.casService.findAll());
        return "user/structureList";
    }

    @RequestMapping("/superadmin/structureAdd")
    public String addCas(Model model) {
        model.addAttribute("cas", new Cas());
        return "superadmin/structureAdd";
    }

    @RequestMapping(value = "/superadmin/structureAdd", method = RequestMethod.POST)
    public String newCas(@Valid @ModelAttribute("cas") Cas cas, Model model, BindingResult bindingResult) {
        this.validator.validate(cas, bindingResult);
        if (this.casService.alreadyExists(cas)) {
            model.addAttribute("message", "*ATTENZIONE: CAS già esistente*");
            return "superadmin/structureAdd";
        }
        else {
            if (!bindingResult.hasErrors()) {
                this.casService.save(cas);
                model.addAttribute("casList", this.casService.findAll());
                return "redirect:/superadmin/structuresManagement";
            }
        }
        return "superadmin/structureAdd";
    }

    @RequestMapping(value = "/superadmin/updateStructure/{id}", method = RequestMethod.POST)
    public String updateCas(@Valid @ModelAttribute("cas") Cas cas, Model model, BindingResult bindingResult) {
        this.validator.validate(cas, bindingResult);
        if (this.casService.alreadyExists(cas)) {
            model.addAttribute("message", "*ATTENZIONE: CAS già esistente*");
            return "superadmin/updateStructure";
        }
        else {
            if (!bindingResult.hasErrors()) {
                this.casService.save(cas);
                model.addAttribute("casList", this.casService.findAll());
                return "redirect:/superadmin/structuresManagement";
            }
        }
        return "superadmin/updateStructure";
    }

    @RequestMapping(value = "/superadmin/structureDelete/{id}", method = RequestMethod.POST)
    public String deleteCas(@PathVariable("id") Long id, Model model) {
        Cas cas = this.casService.findOne(id);
        if (!cas.getGuests().isEmpty()) {
            model.addAttribute("message", "*Impossibile eliminare il CAS: contiene ospiti!*");
            model.addAttribute("casList", this.casService.findAll());
            return "superadmin/structuresManagement";
        }
        else
            this.casService.deleteById(id);
        return "redirect:/superadmin/structuresManagement";
    }

    @RequestMapping("/admin/structureInfo/{id}")
    public String adminCas(@PathVariable("id") Long id, Model model) {
        Cas cas = this.casService.findOne(id);
        model.addAttribute("cas", cas);
        return "admin/structureInfo";
    }

    @RequestMapping("/superadmin/structuresManagement")
    public String casListManagement(Model model) {
        model.addAttribute("casList", this.casService.findAll());
        return "superadmin/structuresManagement";
    }

    @RequestMapping("/superadmin/showUpdateStructure/{id}")
    public String showUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cas", this.casService.findOne(id));
        return "superadmin/updateStructure";
    }

}

