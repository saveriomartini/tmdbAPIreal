package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.business.Genre;
import ch.hearc.ig.themoviedb.service.DbService;
import oracle.jdbc.internal.OraclePreparedStatement;
import oracle.jdbc.internal.OracleTypes;

import java.sql.Connection;
import java.sql.ResultSet;

import static oracle.jdbc.internal.OraclePreparedStatement.*;

public class GenreDAO {

    //Create
    public static Integer insertGenre(Genre genre) {
        Connection c = null;
        OraclePreparedStatement ps = null;
        ResultSet rs = null;

        Integer rID = null;

        try {
            c = DbService.getConnection();
            c.setAutoCommit(false);
            String sql = "INSERT INTO GENRES (ID, NAME) VALUES (?, ?) RETURNING ID INTO ?";
            ps = (OraclePreparedStatement) c.prepareStatement(sql);

            ps.setInt(1, genre.getId());
            ps.setString(2, genre.getName());
            ps.registerReturnParameter(3, OracleTypes.NUMBER);
            ps.executeUpdate();
            c.commit();

            rs = ps.getReturnResultSet();
            while (rs.next()) {
                rID = rs.getInt(1);
            }
            return rID;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    public static Genre getGenrebyName(String name) {
        Connection c = null;
        OraclePreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DbService.getConnection();
            String sql = "SELECT * FROM GENRES WHERE NAME = ?";
            ps = (OraclePreparedStatement) c.prepareStatement(sql);

            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Genre(rs.getInt(1), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
