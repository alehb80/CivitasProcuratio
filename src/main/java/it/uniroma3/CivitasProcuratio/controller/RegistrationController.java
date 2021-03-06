package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.User;
import it.uniroma3.CivitasProcuratio.service.CasService;
import it.uniroma3.CivitasProcuratio.service.UserService;
import it.uniroma3.CivitasProcuratio.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CasService casService;

    @Autowired
    private UserValidator validator;


    @RequestMapping("/superadmin/adminForm/{id}")
    public String showForm(@PathVariable("id") Long id, Model model) {
        Cas cas = this.casService.findOne(id);
        if (cas.getUser() != null) {
            model.addAttribute("message", "*ATTENZIONE: esiste già un Amministratore per questo CAS*");
            model.addAttribute("casList", this.casService.findAll());
            return "superadmin/structuresManagement";
        }
        else {
            User user = new User();
            user.setCas(cas);
            model.addAttribute("user", user);
            return "superadmin/adminAdd";
        }
    }

    @RequestMapping(value = "/superadmin/adminAdd/{id}", method = RequestMethod.POST)
    public String newAdmin(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult) {
        this.validator.validate(user, bindingResult);
        Cas cas = this.casService.findOne(id);
        user.setCas(cas);
        user.setRole("ROLE_ADMIN");
        if (this.userService.alreadyExists(user)) {
            model.addAttribute("message", "*ATTENZIONE: esiste già un Amministratore con questo username/email*");
            return "superadmin/adminAdd";
        } else {
            if (!bindingResult.hasErrors()) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userService.save(user);
                return "redirect:/superadmin/structuresManagement";
            }
        }
        return "superadmin/adminAdd";
    }

    @RequestMapping("/registrationUser")
    public String user(Model model) {
        model.addAttribute("user", new User());
        return "registrationUser";
    }

    @RequestMapping(value = "/registrationUser", method = RequestMethod.POST)
    public String newUser(@Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult, HttpSession session) {
        this.validator.validate(user, bindingResult);
        user.setRole("ROLE_USER");
        if (this.userService.alreadyExists(user)) {
            model.addAttribute("message", "*ATTENZIONE: esiste già un utente con questo username*");
            return "registrationUser";
        }
        else {
            if (!bindingResult.hasErrors()) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userService.save(user);
                session.setAttribute("user", user);
                return "user/homeUser";
            }
        }
        return "registrationUser";
    }

    @RequestMapping("/superadmin")
    public String superAdmin(Model model) {
        model.addAttribute("user", new User());
        return "registrationSuperadmin";
    }

    @RequestMapping(value = "/superadmin", method = RequestMethod.POST)
    public String newSuperAdmin(@Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult) {
        this.validator.validate(user, bindingResult);
        user.setRole("ROLE_SUPERADMIN");
        if (this.userService.alreadyExists(user)) {
            model.addAttribute("message", "*ATTENZIONE: esiste già un utente con questo username*");
            return "registrationSuperadmin";
        }
        else {
            if (!bindingResult.hasErrors()) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userService.save(user);
                return "superadmin/homeSuperadmin";
            }
        }
        return "registrationSuperadmin";
    }

    @RequestMapping("/superadmin/adminInfo/{id}")
    public String adminInfo(@PathVariable("id") Long id, Model model) {
        if (this.userService.findByCas(this.casService.findOne(id)) == null) {
            model.addAttribute("message", "*Nessun Aministratore assegnato al CAS*");
            model.addAttribute("casList", this.casService.findAll());
            return "superadmin/structuresManagement";
        }
        else {
            Cas cas = this.casService.findOne(id);
            model.addAttribute("user", this.userService.findByCas(cas));
            return "superadmin/adminInfo";
        }
    }

}
