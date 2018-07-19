package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.User;
import it.uniroma3.CivitasProcuratio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/role")
    public String loginRole(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        User user = this.userService.findByUsername(auth.getName());
        String targetUrl = "";

        if(role.contains("ROLE_SUPERADMIN")) {
            session.setAttribute("user", user);
            targetUrl = "/superadmin/homeSuperadmin";
        } else if(role.contains("ROLE_ADMIN")) {
            session.setAttribute("user", user);
            targetUrl = "/admin/homeAdmin";
        } else if (role.contains("ROLE_USER")) {
            session.setAttribute("user", user);
            targetUrl = "/user/homeUser";
        } else if (role.contains("ROLE_ARRIVALS_MANAGER")) {
            session.setAttribute("user", user);
            targetUrl = "/arrivalsManager/homeArrivalsManager";
        }
        return targetUrl;
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }

    /*
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("index");
    }

    @RequestMapping(value="/superadmin**", method = RequestMethod.GET)
    public String loginSuperadmin() {
        return "superadmin/homeSuperadmin";
    }

    @RequestMapping(value="/admin**", method = RequestMethod.GET)
    public String loginAdmin() {
        return "admin/homeAdmin";
    }

    @RequestMapping(value="/user**", method = RequestMethod.GET)
    public String loginUser() {
        return "user/homeUser";
    }
    */

}
