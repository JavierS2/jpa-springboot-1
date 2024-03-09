package edu.unimagdalena.jpa.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import edu.unimagdalena.jpa.AbstractIntegrationDBTest;
import edu.unimagdalena.jpa.entities.Partida;

import edu.unimagdalena.jpa.entities.Usuario;

class PartidaRepositoryTest extends AbstractIntegrationDBTest {

    PartidaRepository partidaRepository;

    @Autowired
    public PartidaRepositoryTest(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
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

    Usuario usuario1 = Usuario.builder()
            .email("jv@unimgdalena.edu.co")
            .nombre("Jose")
            .apellidos("domingo")
            .edad(19)
            .password("1234")
            .repPassword("1234")
            .rol("Estudiante")
            .build();

    Partida partida1 = Partida.builder()
            .creador("juan.perez@email.com")
            .deporte("Futbol")
            .ciudad("Bogotá")
            .provincia("Cundinamarca")
            .fecha(LocalDate.of(2024, 3, 12))
            .horaComienzo(LocalTime.of(10, 0))
            .horaFinal(LocalTime.of(12, 0))
            .participantes(Arrays.asList(usuario1, usuario))
            .comentarios("Partido amistoso en el parque").suplentes(2).build();

    Partida partida2 = Partida.builder()
            .creador("maria.gomez@email.com")
            .deporte("Baloncesto")
            .ciudad("Medellín")
            .provincia("Antioquia")
            .fecha(LocalDate.of(2024, 3, 15))
            .horaComienzo(LocalTime.of(15, 0))
            .participantes(Arrays.asList(usuario1, usuario))
            .comentarios("Partido de torneo en el coliseo")
            .suplentes(1)
            .build();

    @BeforeEach
    void setUp() {
        partidaRepository.deleteAll();
    }

    @Test
    @DisplayName("Insertar partida a la base de datos. (SAVE)")
    void testInsert() {
        // when
        Partida partidaSaved = partidaRepository.save(partida1);
        // then
        assertThat(partidaSaved.getIdPartida()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar ciudad partida por ID. (UPDATE)")
    void testUpdateCiudadById() {
        partidaRepository.save(partida1);
        partidaRepository.save(partida2);
        // when
        Long id = partida2.getIdPartida();
        partidaRepository.updateCiudadPartidaById(id, "Cali");
        // then
        assertEquals(partidaRepository.findAll().get(1).getCiudad(), "Cali");
    }

    @Test
    @DisplayName("Borrar partida por ciudad y creador. (DELETE)")
    void testDeletePartidaByCiudadAndCreador() {
        partidaRepository.save(partida1);
        // when
        String ciudad = partida1.getCiudad();
        String creador = partida1.getCreador();
        partidaRepository.deleteByCiudadAndCreador(ciudad, creador);
        // then
        assertThat(partidaRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Buscar partida por creador. (FIND)")
    void testFindByCreador() {
        // given
        partidaRepository.save(partida1);
        partidaRepository.save(partida2);
        // when
        @SuppressWarnings("unchecked")
        List<Partida> partidas = (List<Partida>) partidaRepository.findPartidaByCreador("maria.gomez@email.com");
        // then
        assertEquals(1, partidas.size());
    }

    @Test
    @DisplayName("Ver cantidad de partidas. (READ)")
    void testdGetAllPartidas() {
        partidaRepository.save(partida1);
        partidaRepository.save(partida2);

        List<Partida> partidas = partidaRepository.findAll();

        assertThat(partidas).hasSize(2);
    }

}