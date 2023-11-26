package dataAccess;

import Model.AuthTokenModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthDAO {
    AuthTokenModel model;
    Connection connection;

    public AuthDAO(Connection connection) {
        this.connection = connection;
    }
    public String createAuthtoken(String username) throws DataAccessException{
        String sql = "INSERT INTO authToken (authToken, username) VALUES(?,?)";
        String authToken = UUID.randomUUID().toString();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, authToken);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person to the database");
        }
        return authToken;

    }
    public void clear() throws DataAccessException {
        String sql = "DELETE from authToken";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while clearing the Game table");
        }
    }
    public void delete(String authToken) throws DataAccessException {
        String sql = "DELETE FROM authToken WHERE authToken = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while deleting a specific event");
        }
    }

    public int findSize() {
        ResultSet result;
        String sql = "SELECT COUNT(*) AS row_count FROM AuthToken;";
        int count=0;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            result = stmt.executeQuery();
            if (result.next()) {
                count = result.getInt("row_count");
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void insert(AuthTokenModel authTokenModel) throws DataAccessException{
        String sql = "INSERT INTO authToken (authToken, username) VALUES(?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, authTokenModel.getAuthToken());
            stmt.setString(2, authTokenModel.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person to the database");
        }
    }
    public AuthTokenModel find(String authToken){
        AuthTokenModel token;
        ResultSet result;
        String sql = "SELECT * FROM authToken WHERE authToken = ?;";
        System.out.println("IN here");
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, authToken);
            result = stmt.executeQuery();
            if (result.next()){
                token = new AuthTokenModel(result.getString("username"), result.getString("authToken"));
                return token;
            }else {
                return null;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Finished nul");
            return null;
        }
    }
    public AuthTokenModel findbyUsername(String username){
        AuthTokenModel token;
        ResultSet result;
        String sql = "SELECT * FROM authToken WHERE username = ?;";
        System.out.println("IN here");
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, username);
            result = stmt.executeQuery();
            if (result.next()){
                token = new AuthTokenModel(result.getString("username"), result.getString("authToken"));
                return token;
            }else {
                return null;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Finished nul");
            return null;
        }
    }
}
