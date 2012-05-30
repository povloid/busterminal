package pk.home.busterminal.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HelloWorldController {
	@RequestMapping("/helloWorld")
	public String helloWorld(Model model) {
		model.addAttribute("message", "Hello World!");
		return "helloWorld";
	}

	@RequestMapping(value = "/helloWorld/{id}", method = RequestMethod.GET)
	public String findOwner(@PathVariable String id, Model model) {

		model.addAttribute("owner", "Povloid" + id);
		model.addAttribute("message", "Hello World!");
		return "helloWorld";
	}



}
