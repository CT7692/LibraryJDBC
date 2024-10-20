package org.example.entity;

import java.util.HashMap;

public class User {
    private int userID;
    private String username;
    private String password;
    private int borrowCount;
     private HashMap<String, Book> borrowedBooks;

    public User(String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.borrowCount = 0;
        borrowedBooks = new HashMap<String, Book>();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public HashMap<String, Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBook(Book book) {
            this.borrowedBooks.put(book.getTitle(), book);
            this.borrowCount++;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", borrowCount=" + borrowCount +
                '}';
    }
}
