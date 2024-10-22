package ch.hearc.ig.themoviedb.presentation;

import ch.hearc.ig.themoviedb.persistence.FakeDB;
import ch.hearc.ig.themoviedb.service.MovieService;
import ch.hearc.ig.themoviedb.business.Movie;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    private static Scanner scanner;
    private static FakeDB fakeDB = new FakeDB();
    private static Movie currentMovie;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        MovieService movieService = new MovieService();

        System.out.println("Welcome to TheMovieDB! What would you like to do?");
        int choice;
        do {
            printMainMenu();
            choice = readInt();
            proceedMainMenu(choice, movieService);
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

    private static void proceedMainMenu(int choice, MovieService movieService) {
        try {
            switch (choice) {
                case 1:
                    searchMovieById(movieService);
                    break;
                case 2:
                    //searchMovieByKeyword(movieService);
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
        } catch (IOException | JsonException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private static void searchMovieById(MovieService movieService) throws IOException, JsonException {
        System.out.println("Please enter the movie ID:");
        int movieId = readInt();
        currentMovie = movieService.getMovieDetails(movieId);
        System.out.println("Movie found: " + currentMovie);
    }



    private static void addCurrentMovieToCollection() {
        if (currentMovie != null) {
            fakeDB.addMovie(currentMovie);
            System.out.println("The current movie has been added to the collection.");
        } else {
            System.out.println("No current movie to add. Please search for a movie first.");
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