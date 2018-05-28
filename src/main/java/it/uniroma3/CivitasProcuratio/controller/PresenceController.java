package it.uniroma3.CivitasProcuratio.controller;

import it.uniroma3.CivitasProcuratio.exception.StorageFileNotFoundException;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.Guest;
import it.uniroma3.CivitasProcuratio.model.Presence;
import it.uniroma3.CivitasProcuratio.model.PresenceForm;
import it.uniroma3.CivitasProcuratio.service.CasService;
import it.uniroma3.CivitasProcuratio.service.PresenceService;
import it.uniroma3.CivitasProcuratio.service.StorageService;
import it.uniroma3.CivitasProcuratio.service.GuestService;
import it.uniroma3.CivitasProcuratio.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PresenceController {

	@Autowired
	private CasService casService;

	@Autowired
	private GuestService guestService;

	@Autowired
	private PresenceService presenceService;

	@Autowired
	private StorageService storageService;

	@RequestMapping(value = "/admin/insertDate/{id}", method = RequestMethod.GET)
	public String showFormDate(@PathVariable("id") Long id, Model model) {
		model.addAttribute("presenceDate", new Date());
		model.addAttribute("cas", this.casService.findOne(id));
		model.addAttribute("from", new Date());
		model.addAttribute("to", new Date());
		return "admin/insertDate";
	}

	@RequestMapping(value = "/admin/dailyPresence/{id}", method = RequestMethod.GET)
	public String showReport(@PathVariable("id") Long id, Model model,
			@ModelAttribute("presenceDate") String presenceDate) throws ParseException {
		Cas cas = casService.findOne(id);
		model.addAttribute("cas", cas);
		model.addAttribute("guests", this.guestService.findByCas(cas));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = sdf.parse(String.valueOf(presenceDate));
		List<Presence> presences = this.presenceService.getPresenceGuestsByDate(this.guestService.findByCas(cas), myDate);
		model.addAttribute("myDate", myDate);
		model.addAttribute("presences", presences);
		model.addAttribute("presenceDate", presenceDate);
		model.addAttribute("files", getCasFiles(cas.getId().toString()));
		if (presences.isEmpty())
			model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
		if (!DateUtils.dateValidation(myDate)) {
			model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
			return "admin/insertDate";
		}
		return "admin/dailyPresence";
	}

	private List<String> getCasFiles(String casId) {
		Path casFolder = Paths.get(storageService.getRootLocation().toString(), casId);
		return storageService.loadAll(casFolder)
				.map(path -> MvcUriComponentsBuilder
						.fromMethodName(PresenceController.class, "serveFile", casId, path.getFileName().toString())
						.build().toString())
				.collect(Collectors.toList());
	}

	@GetMapping("admin/fogliFirme/{casId}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String casId, @PathVariable String filename) {
		Path casFolder = Paths.get(storageService.getRootLocation().toString(), casId);
		Resource file = storageService.loadAsResource(filename, casFolder);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("admin/uploadFoglioFirme")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("idCas") Long idCas,
			@RequestParam("presenceDate") String presenceDate, RedirectAttributes redirectAttributes) {
		Path casFolder = Paths.get(storageService.getRootLocation().toString(), idCas.toString());
		storageService.store(file, casFolder);
		redirectAttributes.addFlashAttribute("uploaderMessage",
				"Hai caricato il seguente file: " + file.getOriginalFilename());

		return "redirect:/admin/dailyPresence/" + idCas + "?presenceDate=" + presenceDate;
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/admin/showInsertPresence/{id}/{presenceDate}", method = RequestMethod.GET)
	public String showFormPresence(@PathVariable("id") Long id, Model model,
			@PathVariable("presenceDate") String presenceDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = sdf.parse(String.valueOf(presenceDate));
		Cas cas = this.casService.findOne(id);

		List<Guest> guests = this.guestService.findByCas(cas);
		List<Long> ids = new ArrayList<>();

		for (Guest g : guests) {
			ids.add(g.getId());

			System.out.println(g.getId());
		}

		model.addAttribute("myDate", myDate);
		model.addAttribute("cas", cas);
		model.addAttribute("guests", guests);
		model.addAttribute("presenceForm", new PresenceForm());
		model.addAttribute("presenceDate", presenceDate);
		model.addAttribute("presence", new Presence());
		return "admin/insertPresence";
	}

	@RequestMapping(value = "/admin/insertPresence/{id1}/{presenceDate}", method = RequestMethod.POST)
	public String insertPresence(@PathVariable("id1") Long idCas, @PathVariable("presenceDate") String presenceDate,
			@ModelAttribute("activityForm") PresenceForm presenceForm,
			@Valid @ModelAttribute("presence") Presence presence, Model model, BindingResult bindingResult)
			throws ParseException {
		Cas cas = this.casService.findOne(idCas);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = sdf.parse(String.valueOf(presenceDate));

		model.addAttribute("myDate", myDate);
		model.addAttribute("cas", cas);
		model.addAttribute("guests", this.guestService.findByCas(cas));
		model.addAttribute("presenceForm", new PresenceForm());

		List<Long> checkedIds = presenceForm.getCheckedGuests();

		for (Long id : checkedIds) {
			System.out.println("ID: " + id);
			presence = new Presence();
			presence.setDate(myDate);
			presence.setGuest(this.guestService.findOne(id));

			//this.presenceService.save(presence);

			if(this.presenceService.alreadyExists(presence)) {
				model.addAttribute("message", "*ATTENZIONE: esiste già una presenza per la data selezionata!");
			}
			else {
				if (!bindingResult.hasErrors()) {
					this.presenceService.save(presence);
				}
			}
		}
		return "admin/dailyPresence";
	}

	@RequestMapping(value = "/admin/periodPresence/{id}", method = RequestMethod.POST)
	public String insertPeriod(@PathVariable("id") Long id, Model model, @ModelAttribute("from") String from,
			@ModelAttribute("to") String to) throws ParseException {
		Cas cas = this.casService.findOne(id);
		model.addAttribute("cas", cas);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from1 = sdf.parse(String.valueOf(from));
		Date to1 = sdf.parse(String.valueOf(to));
		if (!DateUtils.dateValidation(from1) || !DateUtils.dateValidation(to1)) {
			model.addAttribute("message",
					"*ATTENZIONE: una delle due date non è corretta, impossibile inserire una data dopo quella odierna!*");
			return "admin/insertDate";
		}
		if (from1.after(to1)) {
			model.addAttribute("message", "*ATTENZIONE: le date inserite non sono conseguenti tra di loro!*");
			return "admin/insertDate";
		}
		List<Presence> presences = this.presenceService.getPresenceBetweenPeriod(this.guestService.findByCas(cas),
				from1, to1);
		if (presences.isEmpty()) {
			model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
			return "admin/insertDate";
		}
		model.addAttribute("presences", presences);
		return "admin/periodPresence";
	}

	@RequestMapping(value = "/superadmin/insertDate/{id}", method = RequestMethod.GET)
	public String showFormDateCas(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cas", this.casService.findOne(id));
		model.addAttribute("presenceDate", new Date());
		model.addAttribute("from", new Date());
		model.addAttribute("to", new Date());
		return "superadmin/dateStructure";
	}

	@RequestMapping(value = "/superadmin/dateStructure/{id}", method = RequestMethod.POST)
	public String dateCas(@PathVariable("id") Long id, Model model, @ModelAttribute("presenceDate") String presenceDate)
			throws ParseException {
		Cas cas = casService.findOne(id);
		model.addAttribute("cas", cas);
		model.addAttribute("guests", this.guestService.findByCas(cas));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = sdf.parse(String.valueOf(presenceDate));
		List<Presence> presences = this.presenceService.getPresenceGuestsByDate(this.guestService.findByCas(cas),
				myDate);
		model.addAttribute("presences", presences);
		model.addAttribute("presenceDate", presenceDate);
		if (presences.isEmpty())
			model.addAttribute("message", "*Non ci sono presenze per la data scelta*");
		if (!DateUtils.dateValidation(myDate)) {
			model.addAttribute("message", "*ATTENZIONE: la data inserita non è corretta*");
			return "superadmin/dateStructure";
		}
		model.addAttribute("text", presences.size());
		return "superadmin/dateStructure";
	}

	@RequestMapping(value = "/superadmin/periodStructure/{id}", method = RequestMethod.POST)
	public String periodCas(@PathVariable("id") Long id, Model model, @ModelAttribute("from") String from,
			@ModelAttribute("to") String to) throws ParseException {
		Cas cas = this.casService.findOne(id);
		model.addAttribute("cas", cas);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from1 = sdf.parse(String.valueOf(from));
		Date to1 = sdf.parse(String.valueOf(to));
		if (!DateUtils.dateValidation(from1) || !DateUtils.dateValidation(to1)) {
			model.addAttribute("text",
					"*ATTENZIONE: una delle due date non è corretta, impossibile inserire una data dopo quella odierna!*");
			return "superadmin/dateStructure";
		}
		if (from1.after(to1)) {
			model.addAttribute("text", "*ATTENZIONE: le date inserite non sono conseguenti tra di loro!*");
			return "superadmin/dateStructure";
		}
		List<Presence> presences = this.presenceService.getPresenceBetweenPeriod(this.guestService.findByCas(cas),
				from1, to1);
		if (presences.isEmpty()) {
			model.addAttribute("text", "*Non ci sono presenze per la data scelta*");
			return "superadmin/dateStructure";
		}
		model.addAttribute("presences", presences);
		model.addAttribute("text", presences.size());
		return "superadmin/dateStructure";
	}

	@RequestMapping(value = "/admin/signatureSheet/{id}")
	public String signatureSheet(@PathVariable("id") Long id, Model model) {
		Date date = new Date();
		model.addAttribute("date", date);
		model.addAttribute("guests", this.guestService.findByCas(this.casService.findOne(id)));
		return "admin/signatureSheet";
	}

}
