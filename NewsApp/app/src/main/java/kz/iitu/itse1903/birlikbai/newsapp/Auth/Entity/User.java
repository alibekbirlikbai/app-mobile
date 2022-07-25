package kz.iitu.itse1903.birlikbai.newsapp.Auth.Entity;


public class User {
    private String Username;
    private String Email;
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        Username = username;
        Email = email;
        this.password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
