package ch.hearc.ig.themoviedb.presentation;

import ch.hearc.ig.themoviedb.business.MovieBuilder;
import ch.hearc.ig.themoviedb.persistence.FakeDB;
import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.persistence.RealDB;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static Scanner scanner;
   //private static FakeDB fakeDB = new FakeDB();
    private static RealDB realDB = new RealDB();
    private static Movie currentMovie;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        MovieBuilder movieBuilder = new MovieBuilder();

        System.out.println("Welcome to TheMovieDB! What would you like to do?");
        int choice;
        do {
            printMainMenu();
            choice = readInt();
            proceedMainMenu(choice, movieBuilder);
        } while (choice != 0);
    }

    private static void printMainMenu() {
        System.out.println("======================================================");
        System.out.println("What would you like to do?");
        System.out.println("1. Search a movie by ID");
        System.out.println("2. Search a movie by keyword");
        System.out.println("3. Add the current movie to the collection");
        System.out.println("4. Display all movies in the collection");
        System.out.println("0. Quit the program");
    }

    private static void proceedMainMenu(int choice, MovieBuilder movieBuilder) {
        try {
            switch (choice) {
                case 1:
                    searchMovieById(movieBuilder);
                    break;
                case 2:
                    searchMovieByKeyword(movieBuilder);
                    break;
                case 3:
                    addCurrentMovieToCollection();
                    break;
                case 4:
                    System.out.println(FakeDB.getAllMovies().toString());
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Error: Invalid input. Please try again.");
                    break;
            }
        } catch (IOException | JsonException | SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private static void searchMovieById(MovieBuilder movieBuilder) throws IOException, JsonException {
        System.out.println("Please enter the movie ID:");
        int movieId = readInt();
        currentMovie = movieBuilder.getMovieDetails(movieId);
        System.out.println(currentMovie);
    }

    private static void searchMovieByKeyword(MovieBuilder movieBuilder) throws IOException, JsonException {
        System.out.println("Please enter the keyword:");
        String keyword = readString();

        // Get the list of movies based on the keyword search
        List<Movie> movies = movieBuilder.getMovieDetails(keyword); // Updated method name

        System.out.println("Search results:");
        for (Movie movie : movies) {
            try {
                // For each movie in the list, use the ID to fetch full details
                Movie detailedMovie = movieBuilder.getMovieDetails(movie.getId());
                System.out.println(detailedMovie);
            } catch (Exception e) {
                System.err.println("Error fetching details for movie ID " + movie.getId() + ": " + e.getMessage());
            }
        }
    }





    private static void addCurrentMovieToCollection() throws SQLException {
        if (currentMovie != null) {
            RealDB.insertMovie(currentMovie);
            System.out.println("The current movie has been added to the collection.");
        } else {
            System.out.println("No current movie to add. Please search for a movie first.");
        }
    }

    private static void displayAllMoviesInCollection() {
        assert RealDB.getAllMovies() != null;
        if (RealDB.getAllMovies().isEmpty()) {
            System.out.println("The collection is empty.");
        } else {
            System.out.println(RealDB.getAllMovies().toString());
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