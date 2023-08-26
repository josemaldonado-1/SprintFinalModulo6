package cl.apr.sprintm6.services;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import cl.apr.sprintm6.dtos.UsuarioDTO;
import cl.apr.sprintm6.models.RolEnum;
import cl.apr.sprintm6.models.Usuario;
import cl.apr.sprintm6.repositories.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
    private PasswordEncoder passwordEncoder; // Asegúrate de inyectar el PasswordEncoder



	public List<Usuario> listar() {

		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

		return usuarios;
		}
	
	public List<UsuarioDTO> listarDTO() {

		return ((Collection<Usuario>) this.usuarioRepository.findAll()).stream().map(UsuarioDTO::new).collect(toList());
		
	}
	
	
	public Usuario findByEmail(String email) {
	    Object result = usuarioRepository.findUserByEmail(email);
	    if (result != null) {
	        Object[] row = (Object[]) result;
	        Usuario usuario = new Usuario();
	        usuario.setId(((Number) row[0]).longValue());
	        usuario.setEmail((String) row[1]);
	        usuario.setNombres((String) row[2]);
	        usuario.setApellidos((String) row[3]);
	        usuario.setPassword((String) row[4]);
	        Integer rolValue = (Integer) row[5];
	        if (rolValue != null) {
	            RolEnum[] roles = RolEnum.values();
	            if (rolValue >= 0 && rolValue < roles.length) {
	                usuario.setRol(roles[rolValue]);
	            }
	        }
	        return usuario;
	    } else {
	        return null;
	    }
	}
	
	
    public Usuario crearUsuario(String email, String password, String nombres, String apellidos,String rut, RolEnum rol) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordEncoder.encode(password)); // Codificar la contraseña antes de almacenarla
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setRut(rut);
        nuevoUsuario.setRol(rol);
        
        return usuarioRepository.save(nuevoUsuario);
    }
    
    public Usuario guardarEdit(Long id,String email, String password, String nombres, String apellidos,String rut, RolEnum rol) {
    	
    	Usuario editUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    	
        //Usuario editUsuario = new Usuario();
        editUsuario.setId(id);
        editUsuario.setEmail(email);
        editUsuario.setPassword(password);
        editUsuario.setNombres(nombres);
        editUsuario.setApellidos(apellidos);
        editUsuario.setRut(rut);
        editUsuario.setRol(rol);
        
        return usuarioRepository.save(editUsuario);
    	
    }
    
    public UsuarioDTO getUsuario(@PathVariable Long id) {
		return this.usuarioRepository.findById(id).map(UsuarioDTO::new).orElse(null);
	}
    
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
		
	}
	
	public Usuario obtener(Long id) {
		return usuarioRepository.findById(id).get(); 
	}

}
