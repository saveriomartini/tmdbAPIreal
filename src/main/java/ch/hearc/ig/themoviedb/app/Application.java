package ch.hearc.ig.themoviedb.app;

import ch.hearc.ig.themoviedb.service.MovieService;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, JsonException {
        MovieService movieService = new MovieService();
        movieService.printMovieDetails(157336);
    }
}