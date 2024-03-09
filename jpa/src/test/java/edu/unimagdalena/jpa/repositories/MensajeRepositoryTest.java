package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.entities.Usuario;
import edu.unimagdalena.jpa.entities.Mensaje;

import edu.unimagdalena.jpa.AbstractIntegrationDBTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

public class MensajeRepositoryTest extends AbstractIntegrationDBTest {
    MensajeRepository mensajeRepository;

    @Autowired
    public MensajeRepositoryTest(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    Usuario usuario = Usuario.builder()
            .email("js@unimgdalena.edu.co")
            .nombre("Javier")
            .apellidos("Santodomingo")
            .edad(19)
            .password("1234")
            .repPassword("1234")
            .rol("Estudiante")
            .build();

    Mensaje mensaje = Mensaje.builder()
            .creador("Javier")
            .destinatario("Jonathan")
            .contenido("Hola!")
            .createdAt(LocalDateTime.now())
            .build();

    Mensaje mensaje2 = Mensaje.builder()
            .creador("Javier")
            .destinatario("Jonathan")
            .contenido("Hola!!")
            .createdAt(LocalDateTime.now())
            .build();

    @BeforeEach
    void setUp() {
        mensajeRepository.deleteAll();
    }

    @Test
    @DisplayName("Insertar mensaje a la base de datos. (SAVE)")
    void testInsert() {
        // when
        Mensaje mensajeSaved = mensajeRepository.save(mensaje);
        // then
        assertThat(mensajeSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar contenido de un mensaje. (UPDATE)")
    void testUpdateById() {
        // when
        mensajeRepository.save(mensaje);
        mensajeRepository.save(mensaje2);
        Long id = mensaje2.getId();
        mensajeRepository.updateMensajeById(id, "Nuevo Mensaje");
        // then
        assertEquals(mensajeRepository.findAll().get(1).getContenido(), "Nuevo Mensaje");
    }

    @Test
    @DisplayName("Borrar mensaje por ID. (DELETE)")
    void testDeleteById() {
        mensajeRepository.save(mensaje);
        // when
        Long id = mensaje.getId();
        mensajeRepository.deleteById(id);
        // then
        assertThat(mensajeRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Buscar mensaje por el id del usuario que lo mandó. (FIND)")
    void testFindByDestinatario() {
        // given
        mensajeRepository.save(mensaje);
        mensajeRepository.save(mensaje2);
        String destinatario = mensaje.getDestinatario();
        // when
        @SuppressWarnings("unchecked")
        List<Mensaje> mensajesFound = (List<Mensaje>) mensajeRepository.findMensajeByDestinatario(destinatario);
        // then
        assertEquals(2, mensajesFound.size());
    }

    @Test
    @DisplayName("Ver cantidad de mensajes almacenados. (READ)")
    void testdGetAllSugerencias() {
        mensajeRepository.save(mensaje);
        mensajeRepository.save(mensaje2);

        List<Mensaje> sugerencias = mensajeRepository.findAll();

        assertThat(sugerencias).hasSize(2);
    }
}