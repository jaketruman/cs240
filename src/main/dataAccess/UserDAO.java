package dataAccess;

import Model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection =connection;
    }


    public void clear() throws DataAccessException {
        String sql = "DELETE from user";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while clearing the Game table");
        }
    }
    public int findSize() {
        ResultSet result;
        String sql = "SELECT COUNT(*) AS row_count FROM user;";
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
    public UserModel findUser(String username) throws DataAccessException {
        UserModel user;
        ResultSet result;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, username);
            result = stmt.executeQuery();
            if (result.next()){
                user = new UserModel(result.getString("username"), result.getString("password"),
                        result.getString("email"));
                return user;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }
    public void insert(UserModel user) throws DataAccessException{
        String sql = "INSERT INTO User (username, password, email) VALUES(?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user to the database");
        }
    }
}
