package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.StructureService;
import it.uniroma3.CivitasProcuratio.util.StructureValidator;
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
public class StructureController {

    @Autowired
    private StructureService structureService;

    @Autowired
    private StructureValidator validator;

    @RequestMapping("/user/structureList")
    public String structuresList(Model model) {
        model.addAttribute("structures", this.structureService.findAll());
        return "user/structureList";
    }

    @RequestMapping("/superadmin/structureAdd")
    public String addStructure(Model model) {
        model.addAttribute("structure", new Structure());
        return "superadmin/structureAdd";
    }

    @RequestMapping(value = "/superadmin/structureAdd", method = RequestMethod.POST)
    public String newStructure(@Valid @ModelAttribute("structure") Structure structure, Model model, BindingResult bindingResult) {
        this.validator.validate(structure, bindingResult);
        if (this.structureService.alreadyExists(structure)) {
            model.addAttribute("message", "*ATTENZIONE: struttura inserita già esistente*");
            return "superadmin/structureAdd";
        }
        else {
            if (!bindingResult.hasErrors()) {
                this.structureService.save(structure);
                model.addAttribute("structures", this.structureService.findAll());
                return "redirect:/superadmin/structuresManagement";
            }
        }
        return "superadmin/structureAdd";
    }

    @RequestMapping(value = "/superadmin/updateStructure/{id}", method = RequestMethod.POST)
    public String updateStructure(@Valid @ModelAttribute("structure") Structure structure, Model model, BindingResult bindingResult) {
        this.validator.validate(structure, bindingResult);
        if (this.structureService.alreadyExists(structure)) {
            model.addAttribute("message", "*ATTENZIONE: struttura inserita già esistente*");
            return "superadmin/updateStructure";
        }
        else {
            if (!bindingResult.hasErrors()) {
                this.structureService.save(structure);
                model.addAttribute("structures", this.structureService.findAll());
                return "redirect:/superadmin/structuresManagement";
            }
        }
        return "superadmin/updateStructure";
    }

    @RequestMapping(value = "/superadmin/structureDelete/{id}", method = RequestMethod.POST)
    public String deleteStructure(@PathVariable("id") Long id, Model model) {
        Structure structure = this.structureService.findOne(id);
        if (!structure.getGuests().isEmpty()) {
            model.addAttribute("message", "*Non è possibile eliminare la struttura: contiene ospiti!*");
            model.addAttribute("structures", this.structureService.findAll());
            return "superadmin/structuresManagement";
        }
        else
            this.structureService.deleteById(id);
        return "redirect:/superadmin/structuresManagement";
    }

    @RequestMapping("/admin/structureInfo/{id}")
    public String adminStructure(@PathVariable("id") Long id, Model model) {
        Structure structure = this.structureService.findOne(id);
        model.addAttribute("structure", structure);
        return "admin/structureInfo";
    }

    @RequestMapping("/superadmin/structuresManagement")
    public String structuresManagement(Model model) {
        model.addAttribute("structures", this.structureService.findAll());
        return "superadmin/structuresManagement";
    }

    @RequestMapping("/superadmin/showUpdateStructure/{id}")
    public String showUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("structure", this.structureService.findOne(id));
        return "superadmin/updateStructure";
    }

}

