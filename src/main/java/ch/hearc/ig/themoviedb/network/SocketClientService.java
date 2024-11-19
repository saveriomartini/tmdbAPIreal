package ch.hearc.ig.themoviedb.network;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.TmdbItem;
import ch.hearc.ig.themoviedb.service.APIService;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SocketClientService implements Runnable {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2222;

    @Override
    public void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            //ask user for keywords
            String keywords;
        do {
            System.out.println("Enter keywords to search for a movie (at least 2 characters, no special characters):");
            Scanner scanner = new Scanner(System.in);
            keywords = scanner.nextLine();
        } while (keywords.length() < 2 || !keywords.matches("[a-zA-Z0-9 ]+"));

            //send keywords to server
            out.writeObject(keywords);


            //receive response from server
            String response = (String) in.readObject();

            //display search results
            displayKeywordsResults(response);

            //ask user for movie id
            int id = getIdFromUser();
            //send id to server
            out.writeObject(id);

            //receive detailed movie details from server
            Movie detailedMovie = (Movie) in.readObject();

            //display movie details
            System.out.println("Movie details:");
            System.out.println(detailedMovie);





        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void displayKeywordsResults(String response) {
        List<Movie> movies = APIService.getMoviesBasicsFromJson(response);
        System.out.println("Search results:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    private static int getIdFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter TMDB ID of the movie:");
        return scanner.nextInt();
    }

}
