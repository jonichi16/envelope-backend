package com.jonichi.envelope.auth.infrastructure.adapter.out.model;

import com.jonichi.envelope.auth.domain.Role;
import java.time.LocalDateTime;

public class UserEntity {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserEntity(
            Integer id,
            String username,
            String email,
            String password,
            Role role
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity user = (UserEntity) o;

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
