package com.jonichi.envelope.auth.infrastructure.adapter.out.mapper;

import com.jonichi.envelope.auth.domain.Role;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.model.UserEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMapperTest {

    @Test
    public void toUserEntity_shouldMapUserToUserEntity() throws Exception {
        // given
        User user = new User("test", "test@mail", "12345");

        // when
        UserEntity userEntity = UserMapper.toUserEntity(user);

        // then
        assertThat(userEntity.getUsername()).isEqualTo(user.getUsername());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(userEntity.getPassword()).isEqualTo(user.getPassword());
        assertThat(userEntity.getRole()).isEqualTo(user.getRole());
    }



    @Test
    public void toUser_shouldMapUserEntityToUser() throws Exception {
        // given
        UserEntity userEntity = new UserEntity(
                1,
                "test",
                "test@mail.com",
                "secret",
                Role.USER
        );

        // when
        User user = UserMapper.toUser(userEntity);

        // then
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(user.getRole()).isEqualTo(userEntity.getRole());
    }

    @Test
    public void toUserDetails_shouldMapUserToUserDetails() throws Exception {
        // given
        User user = new User("test", "test@mail", "12345");

        // when
        UserDetails userDetails = UserMapper.toUserDetails(user);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }

}
