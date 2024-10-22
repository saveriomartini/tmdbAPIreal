package ch.hearc.ig.themoviedb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class APIService {
    private static final String API_KEY = "944f634d1576af81ac19ff8342f8e7e4";
    private static final String LANGUAGE = "en-en";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public String fetchMovieDetails(int movieId) throws IOException {
        String urlStr = BASE_URL + movieId + "?api_key=" + API_KEY + "&language=" + LANGUAGE;
        return getURL(urlStr);
    }

    public String fetchMovieCredits(int movieId) throws IOException {
        String urlStr = BASE_URL + movieId + "/credits?api_key=" + API_KEY + "&language=" + LANGUAGE;
        return getURL(urlStr);
    }

    public String searchMovie(String keywords) throws IOException {
        keywords = keywords.replace(" ", "%20");
        String urlStr = "https://api.themoviedb.org/3/search/movie?query=" + keywords + "&api_key=" + API_KEY + "&language=" + LANGUAGE;
        return getURL(urlStr);
    }

    private String getURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader br;
        if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        return br.lines().collect(Collectors.joining());
    }
}