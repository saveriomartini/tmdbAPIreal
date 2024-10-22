package ch.hearc.ig.themoviedb.service;

import ch.hearc.ig.themoviedb.business.*;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private final APIService tmdbAPIService = new APIService();

    // Metodo esistente per ottenere i dettagli del film
    public Movie getMovieDetails(int movieId) throws IOException, JsonException {
        String jsonResponse = tmdbAPIService.fetchMovieDetails(movieId);
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);
        Movie currentMovie = new Movie();

        currentMovie.setId(((BigDecimal) json.get("id")).intValue());
        currentMovie.setOriginal_title((String) json.get("original_title"));
        currentMovie.setRating(((BigDecimal) json.get("vote_average")).doubleValue());
        currentMovie.setReleaseDate((String) json.get("release_date"));
        currentMovie.setRuntime(((BigDecimal) json.get("runtime")).intValue());
        currentMovie.setTagline((String) json.get("tagline"));
        currentMovie.setOverview((String) json.get("overview"));

        return currentMovie;
    }

    // Metodo aggiunto per deserializzare i crediti
    public List<Credit> getMovieCredits(int movieId) throws IOException, JsonException {
        String jsonResponse = tmdbAPIService.fetchMovieCredits(movieId);
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);
        JsonArray castArray = (JsonArray) json.get("cast");
        List<Credit> credits = new ArrayList<>();

        for (Object castMember : castArray) {
            JsonObject castJson = (JsonObject) castMember;
            Credit credit = new Credit();

            // Deserializzare i dettagli della persona (nome, id)
            Person person = new Person();
            person.setName((String) castJson.get("name"));
            person.setId(((BigDecimal) castJson.get("id")).intValue());
            credit.setPerson(person);

            // Deserializzare il dipartimento
            Department department = new Department();
            department.setName((String) castJson.get("known_for_department"));
            credit.setDepartment(department);

            // Deserializzare il lavoro (se presente)
            if (castJson.containsKey("job")) {
                credit.setJob((String) castJson.get("job"));
            }

            // Deserializzare il personaggio (se presente)
            if (castJson.containsKey("character")) {
                credit.setCharacter((String) castJson.get("character"));
            }

            credits.add(credit);
        }

        return credits;
    }

    public void printMovieCredits(int movieId) throws IOException, JsonException {
        List<Credit> credits = getMovieCredits(movieId);
        for (Credit credit : credits) {
            System.out.println(credit.toString());
        }
    }
}
