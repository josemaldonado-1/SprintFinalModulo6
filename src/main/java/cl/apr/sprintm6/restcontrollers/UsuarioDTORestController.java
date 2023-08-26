package cl.apr.sprintm6.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.apr.sprintm6.services.UsuarioService;

import cl.apr.sprintm6.dtos.UsuarioDTO;

@RestController
@RequestMapping("/api")
public class UsuarioDTORestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/listar")
	public List<UsuarioDTO> listarDTO() {
		return  usuarioService.listarDTO();
	}

}
