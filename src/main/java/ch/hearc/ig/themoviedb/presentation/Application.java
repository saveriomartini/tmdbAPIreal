package ch.hearc.ig.themoviedb.presentation;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.TmdbItem;
import ch.hearc.ig.themoviedb.network.SocketClientService;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Application {

    private static Scanner scanner;
    private static Movie currentMovie;
    private static Map<Integer, TmdbItem> cache = new HashMap<>();

    public static void main(String[] args) {
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
        System.out.println("1. Search for a movie calling the API");
        System.out.println("======================================================");
        System.out.println("2. Create your collection from cache");
        System.out.println("3. Read all movies in the collection");
        System.out.println("4. Update a movie in the collection");
        System.out.println("5. Delete a movie from the collection");
        System.out.println("======================================================");
        System.out.println("0. Quit");
        System.out.println("======================================================");

    }

    private static void proceedMainMenu(int choice, Map <Integer, TmdbItem> cache) {
        try {
            switch (choice) {
                case 1:
                    System.out.println("=======================SEARCHING=========================");
                    int movieId = searchMovieByKeyword(1, cache);
                    cache.put(movieId, currentMovie);
                    System.out.println(currentMovie);
                    break;
                case 2:
                    System.out.println("==============CREATING=======================");
                    persistCache(cache);
                    break;
                case 3:
                    System.out.println("================READING================================");
                    readCollection();
                    break;
                case 4:
                    System.out.println("====================UPDATING=========================");
//                    System.out.println("Please enter the movie ID:");
//                    int id = readInt();
//                    MovieDAO.updateMovieRating(id, 5.0);
//                    updateMovieInCollection();
                    break;
                case 5:
                    System.out.println("===================DELETING============================");

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
        //movieDAO.updateMovieRating(currentMovie.getId_tmdb(), Double.parseDouble(newrating));
        System.out.println("The movie ID has been updated.");
    }


    private static void searchMovieById() throws IOException, JsonException {
        System.out.println("Please enter the movie ID:");
        int movieId = readInt();
               System.out.println(currentMovie);
    }

    private static int searchMovieByKeyword(int choice, Map<Integer, TmdbItem>cache) throws IOException, JsonException {

        SocketClientService socketClientService = new SocketClientService();
        socketClientService.network(1,cache);
        return readInt();

    }

    private static void persistCache(Map<Integer, TmdbItem> cache) throws SQLException {

        SocketClientService socketClientService = new SocketClientService();
        socketClientService.network(2, cache);

    }

    private static void readCollection() throws SQLException {
        SocketClientService socketClientService = new SocketClientService();
        socketClientService.network(3, null);
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
