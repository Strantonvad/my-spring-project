package ru.geekbrains.persistance;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

public class User {

    private int id;

    @NotBlank
    private String login;

    @NotBlank
    private String password;


    private String matchingPassword;

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @AssertTrue(message="Your password and confirmation password do not match!")
    public boolean isValidPassword() {
        return this.password.equals(this.matchingPassword);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
