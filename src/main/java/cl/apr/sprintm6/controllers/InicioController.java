package cl.apr.sprintm6.controllers;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.User;

import cl.apr.sprintm6.dtos.UsuarioDTO;
import cl.apr.sprintm6.services.UsuarioService;

@Controller
public class InicioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public String raiz() {
		return "redirect:/login";
		
	}
	
	@GetMapping("/inicio")
	public String index(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String rolUsuario = authentication.getAuthorities().iterator().next().getAuthority(); // Obtener el primer rol
	    model.addAttribute("rolUsuario", rolUsuario);	
	    
	    if (authentication.getPrincipal() instanceof User) {
	        String nombreUsuario = ((User) authentication.getPrincipal()).getUsername();
	        model.addAttribute("nombreUsuario", nombreUsuario);
	    }
		
	    List<UsuarioDTO> usuarios	= this.usuarioService.listar().stream().map(UsuarioDTO::new).collect(toList());
	    model.addAttribute("usuarios",usuarios);
	    return "inicio";
		}
	
	}


