package ch.hearc.ig.themoviedb.network;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.TmdbItem;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class SocketClientService {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2222;
    private static final String SEARCH_MOVIE = "SEARCH_MOVIE";
    private static final String PERSIST_CACHE = "PERSIST_CACHE";
    private static final String READ_COLLECTION = "READ_COLLECTION";
    private static final String UPDATE_MOVIE = "UPDATE_MOVIE";
    private static final String DELETE_MOVIE = "DELETE_MOVIE";



    public void network(int choice, Map<Integer, TmdbItem> cache) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);

            switch (choice) {
                case 1:
                    out.writeObject(SEARCH_MOVIE);
                    System.out.println("Enter keywords to search for a movie:");
                    String keywords = scanner.nextLine();
                    out.writeObject(keywords);
                    String response = (String) in.readObject();
                    displayKeywordsResults(response);
                    int id = getIdFromUser();
                    out.writeObject(id);
                    Movie detailedMovie = (Movie) in.readObject();
                    cache.put(id, detailedMovie);
                    System.out.println("Movie details:");
                    System.out.println(detailedMovie);
                    break;
                case 2:
                    out.writeObject(PERSIST_CACHE);
                    out.writeObject(cache);
                    break;
                case 3:
                    // send server command 3
                    out.writeObject(READ_COLLECTION);
                    // get collection from server
                    List<Movie> collection = (List<Movie>) in.readObject();
                    // display collection
                    System.out.println("Collection:");
                    for (Movie movie : collection) {
                        System.out.println(movie);
                    }
                    if (collection.isEmpty()) {
                        System.out.println("Collection is empty.");
                    }
                    else if (collection.size() == 1) {
                        System.out.println("Collection contains 1 movie:");
                    }
                    else {
                        System.out.println("Collection contains " + collection.size() + " movies:");
                    }
                    System.out.println("Collection:");
                    for (Movie item : collection) {
                        if (item instanceof Movie) {
                            System.out.println(item);
                        }
                        else {
                        System.out.println(item);
                    }
                    }
                    break;
                case 4:
                    // send server command 4
                    out.writeObject(UPDATE_MOVIE);
                    // ask user for movie id
                    int idToUpdate = getIdFromUser();
                    // send id to server
                    out.writeObject(idToUpdate);
                    // receive movie details from server
                    // ask user for new movie rating
                    System.out.println("Enter new rating:");
                    double newRating = scanner.nextDouble();
                    // send new rating to server
                    out.writeObject(newRating);
                    // receive updated movie details from server
                    Movie updatedMovie = (Movie) in.readObject();
                    // display updated movie details
                    System.out.println("Updated movie details:");
                    System.out.println(updatedMovie);
                    break;
                case 5:
                    // send server command 5
                    out.writeObject(DELETE_MOVIE);
                    // ask user for movie id
                    int idToDelete = getIdFromUser();
                    // send id to server
                    out.writeObject(idToDelete);
                    // receive movie details from server
                    // send movie details to server
                    out.writeObject(cache.get(idToDelete));
                    // receive confirmation from server
                    boolean isDeleted = (boolean) in.readObject();
                    // display confirmation
                    if (isDeleted) {
                        System.out.println("Movie has been deleted.");
                    }
                    else {
                        System.out.println("Movie not found in collection.");
                    }
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayKeywordsResults(String response) {
        List<Movie> movies = getMoviesBasicsFromJson(response);
        System.out.println("Search results:");
        for (Movie movie : movies) {
            System.out.println(movie.displayBasics());
        }

    }

    private static int getIdFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter TMDB ID of the movie:");
        return scanner.nextInt();
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
                String releaseDate = (String) movieObject.get("release_date");
                if (releaseDate != null && !releaseDate.isEmpty()) {
                movie.setReleaseDate(Date.valueOf(releaseDate));
                movies.add(movie);}
                else {
                    movie.setReleaseDate(null);
                }


            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return movies;
    }
}