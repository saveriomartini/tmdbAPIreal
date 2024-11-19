package ch.hearc.ig.themoviedb.presentation;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.TmdbItem;
import ch.hearc.ig.themoviedb.network.SocketClientService;
import ch.hearc.ig.themoviedb.network.SocketServerService;
import ch.hearc.ig.themoviedb.persistence.MovieDAO;
import ch.hearc.ig.themoviedb.service.APIService;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Application {

    private static Scanner scanner;
    private static MovieDAO movieDAO = new MovieDAO();
    private static Movie currentMovie;

    public static void main(String[] args) {
        Map<Integer, TmdbItem> cache = new HashMap<>();
        scanner = new Scanner(System.in);


        System.out.println("======================================================");
        System.out.println("Welcome to TheMovieDB SAVERIO_MARTINI !");

        int choice;
        do {
            printMainMenu(cache);
            choice = readInt();
            proceedMainMenu(choice, cache);
        } while (choice != 0);

        scanner.close();



    }

    private static void printMainMenu(Map <Integer, TmdbItem> cache) {
        System.out.println("======================================================");
        System.out.println("1. Search for a movie");
        System.out.println("======================================================");
        System.out.println("2. Create your collection");
        System.out.println("3. Read all movies in the collection");
        System.out.println("4. Update a movie in the collection");
        System.out.println("5. Delete a movie from the collection");
        System.out.println("======================================================");
        System.out.println("0. Leave");
        System.out.println("======================================================");

    }

    private static void proceedMainMenu(int choice, Map <Integer, TmdbItem> cache) {
        try {
            switch (choice) {
                case 1:
                    System.out.println("=======================SEARCHING=========================");
                    int movieId = searchMovieByKeyword();
                    String response = APIService.getMovieInfo(movieId);
                    currentMovie = APIService.getMovieInfoObject(movieId);
                    cache.put(movieId, currentMovie);
                    System.out.println(currentMovie);
                    break;
                case 2:
                    System.out.println("==============CREATING=======================");
                    for (Map.Entry<Integer, TmdbItem> entry : cache.entrySet()) {
                        MovieDAO.insertMovie((Movie) entry.getValue());
                    }
                    System.out.println("The movies have been added to the collection.");
                    break;
                case 3:
                    System.out.println("================READING================================");
                    displayAllMoviesInCollection();
                    break;
                case 4:
                    System.out.println("====================UPDATING=========================");
//                    System.out.println("Please enter the movie ID:");
//                    int id = readInt();
//                    MovieDAO.updateMovieRating(id, 5.0);
                    updateMovieInCollection();
                    break;
                case 5:
                    System.out.println("===================DELETING============================");
                    System.out.println("Please enter the movie ID:");
                    int id = readInt();
                    MovieDAO.deleteMovie(id);
                    System.out.println("The movie has been deleted.");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Error: Invalid input. Please try again.");
                    break;
            }
        } catch (IOException | JsonException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateMovieInCollection() {
        System.out.println("Please enter the movie ID:");
        String newrating = readString();
        try {
            movieDAO.updateMovieRating(currentMovie.getId_tmdb(), Double.parseDouble(newrating));
            System.out.println("The movie ID has been updated.");
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private static void searchMovieById() throws IOException, JsonException {
        System.out.println("Please enter the movie ID:");
        int movieId = readInt();
               System.out.println(currentMovie);
    }

    private static int searchMovieByKeyword() throws IOException, JsonException {

        SocketServerService socketServerService = new SocketServerService();
        SocketClientService socketClientService = new SocketClientService();
        socketServerService.run();
        socketClientService.run();






        System.out.println("Please enter the movie ID:");
        return readInt();

    }




    private static void addCurrentMovieToCollection() throws SQLException {
        if (currentMovie != null) {
            MovieDAO.insertMovie(currentMovie);
            System.out.println("The current movie has been added to the collection.");
        } else {
            System.out.println("No current movie to add. Please search for a movie first.");
        }
    }

    private static void displayAllMoviesInCollection() throws SQLException {
        assert MovieDAO.getAllMovies() != null;
        if (MovieDAO.getAllMovies().isEmpty()) {
            System.out.println("The collection is empty.");
        } else {
            System.out.println(MovieDAO.getAllMovies().size() + " movies found in the collection:");
            for (Movie movie : MovieDAO.getAllMovies()) {
                System.out.println(movie);
            }
        }
    }



    private static int readInt() {
        int i = 0;
        boolean success = false;
        do {
            try {
                i = scanner.nextInt();
                success = true;
            } catch (InputMismatchException e) {
                System.out.println("Error! Please enter an integer.");
            } finally {
                scanner.nextLine();
            }
        } while (!success);
        return i;
    }

    private static String readString() {
        return scanner.nextLine();
    }
}