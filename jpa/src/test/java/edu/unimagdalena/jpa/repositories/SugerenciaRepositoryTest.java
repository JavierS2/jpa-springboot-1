package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.AbstractIntegrationDBTest;
import edu.unimagdalena.jpa.entities.Sugerencia;
import edu.unimagdalena.jpa.entities.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

class SugerenciaRepositoryTest extends AbstractIntegrationDBTest {

    SugerenciaRepository sugerenciaRepository;

    @Autowired
    public SugerenciaRepositoryTest(SugerenciaRepository sugerenciaRepository) {
        this.sugerenciaRepository = sugerenciaRepository;
    }

    @BeforeEach
    void setUp() {
        sugerenciaRepository.deleteAll();
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

    Sugerencia sugerencia1 = Sugerencia.builder()
            .descripcion("Esta es la sugerencia 1")
            .createdAt(LocalDateTime.now())
            .usuario(usuario)
            .build();

    Sugerencia sugerencia2 = Sugerencia.builder()
            .descripcion("Esta es la sugerencia 2")
            .createdAt(LocalDateTime.now())
            .usuario(usuario)
            .build();

    @Test
    @DisplayName("Insertar sugerencia a la base de datos. (SAVE)")
    void testInsert() {
        // when
        Sugerencia sugerenciaSaved = sugerenciaRepository.save(sugerencia1);
        // then
        assertThat(sugerenciaSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar descripción de una sugerencia. (UPDATE)")
    void testUpdateDescById() {
        // when
        sugerenciaRepository.save(sugerencia1);
        sugerenciaRepository.save(sugerencia2);
        Long id = sugerencia2.getId();
        sugerenciaRepository.updateSugerenciaById(id, "newDescription");
        // then
        assertEquals("newDescription", sugerenciaRepository.findAll().get(1).getDescripcion());
    }

    @Test
    @DisplayName("Borrar sugerencia por ID. (DELETE)")
    void testDeleteById() {
        sugerenciaRepository.save(sugerencia1);
        // when
        Long id = sugerencia1.getId();
        sugerenciaRepository.deleteById(id);
        // then
        assertThat(sugerenciaRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Buscar sugerencia por el id del usuario que la creó. (FIND)")
    void testFindByIdUsuario() {
        // given
        sugerenciaRepository.save(sugerencia1);
        sugerenciaRepository.save(sugerencia2);
        Long id = sugerencia1.getId();
        // when
        @SuppressWarnings("unchecked")
        List<Sugerencia> sugerencias = (List<Sugerencia>) sugerenciaRepository.findSugerenciaById(id);
        // then
        assertEquals(2, sugerencias.size());
    }

    @Test
    @DisplayName("Ver cantidad de sugerencias almacenadas. (READ)")
    void testdGetAllSugerencias() {
        sugerenciaRepository.save(sugerencia1);
        sugerenciaRepository.save(sugerencia2);

        List<Sugerencia> sugerencias = sugerenciaRepository.findAll();

        assertThat(sugerencias).hasSize(2);

    }
}