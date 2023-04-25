package edu.duke.ece651.team3.shared;

import javax.swing.*;

public class User {
    private String username;
    private JPasswordField password;

    public User(String _username, JPasswordField _password) {
        this.username = _username;
        this.password = _password;
    }
}
