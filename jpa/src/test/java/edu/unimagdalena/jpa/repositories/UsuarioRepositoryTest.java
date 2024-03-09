package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.AbstractIntegrationDBTest;
import edu.unimagdalena.jpa.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

class UsuarioRepositoryTest extends AbstractIntegrationDBTest {

    UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioRepositoryTest(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    Usuario usuario = Usuario.builder()
            .email("jv@unimgdalena.edu.co")
            .nombre("Jonathan")
            .apellidos("Vega")
            .edad(23)
            .password("12345")
            .repPassword("12345")
            .rol("Estudiante")
            .build();

    Usuario usuario2 = Usuario.builder()
            .email("js@unimgdalena.edu.co")
            .nombre("Javier")
            .apellidos("Santodomingo")
            .edad(19)
            .password("1234")
            .repPassword("1234")
            .rol("Estudiante")
            .build();

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    @DisplayName("Verifica que se insertó usuario.")
    void testInsert() {
        Usuario userSaved = usuarioRepository.save(usuario);
        assertThat(userSaved.getIdUsuario()).isNotNull();
    }

    @Test
    @DisplayName("Obtener por ID.")
    void testGetById() {
        usuarioRepository.save(usuario2);
        Long idUser = usuarioRepository.save(usuario).getIdUsuario();
        Usuario user = usuarioRepository.getUsuarioByIdUsuario(idUser);

        assertEquals(idUser, user.getIdUsuario());
    }

    @Test
    @DisplayName("Actualizar nombre por id.")
    void testUpdateNameById() {
        Long idUsuario = usuarioRepository.save(usuario).getIdUsuario();
        usuarioRepository.save(usuario2);

        usuarioRepository.updateNombreUsuarioById(idUsuario, "Jhon");

        assertThat(usuario.getNombre().equals("Jhon"));
    }

    @Test
    @DisplayName("Eliminar por email.")
    void testDeleteByEmail() {
        Long id = usuarioRepository.save(usuario).getIdUsuario();

        usuarioRepository.deleteByEmail("jv@unimgdalena.edu.co");
        Usuario usuario1 = usuarioRepository.findById(id).orElse(null);

        assertNull(usuario1);
    }

    @Test
    @DisplayName("Obtener lista de los usuarios.")
    void testdGetAllUsers() {
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        List<Usuario> usuarios = usuarioRepository.findAll();

        assertThat(usuarios).hasSize(2);
    }

    @Test
    @DisplayName("Buscar por ID.")
    void testFindById() {
        usuarioRepository.save(usuario2);
        Long idUser = usuarioRepository.save(usuario).getIdUsuario();

        Usuario user = usuarioRepository.findUsuarioByIdUsuario(idUser);

        assertEquals(idUser, user.getIdUsuario());
    }

    @Test
    @DisplayName("Buscar por nombre y apellido.")
    void testFindByNombreAndApellido() {
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

        List<Usuario> usuarios = usuarioRepository.findByNombreAndApellidos("Jonathan", "Vega");

        assertThat(usuarios).isNotEmpty();
        assertThat(usuarios).first().hasFieldOrPropertyWithValue("nombre", "Jonathan");
        assertThat(usuarios).first().hasFieldOrPropertyWithValue("apellidos", "Vega");
    }
}