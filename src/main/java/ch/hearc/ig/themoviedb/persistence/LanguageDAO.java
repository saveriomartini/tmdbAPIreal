package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.business.Language;
import ch.hearc.ig.themoviedb.service.DbService;
import oracle.jdbc.internal.OraclePreparedStatement;
import oracle.jdbc.internal.OracleTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDAO {
    //Create
    public static Integer insertLanguage(Language language) {
        Connection c = null;
        OraclePreparedStatement ps = null;
        ResultSet rs = null;

        Integer rID = null;

        try {
            c = DbService.getConnection();
            c.setAutoCommit(false);
            String sql = "INSERT INTO LANGUAGES (ID, CODE) VALUES (?, ?) RETURNING ID INTO ?";
            ps = (OraclePreparedStatement) c.prepareStatement(sql);

            ps.setInt(1, language.getId());
            ps.setString(2, language.getCode());
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

    //Read
    public static Language getLanguageById(int idLanguage) {
        Connection c = null;
        OraclePreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DbService.getConnection();
            String sql = "SELECT * FROM LANGUAGES WHERE ID = ?";
            ps = (OraclePreparedStatement) c.prepareStatement(sql);

            ps.setInt(1, idLanguage);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Language(rs.getInt("ID"), rs.getString("CODE"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    public static Integer getLanguageIdByCode(String code) throws SQLException {
        Connection connection = DbService.getConnection();
        String query = "SELECT ID FROM LANGUAGES WHERE CODE = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        }
        return null; // Return null if the language code is not found
    }

}
