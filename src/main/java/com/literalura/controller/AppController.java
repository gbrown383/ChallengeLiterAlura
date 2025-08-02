package com.literalura.controller;

import com.literalura.client.GutendexClient;
import com.literalura.service.AutorService;
import com.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppController implements CommandLineRunner {

    private final LibroService libroService;
    private final AutorService autorService;
    private final GutendexClient client = new GutendexClient();

    public AppController(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            System.out.print("Opción: ");
            String entrada = scanner.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("\n Entrada no válida. Intenta de nuevo.\n");
                continue;
            }

            try {
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = scanner.nextLine();
                        client.buscarLibro(titulo).ifPresent(libroService::guardarSiNoExiste);
                    }
                    case 2 -> {
                        System.out.println("\n Libros registrados:");
                        libroService.imprimirTodos();
                    }
                    case 3 -> {
                        System.out.println("\n Autores registrados:");
                        autorService.mostrarAutoresConLibros();
                    }
                    case 4 -> {
                        System.out.print("\n📅 Ingrese el año para buscar autores vivos: ");
                        try {
                            int anio = Integer.parseInt(scanner.nextLine());
                            autorService.listarVivosEn(anio);
                        } catch (NumberFormatException e) {
                            System.out.println(" Año no válido. Intenta con un número.");
                        }
                    }
                    case 5 -> {
                        System.out.print("\n Ingrese el idioma (ES, EN, FR, PT): ");
                        String idioma = scanner.nextLine();
                        libroService.imprimirPorIdioma(idioma);
                    }
                    case 0 -> System.out.println("\nSaliendo del programa...");
                    default -> System.out.println("\n Opción no válida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n Error interno: " + e.getMessage());
            }

            System.out.println(); // Espacio entre ciclos
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                -----------------------------------
                Elija una opción:
                1 - Buscar libro por título
                2 - Listar libros
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                -----------------------------------""");
    }
}
