package ch.hearc.ig.themoviedb.business;

import ch.hearc.ig.themoviedb.service.APIService;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MovieBuilder {
    private final APIService tmdbAPIService = new APIService();

    public Movie getMovieDetails(int movieId) throws IOException, JsonException {
        String jsonResponse = tmdbAPIService.fetchMovieDetails(movieId);
        return buildMovieFromJson(jsonResponse);
    }


    public List<Movie> getMovieDetails(String keyword) throws IOException, JsonException {
        String jsonResponse = tmdbAPIService.fetchMovieDetails(keyword);
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);

        JsonArray resultsArray = (JsonArray) json.get("results"); // Adjust to match actual JSON structure
        List<Movie> movies = new ArrayList<>();

        if (resultsArray != null) {
            for (Object result : resultsArray) {
                JsonObject movieJson = (JsonObject) result;
                movies.add(buildMovieFromJson(movieJson.toJson()));
            }
        }

        return movies;
    }


    public Set<Movie> getCollectionDetails(List<Integer> movieIds) throws IOException, JsonException {
        Set<Movie> movies = new HashSet<>();

        for (Integer movieId : movieIds) {
            String jsonResponse = tmdbAPIService.fetchMovieDetails(movieId);
            movies.add(buildMovieFromJson(jsonResponse));
        }

        return movies;
    }


    // Helper method to build a Movie object from JSON response
    private Movie buildMovieFromJson(String jsonResponse) throws JsonException {
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);
        Movie currentMovie = new Movie();

        // Check and set each field with null checks
        if (json.get("id") != null) {
            currentMovie.setId(((BigDecimal) json.get("id")).intValue());
        }

        currentMovie.setOriginal_title((String) json.getOrDefault("original_title", "Unknown Title"));

        if (json.get("vote_average") != null) {
            currentMovie.setRating(((BigDecimal) json.get("vote_average")).doubleValue());
        } else {
            currentMovie.setRating(0.0); // default rating if null
        }

        currentMovie.setReleaseDate((String) json.getOrDefault("release_date", "Unknown Date"));

        // Use extractGenres method but handle potential nulls within that method if necessary
        currentMovie.setGenres(extractGenres(json));

        if (json.get("runtime") != null) {
            currentMovie.setRuntime(((BigDecimal) json.get("runtime")).intValue());
        } else {
            currentMovie.setRuntime(0); // default runtime if null
        }

        currentMovie.setTagline((String) json.getOrDefault("tagline", "No tagline available"));
        currentMovie.setOverview((String) json.getOrDefault("overview", "No overview available"));

        // Use extractCast method but handle potential nulls within that method if necessary
        currentMovie.setCast(extractCast(json));

        return currentMovie;
    }


    // Helper method to extract genres
    private Set<String> extractGenres(JsonObject json) {
        Set<String> genres = new HashSet<>();
        JsonArray genresArray = (JsonArray) json.get("genres");

        if (genresArray != null) {
            for (Object genreObj : genresArray) {
                JsonObject genreJson = (JsonObject) genreObj;
                genres.add((String) genreJson.get("name"));
            }
        }

        return genres;
    }

    // Helper method to extract cast
    private Set<Person> extractCast(JsonObject json) {
        Set<Person> cast = new HashSet<>();
        JsonObject credits = (JsonObject) json.get("credits");
        if (credits != null) {
            JsonArray castArray = (JsonArray) credits.get("cast");

            if (castArray != null) {
                for (Object castMember : castArray) {
                    JsonObject castJson = (JsonObject) castMember;
                    Person person = new Person();
                    person.setName((String) castJson.get("name"));
                    person.setId(((BigDecimal) castJson.get("id")).intValue());

                    cast.add(person);
                }
            }
        }

        return cast;
    }
}
