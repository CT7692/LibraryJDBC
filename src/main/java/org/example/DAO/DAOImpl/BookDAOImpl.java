package org.example.DAO.DAOImpl;

import org.example.DAO.BookDAO;
import org.example.connection.ConnectionFactory;
import org.example.entity.Book;
import org.example.entity.User;

import java.sql.*;

public class BookDAOImpl implements BookDAO {

    private Connection connection;

    public BookDAOImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Book selectBook(User user, String title) {

        Book book = null;

        try {
            String bookSearch = "select * from books where title = ? and in_stock = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(bookSearch);
            preparedStatement.setString(1, title);
            preparedStatement.setBoolean(2, true);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("genre")
                );
                book.setInStock(false);
                book.setUserID(user.getUserID());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return book;
    }

    @Override
    public boolean borrowBook(User user, Book book) {
        boolean success = false;

        if (book != null) {

            if(user.getBorrowCount() < 5) {
                try {
                    String rentProcedure = "{call rentBook(?, ?)}";
                    CallableStatement callableStatement = connection.prepareCall(rentProcedure);
                    callableStatement.setInt(1, book.getBookID());
                    callableStatement.setInt(2, user.getUserID());
                    callableStatement.execute();
                    user.addBook(book);
                    success = true;
                }  catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            else System.out.println("Cannot borrow more than 5 books.");
        }
        else System.out.println("This is not in stock.");

        return success;
    }

    @Override
    public boolean returnBook(User user, String title) {

        boolean success = false;

        if (user.getBorrowCount() > 0) {

            Book bookToReturn = user.getBorrowedBooks().get(title);
            try {
                String returnProcedure = "{call returnBook(?, ?)}";
                CallableStatement callableStatement = connection.prepareCall(returnProcedure);
                callableStatement.setInt(1, bookToReturn.getBookID());
                callableStatement.setInt(2, user.getUserID());
                callableStatement.execute();
                user.getBorrowedBooks().remove(title);

                int borrowCount = user.getBorrowCount();
                user.setBorrowCount(borrowCount - 1);

                success = true;
                System.out.println("Book returned.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return success;
    }

    @Override
    public void viewAllInStockBooks() {
        try {
            String selectQuery = "select * from books where in_stock = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setBoolean(1, true);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst())
                System.out.println("No books in stock.");
            else {
                System.out.println("################ All Books In Stock ################");
                while(resultSet.next()) {
                    Book book = new Book(
                            resultSet.getInt("book_id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("genre"));
                    resultSet.getBoolean("in_stock");
                    System.out.println(book);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean selectSubsetOfBooks(String searchCriteria, String searchValue) {

        boolean success = false;
        String searchQuery = "select * from books where " +
                searchCriteria.toLowerCase() + " = ? and in_stock = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, searchValue);
            preparedStatement.setBoolean(2, true);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst())
                return false;
            else {
                success = true;
                while(resultSet.next()) {
                    Book book = new Book(
                            resultSet.getInt("book_id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("genre"));
                    resultSet.getBoolean("in_stock");
                    System.out.println(book);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }
}
