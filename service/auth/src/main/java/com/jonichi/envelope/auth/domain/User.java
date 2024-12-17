package com.jonichi.envelope.auth.domain;

/**
 * Represents a user in the system.
 *
 * <p>The {@code User} class holds the essential information about a user, including the username,
 * email, password, and role. It is used to represent authenticated users, and it contains methods
 * for managing the user's identity and credentials.</p>
 */
public class User {

    private Integer id = null;
    private final String username;
    private final String email;
    private final String password;
    private Role role = Role.USER;

    /**
     * Creates a new user with the provided username, email, and password. The role is set to
     * {@code USER} by default.
     *
     * @param username the username of the user
     * @param email the email address of the user
     * @param password the password for the user
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Creates a new user with the provided id, username, email, password, and role.
     *
     * @param id the unique identifier of the user
     * @param username the username of the user
     * @param email the email address of the user
     * @param password the password for the user
     * @param role the role of the user, default is {@code USER}
     */
    public User(Integer id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;

        if (!username.equals(user.username)) {
            return false;
        }
        if (!email.equals(user.email)) {
            return false;
        }
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
