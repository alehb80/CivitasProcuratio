package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.model.Activity;
import it.uniroma3.CivitasProcuratio.model.Structure;
import it.uniroma3.CivitasProcuratio.service.ActivityService;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import it.uniroma3.CivitasProcuratio.service.StructureService;
import it.uniroma3.CivitasProcuratio.util.ActivityValidator;
import it.uniroma3.CivitasProcuratio.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private StructureService structureService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityValidator validator;

    @RequestMapping(value = "/admin/insertDate/{id}", method = RequestMethod.GET)
    public String showFormDate(@PathVariable("id") Long id, Model model){
        model.addAttribute("activityDate", new Date());
        model.addAttribute("structure", this.structureService.findOne(id));
        model.addAttribute("from", new Date());
        model.addAttribute("to", new Date());
        return "admin/insertDate";
    }

    @RequestMapping(value = "/admin/dailyPresence/{id}", method = RequestMethod.POST)
    public String showReport(@PathVariable("id") Long id, Model model, @ModelAttribute("activityDate") String activityDate) throws ParseException {
        Structure structure = structureService.findOne(id);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse(String.valueOf(activityDate));
        List<Activity> activities = this.activityService.getPresenceGuestsByDate(this.guestService.findByStructure(structure), myDate);
        model.addAttribute("myDate", myDate);
        model.addAttribute("activities", activities);
        model.addAttribute("activityDate", activityDate);
        if (activities.isEmpty())
            model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
        if (!DateUtils.dateValidation(myDate)) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "admin/insertDate";
        }
        return "admin/dailyPresence";
    }

    @RequestMapping(value = "/admin/showInsertPresence/{id}/{activityDate}", method = RequestMethod.GET)
    public String showFormPresence(@PathVariable("id") Long id, Model model, @PathVariable("activityDate") String activityDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse(String.valueOf(activityDate));
        model.addAttribute("myDate", myDate);
        Structure structure = this.structureService.findOne(id);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        model.addAttribute("activityDate", activityDate);
        model.addAttribute("activity", new Activity());
        return "admin/insertPresence";
    }

    @RequestMapping(value = "/admin/insertPresence/{id1}/{id2}/{activityDate}", method = RequestMethod.POST)
    public String insertPresence(@PathVariable("id1") Long idStructure,
                                 @PathVariable("id2") Long idGuest,
                                 @PathVariable("activityDate") String activityDate,
                                 @Valid @ModelAttribute("activity") Activity activity,
                                 Model model, BindingResult bindingResult) throws ParseException {
        this.validator.validate(activity, bindingResult);
        Structure structure = this.structureService.findOne(idStructure);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse(String.valueOf(activityDate));
        model.addAttribute("myDate", myDate);
        activity.setName("presence");
        activity.setDate(myDate);
        activity.setGuest(this.guestService.findOne(idGuest));
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        model.addAttribute("activity", activity);
        if (this.activityService.alreadyExists(activity,"presence", myDate)) {
            model.addAttribute("message", "*ATTENZIONE: esiste già una presenza per la data selezionata!");
            return "admin/insertPresence";
        }
        else {
            if (!bindingResult.hasErrors()) {
                this.activityService.save(activity);
                return "redirect:/admin/showInsertPresence/{id1}/{activityDate}";
            }
        }
        return "admin/insertPresence";
    }

    @RequestMapping(value = "/admin/periodPresence/{id}", method = RequestMethod.POST)
    public String insertPeriod(@PathVariable("id") Long id, Model model,
                               @ModelAttribute("from") String from,
                               @ModelAttribute("to") String to) throws ParseException {
        Structure structure = this.structureService.findOne(id);
        model.addAttribute("structure", structure);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from1 = sdf.parse(String.valueOf(from));
        Date to1 = sdf.parse(String.valueOf(to));
        if (!DateUtils.dateValidation(from1) || !DateUtils.dateValidation(to1)) {
            model.addAttribute("message", "*ATTENZIONE: una delle due date non è corretta, impossibile inserire una data dopo quella odierna!*");
            return "admin/insertDate";
        }
        if (from1.after(to1)) {
            model.addAttribute("message", "*ATTENZIONE: le date inserite non sono conseguenti tra di loro!*");
            return "admin/insertDate";
        }
        List<Activity> activities = this.activityService.getPresenceBetweenPeriod(this.guestService.findByStructure(structure), from1, to1);
        if (activities.isEmpty()) {
            model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
            return "admin/insertDate";
        }
        model.addAttribute("activities", activities);
        return "admin/periodPresence";
    }

    @RequestMapping(value = "/superadmin/insertDate/{id}", method = RequestMethod.GET)
    public String showFormDateStructure(@PathVariable("id") Long id, Model model){
        model.addAttribute("structure", this.structureService.findOne(id));
        model.addAttribute("activityDate", new Date());
        model.addAttribute("from", new Date());
        model.addAttribute("to", new Date());
        return "superadmin/dateStructure";
    }

    @RequestMapping(value = "/superadmin/dateStructure/{id}", method = RequestMethod.POST)
    public String dateStructure(@PathVariable("id") Long id, Model model, @ModelAttribute("activityDate") String activityDate) throws ParseException {
        Structure structure = structureService.findOne(id);
        model.addAttribute("structure", structure);
        model.addAttribute("guests", this.guestService.findByStructure(structure));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse(String.valueOf(activityDate));
        List<Activity> activities = this.activityService.getPresenceGuestsByDate(this.guestService.findByStructure(structure), myDate);
        model.addAttribute("activities", activities);
        model.addAttribute("activityDate", activityDate);
        if (activities.isEmpty())
            model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
        if (!DateUtils.dateValidation(myDate)) {
            model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
            return "superadmin/dateStructure";
        }
        model.addAttribute("message", activities.size());
        return "superadmin/dateStructure";
    }

    @RequestMapping(value = "/superadmin/periodStructure/{id}", method = RequestMethod.POST)
    public String periodStructure(@PathVariable("id") Long id, Model model,
                                  @ModelAttribute("from") String from,
                                  @ModelAttribute("to") String to) throws ParseException {
        Structure structure = this.structureService.findOne(id);
        model.addAttribute("structure", structure);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from1 = sdf.parse(String.valueOf(from));
        Date to1 = sdf.parse(String.valueOf(to));
        if (!DateUtils.dateValidation(from1) || !DateUtils.dateValidation(to1)) {
            model.addAttribute("text", "*ATTENZIONE: una delle due date non è corretta, impossibile inserire una data dopo quella odierna!*");
            return "superadmin/dateStructure";
        }
        if (from1.after(to1)) {
            model.addAttribute("text", "*ATTENZIONE: le date inserite non sono conseguenti tra di loro!*");
            return "superadmin/dateStructure";
        }
        List<Activity> activities = this.activityService.getPresenceBetweenPeriod(this.guestService.findByStructure(structure), from1, to1);
        if (activities.isEmpty()) {
            model.addAttribute("text", "*Non ci sono presenze per la data scelta*");
            return "superadmin/dateStructure";
        }
        model.addAttribute("activities", activities);
        model.addAttribute("text", activities.size());
        return "superadmin/dateStructure";
    }

    @RequestMapping(value = "/admin/signatureSheet/{id}")
    public String signatureSheet(@PathVariable("id") Long id, Model model) {
        Date date = new Date();
        model.addAttribute("date", date);
        model.addAttribute("guests", this.guestService.findByStructure(this.structureService.findOne(id)));
        return "admin/signatureSheet";
    }

}
