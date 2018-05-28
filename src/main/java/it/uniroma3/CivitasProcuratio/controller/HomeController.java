package it.uniroma3.CivitasProcuratio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Calendar;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/index"})
    public String hello() {
        return "index";
    }

}
