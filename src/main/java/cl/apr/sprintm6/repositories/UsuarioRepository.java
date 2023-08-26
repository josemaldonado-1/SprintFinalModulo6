package cl.apr.sprintm6.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.apr.sprintm6.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
    
    @Query(value = "SELECT * FROM usuario WHERE email = :email LIMIT 1", nativeQuery = true)
    Object findUserByEmail(@Param("email") String email);

}
