package ch.hearc.ig.themoviedb.app;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.MovieService;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;

public class Application {
     public static void main(String[] args) throws IOException, JsonException {
                 MovieService movieService = new MovieService();
                 Movie movie = movieService.getMovieDetails(519182); // Example movie ID
                 System.out.println("Title: " + movie.getTitle());
                 System.out.println("Release Date: " + movie.getReleaseDate());
                 System.out.println("Overview: " + movie.getOverview());
             }
         }



