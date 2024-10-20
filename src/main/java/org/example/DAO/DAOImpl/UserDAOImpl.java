package org.example.DAO.DAOImpl;

import org.example.DAO.UserDAO;
import org.example.connection.ConnectionFactory;
import org.example.entity.Book;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDAOImpl implements UserDAO {

    private Connection connection;

    public UserDAOImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public boolean registerUser(User user) {
        boolean success = false;
        try {
            String registerQuery = "insert into user(" +
                    "username, password) values(?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(registerQuery);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int rows = preparedStatement.executeUpdate();
            success = true;
            System.out.println(rows + " user added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }

    @Override
    public User loginUser(String username, String password) {
        User user = null;
        try {
            String loginQuery = "select * from user where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getString(2),
                        resultSet.getString(3));
                user.setUserID(resultSet.getInt(1));
                System.out.println("Login successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    @Override
    public void viewBorrowedBook(HashMap<String, Book> books) {
        System.out.println("################ Book Collection ################");
        for (Book book : books.values()) System.out.println(book);
    }
}
