package ch.hearc.ig.themoviedb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class MovieAPIService {
    private static final String API_KEY = "944f634d1576af81ac19ff8342f8e7e4";

    public String fetchMovieDetails(int movieId) throws IOException {
        String urlStr = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY;
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