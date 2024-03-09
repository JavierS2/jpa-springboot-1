package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario WHERE u.idUsuario = ?1")
    Usuario getUsuarioByIdUsuario(Long idUsuario);

    @Query("UPDATE Usuario SET nombre = ?2 WHERE idUsuario = ?1")
    void updateNombreUsuarioById(Long id, String newNombre);

    @Query("DELETE u FROM Usuario WHERE u.email = ?1")
    void deleteByEmail(String email);

    Usuario findUsuarioByIdUsuario(Long idUsuario);

    List<Usuario> findByNombreAndApellidos(String nombre, String apellidos);
}
