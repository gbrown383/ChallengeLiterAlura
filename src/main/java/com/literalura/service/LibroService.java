package com.literalura.service;

import com.literalura.model.Libro;
import com.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepo;

    public LibroService(LibroRepository libroRepo) {
        this.libroRepo = libroRepo;
    }

    public void guardarSiNoExiste(Libro libro) {
        libroRepo.findByTituloIgnoreCase(libro.getTitulo())
                .ifPresentOrElse(
                        l -> System.out.println("\n El libro ya existe en la base de datos.\n"),
                        () -> {
                            libroRepo.save(libro);
                            mostrarResumenLibro(libro);
                        }
                );
    }

    public void mostrarResumenLibro(Libro libro) {
        System.out.println("------------- LIBRO --------------");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor().getNombre());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Número de descargas: " + libro.getDescargas());
        System.out.println("-----------------------------------\n");
    }

    public void imprimirTodos() {
        List<Libro> libros = libroRepo.findAll();
        libros.forEach(this::mostrarResumenLibro);
    }

    public void imprimirPorIdioma(String idioma) {
        List<Libro> libros = libroRepo.findByIdiomaIgnoreCase(idioma);
        if (libros.isEmpty()) {
            System.out.println(" No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(this::mostrarResumenLibro);
        }
    }
}
