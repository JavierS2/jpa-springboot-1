package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.entities.Partida;
import edu.unimagdalena.jpa.entities.Sugerencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("SELECT p FROM Partidas WHERE p.id = ?1")
    Sugerencia getPartidaById(Long id);

    @Query("UPDATE Partidas SET ciudad = ?2 WHERE idPartida = ?1")
    void updateCiudadPartidaById(Long id, String newCiudad);

    @Query("DELETE p FROM Partidas WHERE p.ciudad = ?1 AND p.creador = ?2")
    void deleteByCiudadAndCreador(String ciudad, String creador);

    Partida findPartidaByCreador(String creador);

}
