package ch.hearc.ig.themoviedb.network;

import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.persistence.MovieDAO;
import ch.hearc.ig.themoviedb.service.APIService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Receive keywords from client
            String keywords = (String) in.readObject();
            System.out.println("Keywords received: " + keywords);

            // Search for the movie
            String response = APIService.searchMovie(keywords);

            // Send the response to the client
            out.writeObject(response);

            // Receive the movie id from the client
            int id = (int) in.readObject();
            System.out.println("Movie id received: " + id);

            // Get the movie details
            Movie detailedMovie = APIService.getMovieInfoObject(id);

            // Send the movie details to the client
            out.writeObject(detailedMovie);






        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
