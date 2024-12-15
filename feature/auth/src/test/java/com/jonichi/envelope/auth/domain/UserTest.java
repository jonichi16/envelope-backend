package com.jonichi.envelope.auth.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class UserTest {

    @Test
    public void user_shouldBeAbleToCreateUser() throws Exception {
        // given
        User user = new User("test", "test@mail.com", "secret");
        user.setId(1);
        user.setRole(Role.USER);

        // when, then
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo("test@mail.com");
        assertThat(user.getPassword()).isEqualTo("secret");
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void user_withSameUser_shouldBeEqual() throws Exception {
        // given
        User user1 = new User(1, "test", "test@mail.com", "secret", Role.USER);
        User user2 = new User(1, "test", "test@mail.com", "secret", Role.USER);

        // when, then
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    public void testEqualsAndHashCode_DifferentValues() throws Exception {
        User user1 = new User(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        User user2 = new User(2, "janedoe", "jane.doe@example.com", "password456", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }

    @Test
    public void testEquals_NullAndDifferentClass() throws Exception {
        User user = new User(1, "johndoe", "john.doe@example.com", "password123", Role.USER);

        assertThat(user).isNotEqualTo(null);
        assertThat(user).isNotEqualTo("Some String");
    }

    @Test
    public void testEquals_differentEmail() throws Exception {
        User user1 = new User(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        User user2 = new User(1, "johndoe", "jane.doe@example.com", "password123", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }

    @Test
    public void testEquals_differentPassword() throws Exception {
        User user1 = new User(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        User user2 = new User(1, "johndoe", "john.doe@example.com", "password456", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }

    @Test
    public void testEquals_sameObject() throws Exception {
        User user = new User("johndoe", "john.doe@example.com", "password123");

        assertThat(user).isEqualTo(user);
    }

}