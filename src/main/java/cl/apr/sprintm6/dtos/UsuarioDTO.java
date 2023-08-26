package cl.apr.sprintm6.dtos;


import cl.apr.sprintm6.models.RolEnum;
import cl.apr.sprintm6.models.Usuario;

public class UsuarioDTO {
	
	protected Long id;
	protected String nombres;
	protected String apellidos;
	protected String rut;
	protected String email;
	protected RolEnum rol;
	
	//Getters y Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RolEnum getRol() {
		return rol;
	}
	public void setRol(RolEnum rol) {
		this.rol = rol;
	}
	
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.rut = usuario.getRut();
        this.email = usuario.getEmail();
        this.rol=usuario.getRol();  
}

}
