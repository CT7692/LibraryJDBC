package org.example.DAO;

import org.example.entity.Book;
import org.example.entity.User;

import java.util.HashMap;

public interface UserDAO {
    boolean registerUser(User user);
    User loginUser(String username, String password);
    void viewBorrowedBook(HashMap<String, Book> books);
}
