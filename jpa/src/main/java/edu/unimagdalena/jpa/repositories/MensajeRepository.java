package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.entities.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    // @Query("SELECT m FROM Mensajes WHERE m.id = ?1")
    Mensaje findMensajeById(Long id);

    @Query("UPDATE Mensajes SET contenido = ?2 WHERE id = ?1")
    void updateMensajeById(Long id, String newContenido);

    @Query("DELETE m FROM Mensajes WHERE m.id = ?1")
    void deleteById(Long id);

    Mensaje findMensajeByDestinatario(String destinatario);
}
