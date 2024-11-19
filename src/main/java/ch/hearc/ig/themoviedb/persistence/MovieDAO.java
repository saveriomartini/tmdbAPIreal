package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.service.DbService;
import ch.hearc.ig.themoviedb.business.*;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OraclePreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class MovieDAO {

    // Create
    public static Integer insertMovie(Movie movie) throws SQLException {
        Connection db = DbService.getConnection();
        OraclePreparedStatement dbStatement = null;
        ResultSet rs = null;
        Integer rID = null;

        try {
            db.setAutoCommit(false);
            String sql = "INSERT INTO MOVIES (ID_THEMOVIEDB, ORIGINAL_TITLE, RELEASE_DATE, RATING, POSTER_PATH, RUNTIME, ID_LANGUAGE, TAGLINE, OVERVIEW) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ID INTO ?";
            dbStatement = (OraclePreparedStatement) db.prepareStatement(sql);

            dbStatement.setInt(1, movie.getId_tmdb());
            dbStatement.setString(2, movie.getOriginal_title());
            dbStatement.setDate(3, movie.getReleaseDate());
            dbStatement.setDouble(4, movie.getRating());
            dbStatement.setString(5, movie.getPoster_path());
            dbStatement.setInt(6, movie.getRuntime());

            // Fetch the language ID
            Integer languageId = null;
            if (movie.getOriginal_language() != null) {
                languageId = LanguageDAO.getLanguageIdByCode(movie.getOriginal_language().getCode());
            }
            if (languageId != null) {
                dbStatement.setInt(7, languageId);
            } else {
                // Default to English (ID = 1)
                dbStatement.setInt(7, 1);
            }

            dbStatement.setString(8, movie.getTagline());
            dbStatement.setString(9, movie.getOverview());
            dbStatement.registerReturnParameter(10, OracleTypes.NUMBER);
            dbStatement.executeUpdate();
            db.commit();

            rs = dbStatement.getReturnResultSet();
            if (rs != null && rs.next()) {
                rID = rs.getInt(1);
            }
            return rID;
        } finally {
            if (dbStatement != null) dbStatement.close();
            if (rs != null) rs.close();
        }
    }



    // Read
    public static List<Movie> getAllMovies() throws SQLException {
        Connection db = DbService.getConnection();
        List<Movie> movies = new ArrayList<>();
        try (PreparedStatement dbStatement = db.prepareStatement("SELECT * FROM MOVIES");
             ResultSet dbResultSet = dbStatement.executeQuery();
        ) {
            while (dbResultSet.next()) {
                Movie movie = new Movie();
                movie.setID(dbResultSet.getInt("ID"));
                movie.setId_tmdb(dbResultSet.getInt("ID_THEMOVIEDB"));
                movie.setOriginal_title(dbResultSet.getString("ORIGINAL_TITLE"));
                movie.setRating(dbResultSet.getDouble("RATING"));
                movie.setReleaseDate(dbResultSet.getDate("RELEASE_DATE"));
                movie.setRuntime(dbResultSet.getInt("RUNTIME"));
                movie.setTagline(dbResultSet.getString("TAGLINE"));
                movie.setOverview(dbResultSet.getString("OVERVIEW"));
                movie.setPoster_path(dbResultSet.getString("POSTER_PATH"));
                movie.setOriginal_language(LanguageDAO.getLanguageById(dbResultSet.getInt("ID_LANGUAGE")));

                movies.add(movie);
            }
            return movies;
        }


    }

    // Update
    public static void updateMovieRating(int tmdb_id, double rating) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("UPDATE MOVIES SET rating = ? WHERE ID_THEMOVIEDB = ?");
        ) {
            dbStatement.setDouble(1, rating);
            dbStatement.setInt(2, tmdb_id);

            dbStatement.executeUpdate();
        }
    }


    // Delete
    public static void deleteMovie(int tmdb_id) throws SQLException {
        Connection db = DbService.getConnection();
        try (PreparedStatement dbStatement = db.prepareStatement("DELETE FROM MOVIES WHERE ID_THEMOVIEDB = ?");
        ) {
            dbStatement.setInt(1, tmdb_id);
            dbStatement.executeUpdate();
        }
        }

    //ADD GENRE TO MOVIE
    public static long assignGenreToMovie(int genreID, int movieID) {
        Connection c = null;
        oracle.jdbc.OraclePreparedStatement pstmt = null;
        ResultSet rs = null;

        long rNumero = -1;

        try {
            c = DbService.getConnection();

            String sql = "insert into MOVIES_GENRES (MOVIE_ID, GENRE_ID) values (?, ?) returning ID into ?";
            pstmt = (oracle.jdbc.OraclePreparedStatement) c.prepareStatement(sql);

            pstmt.setLong(1, movieID);
            pstmt.setLong(2, genreID);
            pstmt.registerReturnParameter(3, OracleTypes.NUMBER);
            pstmt.executeUpdate();

            rs = pstmt.getReturnResultSet();
            while (rs.next()) {
                rNumero = rs.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return rNumero;
    }
}