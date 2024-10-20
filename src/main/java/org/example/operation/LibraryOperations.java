package org.example.operation;

import org.example.DAO.BookDAO;
import org.example.DAO.UserDAO;
import org.example.DAOFactory.BookDAOFactory;
import org.example.DAOFactory.UserDAOFactory;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.menu.Menu;
import org.example.validation.InputValidation;

import java.util.Scanner;

public class LibraryOperations {

    private UserDAO userDAO;
    private BookDAO bookDAO;
    private Scanner sc = new Scanner(System.in);
    private InputValidation iv;

    public LibraryOperations() {
        userDAO = UserDAOFactory.getUserDAO();
        bookDAO = BookDAOFactory.getBookDAO();
        iv = new InputValidation();

        String firstOption;

        do {
            Menu.opening();
            System.out.print("Enter Option: ");
            firstOption = sc.nextLine().trim();

            if(iv.isValidOption(firstOption, 1, 3)) {
                switch(firstOption) {
                    case "1": {
                        User user = createUser();
                        boolean customerRegistered = userDAO.registerUser(user);
                        if(!customerRegistered) System.out.println("Error occurred in registration.");
                        break;
                    }

                    case "2": {
                        User user = checkUserLogin();
                        boolean loginSuccess = user != null;
                        loginOperations(loginSuccess, user);
                        break;
                    }
                }
            }
        } while(!firstOption.equals("3"));
    }

    public User createUser() {
        System.out.println("################ Register User ################");
        System.out.print("Enter Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        return new User(username, password);
    }

    public User checkUserLogin() {
        System.out.println("################ Login User ################");
        System.out.print("Enter Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();
        User user = userDAO.loginUser(username, password);
        return user;
    }

    public void loginOperations(boolean loginSuccessful, User user) {
        if(loginSuccessful) {
            String loginOption;

            do {
                Menu.afterLogin(user);
                System.out.print("Enter Option: ");
                loginOption = sc.nextLine().trim();

                if(iv.isValidOption(loginOption, 1, 6)) {
                    switch (loginOption) {

                        case "1": {
                            if(user.getBorrowCount() > 0)
                                userDAO.viewBorrowedBook(user.getBorrowedBooks());
                            else
                                System.out.println("No books yet.");
                            break;
                        }

                        case "2": {
                            boolean booksExist = specifiedBookSearch();
                            if(!booksExist) System.out.println("We don't have these books now.");
                            break;
                        }

                        case "3": {
                            Book book = selectBookToCheckOut(user);
                            boolean borrowIsSuccess = bookDAO.borrowBook(user, book);
                            if(borrowIsSuccess) System.out.println("Happy reading!");
                            break;
                        }

                        case "4": {
                            chooseBookAndReturn(user);
                            break;
                        }

                        case "5": {
                            bookDAO.viewAllInStockBooks();
                            break;
                        }
                    }
                }

            }while(!loginOption.equalsIgnoreCase("6"));
        }
        else
            System.out.println("Invalid credentials.");
    }

    public boolean specifiedBookSearch() {
        System.out.println("################ Search for a Book ################");
        System.out.print("Search By? ");
        String attribute = sc.nextLine().trim();

        System.out.print("For? ");
        String searchValue = sc.nextLine().trim();

        return bookDAO.selectSubsetOfBooks(attribute, searchValue);
    }

    public void chooseBookAndReturn(User user) {
        System.out.println("################ Return a Book ################");
        System.out.print("What's the title of the book you're returning? ");
        String title = sc.nextLine().trim();
        if (user.getBorrowedBooks().containsKey(title)) {
            boolean bookReturned = bookDAO.returnBook(user, title);
            if(!bookReturned) System.out.println("An error occurred during the returning process.");
        }
        else
            System.out.println("You don't have this book.");
    }

    public Book selectBookToCheckOut(User user) {
        System.out.println("################ Check Out Book ################");
        System.out.print("Enter Title: ");
        String title =  sc.nextLine().trim();
        return bookDAO.selectBook(user, title);
    }
}
