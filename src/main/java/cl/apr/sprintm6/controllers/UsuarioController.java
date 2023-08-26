package cl.apr.sprintm6.controllers;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cl.apr.sprintm6.dtos.UsuarioDTO;
import cl.apr.sprintm6.models.RolEnum;
import cl.apr.sprintm6.models.Usuario;
import cl.apr.sprintm6.repositories.UsuarioRepository;
import cl.apr.sprintm6.services.UsuarioService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/crear")
	public String formUsuario(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String rolUsuario = authentication.getAuthorities().iterator().next().getAuthority(); // Obtener el primer rol
	    model.addAttribute("rolUsuario", rolUsuario);
		
		return "admin/formUsuario";
	}
	
	@PostMapping("/crear")
	public String createUsuario(
	        @RequestParam String nombres,
	        @RequestParam String apellidos,
	        @RequestParam String rut,
	        @RequestParam String rol,
	        @RequestParam String email,
	        @RequestParam String password,
	        Model model
	      ) {

	    if (nombres.isEmpty() || apellidos.isEmpty() || rut.isEmpty() || email.isEmpty() || password.isEmpty()) {
	    	String faltanDatos = "ERROR : Faltan datos en el formulario";
	    	model.addAttribute("faltanDatos",faltanDatos);
	    	
	        return "error";
	    }

	    if (usuarioRepository.findByEmail(email) != null) {
	    	String correoEnUso = "ERROR : El correo indicado se encuentra en uso";
	    	model.addAttribute("correoEnUso",correoEnUso);
	        return "error";
	    }

	    RolEnum role = RolEnum.valueOf(rol);

	    Usuario newUser = usuarioService.crearUsuario(email, password, nombres, apellidos,rut, role);

	    // You might want to perform additional checks or actions here
	    if (newUser != null) {
	        return "redirect:/inicio";
	    } else {
	    	String error = "ERROR : El usuario no pudo ser creado";
	    	model.addAttribute("error",error);
	        return "error";
	    }
	}	
	
	@GetMapping("/listar")
	public String getUsuarios(Model model) {
	List<UsuarioDTO> usuarios	= this.usuarioService.listar().stream().map(UsuarioDTO::new).collect(toList());
	model.addAttribute("usuarios",usuarios);
	return "inicio";
	}


	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable Long id, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String rolUsuario = authentication.getAuthorities().iterator().next().getAuthority(); // Obtener el primer rol
	    model.addAttribute("rolUsuario", rolUsuario);
		
        UsuarioDTO usuario = usuarioService.getUsuario(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "admin/editarUsuario";
            
        } else {
        	return null;
       }
    }
	
	@PostMapping("/editar")
	public String guardarEdit(Long id, 
			@RequestParam String nombres,
	        @RequestParam String apellidos,
	        @RequestParam String rut,
	        @RequestParam RolEnum rol,
	        @RequestParam String email) {
		
		 Usuario usuario = usuarioService.obtener(id);
		 
		 String password = usuario.getPassword();
		
		usuarioService.guardarEdit(id,email, password, nombres, apellidos,rut, rol);
		
		
		return "redirect:/inicio";
		
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id) {
		usuarioService.eliminarUsuario(id);
		
		return "redirect:/inicio";
	}
	

	
	@GetMapping("/usuarios/current")
	public UsuarioDTO getClient(Authentication authentication) {
		Usuario client = this.usuarioService.findByEmail(authentication.getName());
		
		return new UsuarioDTO(client);
	}


	

}
