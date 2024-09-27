package ch.hearc.ig.themoviedb.business;

import ch.hearc.ig.themoviedb.service.MovieAPIService;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;

public class MovieService {
    private final MovieAPIService movieAPIService = new MovieAPIService();

    public Movie getMovieDetails(int movieId) throws IOException, JsonException {
        String jsonResponse = movieAPIService.fetchMovieDetails(movieId);
        JsonObject json = (JsonObject) Jsoner.deserialize(jsonResponse);
        Movie movie = new Movie();
        movie.setTitle((String) json.get("title"));
        movie.setReleaseDate((String) json.get("release_date"));
        movie.setOverview((String) json.get("overview"));
        return movie;
    }
}
