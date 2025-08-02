package com.literalura.repository;

import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllConLibros();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE (a.fallecimiento IS NULL OR a.fallecimiento > :anio) AND a.nacimiento <= :anio")
    List<Autor> findAutoresVivosEn(int anio);
}
