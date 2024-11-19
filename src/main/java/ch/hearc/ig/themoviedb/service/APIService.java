package ch.hearc.ig.themoviedb.service;

import ch.hearc.ig.themoviedb.business.*;
import com.github.cliftonlabs.json_simple.*;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class APIService implements Jsonable {
    private static final String API_KEY = "944f634d1576af81ac19ff8342f8e7e4";
    private static final String LANGUAGE = "en-en";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";


    public static String getURL(String url) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }


    public static String searchMovie(String keywords) throws IOException {
        keywords = keywords.replace(" ", "%20");
        String url = STR."https://api.themoviedb.org/3/search/movie?query=\{keywords}&api_key=\{API_KEY}&language=\{LANGUAGE}";
        System.out.println(url);
        return getURL(url);
    }


    public static List<Movie> getMoviesBasicsFromJson(String json) {
        List<Movie> movies = new ArrayList<>();
        try {
            JsonObject deserializedObject = (JsonObject) Jsoner.deserialize(json);
            JsonArray results = (JsonArray) deserializedObject.get("results");
            for (int i = 0; i < results.size(); i++) {
                JsonObject movieObject = (JsonObject) results.get(i);
                Movie movie = new Movie();
                movie.setId_tmdb(((BigDecimal) movieObject.get("id")).intValue());
                movie.setOriginal_title((String) movieObject.get("original_title"));
                movie.setReleaseDate(Date.valueOf((String) movieObject.get("release_date")));
                movie.setTagline((String) movieObject.get("tagline"));
                movies.add(movie);
            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return movies;
    }


    public static String getMovieInfo(int id_tmdb) throws IOException {
        String url = BASE_URL + id_tmdb + "?api_key=" + API_KEY + "&language=" + LANGUAGE;
        System.out.println(url);
        return getURL(url);
    }

    public static Movie getMovieInfoObject(int id_tmdb) throws IOException, SQLException {
        String response = getMovieInfo(id_tmdb);
        Movie movie = new Movie();
        movie.setId_tmdb(id_tmdb);

        JsonObject deserializedObject = null;
        try {
            deserializedObject = (JsonObject) Jsoner.deserialize(response);
        } catch (JsonException e) {
            e.printStackTrace();
        }

        // Extract the basic details
        movie.setOriginal_title((String) deserializedObject.get("original_title"));
        movie.setTagline((String) deserializedObject.get("tagline"));
        movie.setOverview((String) deserializedObject.get("overview"));

        // Extract the poster_path
        String posterPath = (String) deserializedObject.get("poster_path");
        if (posterPath != null && !posterPath.isEmpty()) {
            movie.setPoster_path("https://image.tmdb.org/t/p/w500" + posterPath);
        } else {
            movie.setPoster_path(null); // Handle cases where the poster_path is missing
        }

        // Handle other fields...
        BigDecimal ratingDecimal = (BigDecimal) deserializedObject.get("vote_average");
        if (ratingDecimal != null) {
            movie.setRating(ratingDecimal.doubleValue());
        }

        String releaseDateStr = (String) deserializedObject.get("release_date");
        if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
            movie.setReleaseDate(Date.valueOf(releaseDateStr));
        }

        BigDecimal runtimeDecimal = (BigDecimal) deserializedObject.get("runtime");
        if (runtimeDecimal != null) {
            movie.setRuntime(runtimeDecimal.intValue());
        }

        // Extract the original language
        Language lang = new Language();
        lang.setCode((String) deserializedObject.get("original_language"));
        movie.setOriginal_language(lang);

        // Extract the genres
        JsonArray genres = (JsonArray) deserializedObject.get("genres");
        if (genres != null && !genres.isEmpty()) {
            Set<Genre> genreSet = new HashSet<>();
            for (int i = 0; i < genres.size(); i++) {
                JsonObject genreObject = (JsonObject) genres.get(i);
                Genre genre = new Genre();
                genre.setId(((BigDecimal) genreObject.get("id")).intValue());
                genre.setName((String) genreObject.get("name"));
                genreSet.add(genre);
            }
            movie.setGenres(genreSet);
        }

        return movie;
    }


    @Override
    public String toJson() {
        return "";
    }

    @Override
    public void toJson(Writer writer) throws IOException {

    }
}
