package com.alura.challenge_literatura.repository;

import com.alura.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT l FROM Libro l WHERE l.nombreAutor LIKE CONCAT('%', (:nombreAutor), '%')")
    List<Libro> buscarPorAutor(String nombreAutor);

    List<Libro> findByIdiomaContains(String idioma);

}
