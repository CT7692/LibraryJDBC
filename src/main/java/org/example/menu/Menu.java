package org.example.menu;

import org.example.entity.User;

public class Menu {

    public static void opening() {
        System.out.println("################ Enter This Library If You Dare ################");
        System.out.println("1: Register User");
        System.out.println("2: Login");
        System.out.println("3: Exit");
    }

    public static void afterLogin(User user) {
        System.out.println("################ Welcome to the Forbidden Library, "
                + user.getUsername() +" ################");
        System.out.println("1: View Your Book Collection");
        System.out.println("2: Search Specific Book(s)");
        System.out.println("3: Check Out a Book");
        System.out.println("4: Return a Book");
        System.out.println("5: View All Books In Stock");
        System.out.println("6: Exit");
    }
}
