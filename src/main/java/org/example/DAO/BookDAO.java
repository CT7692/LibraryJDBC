package org.example.DAO;

import org.example.entity.Book;
import org.example.entity.User;

public interface BookDAO {
    Book selectBook(User user, String title);
    boolean borrowBook(User user, Book book);
    boolean returnBook(User user, String title);
    boolean selectSubsetOfBooks(String searchCriteria, String searchValue);
    void viewAllInStockBooks();
}
