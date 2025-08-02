package com.literalura.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.model.Autor;
import com.literalura.model.Libro;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class GutendexClient {
    private final String BASE_URL = "https://gutendex.com/books/?search=";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Optional<Libro> buscarLibro(String titulo) {
        try {
            String encoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + encoded))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            JsonNode libroJson = root.path("results").get(0);

            Libro libro = new Libro();
            libro.setTitulo(libroJson.path("title").asText());
            libro.setIdioma(libroJson.path("languages").get(0).asText());
            libro.setDescargas(libroJson.path("download_count").asInt());

            JsonNode autorJson = libroJson.path("authors").get(0);
            Autor autor = new Autor();
            autor.setNombre(autorJson.path("name").asText());
            autor.setNacimiento(autorJson.path("birth_year").asInt());
            autor.setFallecimiento(autorJson.path("death_year").asInt());

            libro.setAutor(autor);
            return Optional.of(libro);

        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
            return Optional.empty();
        }
    }
}
