package com.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Autor() {
    }

    public Autor(String nombre, Integer nacimiento, Integer fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Método para agregar un libro y mantener relación bidireccional
    public void agregarLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    // Formato plano para impresión ordenada
    public String formatoCuadro() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAutor: ").append(nombre).append("\n");
        sb.append("Nacimiento: ").append(nacimiento != null ? nacimiento : "¿?").append("\n");
        sb.append("Fallecimiento: ").append(fallecimiento != null ? fallecimiento : "¿?").append("\n");

        sb.append("Libro: ");
        if (libros != null && !libros.isEmpty()) {
            libros.forEach(libro -> sb.append(" ").append(libro.getTitulo()));
        } else {
            sb.append("Ninguno");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return formatoCuadro();
    }
}
