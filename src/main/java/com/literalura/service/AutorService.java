package com.literalura.service;

import com.literalura.model.Autor;
import com.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepo;

    public AutorService(AutorRepository autorRepo) {
        this.autorRepo = autorRepo;
    }

    public void mostrarAutoresConLibros() {
        try {
            List<Autor> autores = autorRepo.findAllConLibros();

            for (Autor autor : autores) {
                System.out.println("\nAutor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFallecimiento());

                if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                    System.out.println("Libro:");
                    autor.getLibros().forEach(libro -> System.out.println("📚 " + libro.getTitulo()));
                } else {
                    System.out.println("Libros: Ninguno registrado.");
                }
            }
        } catch (Exception e) {
            System.out.println(" Error interno: " + e.getMessage());
        }
    }

    public void listarVivosEn(int anio) {
        try {
            List<Autor> autores = autorRepo.findAutoresVivosEn(anio);

            for (Autor autor : autores) {
                System.out.println("\nAutor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFallecimiento());

                if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                    System.out.println("Libro :");
                    autor.getLibros().forEach(libro -> System.out.println("📚 " + libro.getTitulo()));
                } else {
                    System.out.println("Libro: Ninguno registrado.");
                }
            }
        } catch (Exception e) {
            System.out.println(" Error interno: " + e.getMessage());
        }
    }
}
