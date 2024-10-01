package ch.hearc.ig.themoviedb.service;

import ch.hearc.ig.themoviedb.business.Movie;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.math.BigDecimal;

public class MovieService {
    private final APIService tmdbAPIService = new APIService();

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

    public Movie fetchMovieById(int movieId) throws IOException, JsonException {
        return getMovieDetails(movieId);
    }

    public void printMovieDetails(int movieId) throws IOException, JsonException {
        Movie movie = fetchMovieById(movieId);
        System.out.println(movie.toString());
    }
}