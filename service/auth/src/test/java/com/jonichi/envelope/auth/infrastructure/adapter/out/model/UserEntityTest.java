package com.jonichi.envelope.auth.infrastructure.adapter.out.model;

import com.jonichi.envelope.auth.domain.Role;
import java.util.Collection;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class UserEntityTest {

    @Test
    public void userEntity_getAuthorities() throws Exception {
        // given
        UserEntity user = new UserEntity(
                1,
                "test",
                "test@mail.com",
                "secret",
                 Role.USER
        );

        // when
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // then
        assertThat(authorities).hasSize(1);
        assertThat(authorities).extracting(GrantedAuthority::getAuthority)
                .containsExactly(Role.USER.name());
    }

    @Test
    public void userEntity_shouldBeAbleToCreateUserEntity() throws Exception {
        // given
        UserEntity user = new UserEntity(
                1,
                "test",
                "test@mail.com",
                "secret",
                Role.USER
        );
        user.setId(2);
        user.setUsername("test2");
        user.setEmail("test2@mail.com");
        user.setPassword("secret2");
        user.setRole(Role.USER);

        // when, then
        assertThat(user.getUsername()).isEqualTo("test2");
        assertThat(user.getEmail()).isEqualTo("test2@mail.com");
        assertThat(user.getPassword()).isEqualTo("secret2");
        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getCreatedDate()).isNull();
        assertThat(user.getUpdatedDate()).isNull();
    }

    @Test
    public void userEntity_withSameUser_shouldBeEqual() throws Exception {
        // given
        UserEntity user1 = new UserEntity(
                1,
                "test",
                "test@mail.com",
                "secret",
                Role.USER
        );

        UserEntity user2 = new UserEntity(
                1,
                "test",
                "test@mail.com",
                "secret",
                Role.USER
        );

        // when, then
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    public void testEqualsAndHashCode_DifferentValues() throws Exception {
        UserEntity user1 = new UserEntity(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        UserEntity user2 = new UserEntity(2, "janedoe", "jane.doe@example.com", "password456", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }

    @Test
    public void testEquals_NullAndDifferentClass() throws Exception {
        UserEntity user = new UserEntity(1, "johndoe", "john.doe@example.com", "password123", Role.USER);

        assertThat(user).isNotEqualTo(null);
        assertThat(user).isNotEqualTo("Some String");
    }

    @Test
    public void testEquals_differentEmail() throws Exception {
        UserEntity user1 = new UserEntity(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        UserEntity user2 = new UserEntity(1, "johndoe", "jane.doe@example.com", "password123", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }

    @Test
    public void testEquals_differentPassword() throws Exception {
        UserEntity user1 = new UserEntity(1, "johndoe", "john.doe@example.com", "password123", Role.USER);
        UserEntity user2 = new UserEntity(1, "johndoe", "john.doe@example.com", "password456", Role.USER);

        assertThat(user1).isNotEqualTo(user2);
        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }
}