package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.service.DbService;
import ch.hearc.ig.themoviedb.business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class RealDB {
    public static long insertMovie(Movie movie) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("INSERT INTO MOVIES (id, original_title, rating, releasedate, runtime, tagline, overview) VALUES (?,?,?,?,?,?,?)");
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

    public static CharSequence getAllMovies() {
        return null;
    }
}