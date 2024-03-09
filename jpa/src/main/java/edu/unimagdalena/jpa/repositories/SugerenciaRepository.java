package edu.unimagdalena.jpa.repositories;

import edu.unimagdalena.jpa.entities.Sugerencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SugerenciaRepository extends JpaRepository<Sugerencia, Long> {

    @Query("SELECT s FROM Sugerencias WHERE s.id = ?1")
    Sugerencia getSugerenciaById(Long id);

    /*
     * @Query("UPDATE Sugerencias SET descripcion = ?2 WHERE id = ?1")
     * void updateSugerenciaById(Long id, String newDescripcion);
     */

    @Query("UPDATE Sugerencia SET descripcion = :nuevaDescripcion WHERE id = :id")
    void updateSugerenciaById(@Param("id") Long id, @Param("nuevaDescripcion") String newDescripcion);

    @Query("DELETE s FROM Sugerencias WHERE s.id = ?1")
    void deleteById(Long id);

    Sugerencia findSugerenciaById(Long id);
}
