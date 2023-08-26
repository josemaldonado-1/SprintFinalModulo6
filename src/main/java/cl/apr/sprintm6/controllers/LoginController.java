package cl.apr.sprintm6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.apr.sprintm6.models.Usuario;
	
	@Controller
	public class LoginController {

	    @GetMapping("/login")
	    public String login(Model model) {
	        model.addAttribute("user", new Usuario());
	        return "login";
	    }

	    @GetMapping("/admin")
	    public String adminRedirect() {
	        return "redirect:/inicio";
	    }

	    @GetMapping("/cliente")
	    public String clienteRedirect() {
	        return "redirect:/inicio";
	    }

	    @GetMapping("/administrativo")
	    public String administrativoRedirect() {
	        return "redirect:/inicio";
	    }
	}

