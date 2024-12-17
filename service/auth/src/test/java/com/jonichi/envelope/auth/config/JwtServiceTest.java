package com.jonichi.envelope.auth.config;

import com.jonichi.envelope.auth.domain.Role;
import com.jonichi.envelope.auth.domain.User;
import com.jonichi.envelope.auth.infrastructure.adapter.out.mapper.UserMapper;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceTest {


    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Test secret key
        jwtService.secretKey = "r+uZcNlc4WPU5ilIFqkJTLVcEtPb3" +
                "hs7HY3gF0Ix4lo9zkr8RCgJ5c1UqQavbvfY" +
                "TfobHix3dK3mhIlh3ayHJg==";

        // 1 minute test token expiration
        jwtService.tokenExpiration = 1000 * 60;
    }

    @Test
    public void generateToken_shouldGenerateWithUser() throws Exception {
        // given
        User user = new User(1, "testUser", "test@example.com", "password123", Role.USER);

        // when
        String token = jwtService.generateToken(user);

        // then
        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("testUser");
    }

    @Test
    void generateToken_withExtraClaims() throws Exception {
        User user = new User(1, "testUser", "test@example.com", "password123", Role.USER);

        UserDetails userDetails = UserMapper.toUserDetails(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());

        String token = jwtService.generateToken(extraClaims, userDetails);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("testUser");
    }

    @Test
    void generateToken_withoutExtraClaims() throws Exception {
        User user = new User(1, "testUser", "test@example.com", "password123", Role.USER);
        UserDetails userDetails = UserMapper.toUserDetails(user);

        String token = jwtService.generateToken(userDetails);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("testUser");
    }

    @Test
    void isTokenExpired() throws Exception {
        User user = new User(1, "testUser", "test@example.com", "password123", Role.USER);
        UserDetails userDetails = UserMapper.toUserDetails(user);

        String token = jwtService.generateToken(userDetails);
        assertThat(jwtService.isTokenExpired(token)).isFalse();

        jwtService.tokenExpiration = 0; // update expiration time
        String expiredToken = jwtService.generateToken(userDetails);

        assertThat(jwtService.isTokenExpired(expiredToken)).isTrue();
    }

    @Test
    void isTokenValid() throws Exception {
        User user = new User(1, "testUser", "test@example.com", "password123", Role.USER);
        UserDetails userDetails = UserMapper.toUserDetails(user);

        String token = jwtService.generateToken(userDetails);

        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();

        User anotherUser = new User(2, "anotherUser", "other@example.com", "password123", Role.USER);
        UserDetails anotherUserDetails = UserMapper.toUserDetails(anotherUser);

        assertThat(jwtService.isTokenValid(token, anotherUserDetails)).isFalse();

        jwtService.tokenExpiration = 0; // update expiration time
        String expiredToken = jwtService.generateToken(userDetails);

        assertThat(jwtService.isTokenValid(expiredToken, userDetails)).isFalse();
    }

}
