package EventRegistration;

public class User {
    private String name, email, username, password, role;

    public User(String name, String email, String username, String password, String role) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }

    public boolean isAdmin() { return "ADMIN".equals(role); }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

