package it.uniroma3.CivitasProcuratio.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MapsController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/superadmin/maps/addPlace", method = RequestMethod.POST)
	public String addPlace(Model model, @ModelAttribute("name") String name, @ModelAttribute("lat") String lat,
			@ModelAttribute("lng") String lng, @ModelAttribute("category") String category,
			@ModelAttribute("capacity") String capacity, @ModelAttribute("description") String description) {
		try {
			JSONObject newPlace = new JSONObject();
			newPlace.put("name", name);
			newPlace.put("lat", lat);
			newPlace.put("lng", lng);
			newPlace.put("category", category);
			if(category.equals("CAS"))
				newPlace.put("capacity", new Integer(capacity));
			else newPlace.put("capacity", new Integer(0));
			newPlace.put("description", description);
			JSONParser parser = new JSONParser();
			InputStream placesStream = new ClassPathResource("static/json/places.json").getInputStream();
			JSONArray oldPlaces = (JSONArray) parser.parse(new InputStreamReader(placesStream));
			oldPlaces.add(newPlace);
			File file = new ClassPathResource("static/json/places.json").getFile();
			FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
			fileWriter.write(oldPlaces.toJSONString());
			fileWriter.close();
			model.addAttribute("places", oldPlaces);
			return "superadmin/maps";
		} catch (Exception e) {
			System.out.println(e);
			return "superadmin/maps";
		}

	}

	@RequestMapping(value = "/superadmin/maps")
	public String maps(Model model) {
		try {
			JSONParser parser = new JSONParser();
			InputStream placesStream = new ClassPathResource("static/json/places.json").getInputStream();
			JSONArray places = (JSONArray) parser.parse(new InputStreamReader(placesStream));
			model.addAttribute("places", places);
			return "superadmin/maps";
		} catch (Exception e) {
			System.out.println(e);
			return "superadmin/maps";
		}
	}

}
