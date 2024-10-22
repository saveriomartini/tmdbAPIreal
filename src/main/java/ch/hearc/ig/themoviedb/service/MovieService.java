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

/**
 * Service class for handling movie-related operations.
 */
public class MovieService {
    private final APIService tmdbAPIService = new APIService();

    /**
     * Retrieves the details of a movie.
     *
     * @param movieId the ID of the movie
     * @return the details of the movie
     * @throws IOException if an I/O error occurs
     * @throws JsonException if a JSON parsing error occurs
     */
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

    /**
     * Retrieves the credits of a movie.
     *
     * @param movieId the ID of the movie
     * @return a list of credits for the movie
     * @throws IOException if an I/O error occurs
     * @throws JsonException if a JSON parsing error occurs
     */
    public List<Credit> getMovieCredits(int movieId) throws IOException, JsonException {
        String jsonResponse = tmdbAPIService.fetchMovieCredits(movieId);
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);
        JsonArray castArray = (JsonArray) json.get("cast");
        List<Credit> credits = new ArrayList<>();

        for (Object castMember : castArray) {
            JsonObject castJson = (JsonObject) castMember;
            Credit credit = new Credit();

            // Deserialize person details (name, id)
            Person person = new Person();
            person.setName((String) castJson.get("name"));
            person.setId(((BigDecimal) castJson.get("id")).intValue());
            credit.setPerson(person);

            // Deserialize department
            Department department = new Department();
            department.setName((String) castJson.get("known_for_department"));
            credit.setDepartment(department);

            // Deserialize job
            if (castJson.containsKey("job")) {
                credit.setJob((Job) castJson.get("job"));
            }

            // Deserialize character
            if (castJson.containsKey("character")) {
                credit.setCharacter((String) castJson.get("character"));
            }

            credits.add(credit);
        }

        return credits;
    }

    /**
     * Prints the credits of a movie.
     *
     * @param movieId the ID of the movie
     * @throws IOException if an I/O error occurs
     * @throws JsonException if a JSON parsing error occurs
     */
    public void printMovieCredits(int movieId) throws IOException, JsonException {
        List<Credit> credits = getMovieCredits(movieId);
        for (Credit credit : credits) {
            System.out.println(credit.toString());
        }
    }
}