package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.service.DbService;
import ch.hearc.ig.themoviedb.business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RealDB {

    // Create
    public static long insertMovie(Movie movie) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("INSERT INTO MOVIES (movie_id, original_title, rating, releasedate, runtime, tagline, overview) VALUES (?,?,?,?,?,?,?)");
        ) {
            dbStatement.setLong(1, movie.getId());
            dbStatement.setString(2, movie.getOriginal_title());
            dbStatement.setDouble(3, movie.getRating());
            dbStatement.setString(4, movie.getReleaseDate());
            dbStatement.setInt(5, movie.getRuntime());
            dbStatement.setString(6, movie.getTagline());
            dbStatement.setString(7, movie.getOverview());
            return dbStatement.executeUpdate();
        }
    }

    // Read
    public static List<Movie> getAllMovies() throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("SELECT * FROM MOVIES");
        ) {
            ResultSet rs = dbStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("original_title"),
                        rs.getDouble("rating"),
                        rs.getString("releasedate"),
                        rs.getInt("runtime"),
                        rs.getString("tagline"),
                        rs.getString("overview")
                ));
                return movies;


            }
            return null;
        }
    }

    // Update
    public void updateMovieId(int movieId) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("UPDATE MOVIES SET movie_id = ? WHERE MOVIE_ID = 0");
        ) {
            dbStatement.setInt(1, movieId);
            dbStatement.executeUpdate();
        }

    }


    // Delete
    public void deleteMovie(Movie movie) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("DELETE FROM MOVIES WHERE movie_id = 1");
        ) {
            dbStatement.executeUpdate();
        }
    }
}