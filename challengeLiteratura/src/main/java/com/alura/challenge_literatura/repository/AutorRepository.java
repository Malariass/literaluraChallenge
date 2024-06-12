package com.alura.challenge_literatura.repository;

import com.alura.challenge_literatura.model.Autor;
import com.alura.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Consulta BBDD de Libro por nombre de autor
    Optional<Autor> findByNombreContains(String nombreAutor);

    // Consulta
    @Query("SELECT a FROM Autor a WHERE a.anioDeNacimiento <= :anioDeMuerte AND (a.anioDeMuerte >= :anioDeNacimiento OR a.anioDeMuerte IS NULL)")
    List<Autor> busquedaAutorFechaNacimientoMuerte(Integer anioDeNacimiento, Integer anioDeMuerte);

}
