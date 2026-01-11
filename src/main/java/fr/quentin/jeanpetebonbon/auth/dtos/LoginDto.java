package fr.quentin.jeanpetebonbon.auth.dtos;

import fr.quentin.jeanpetebonbon.auth.user.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Login query dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {
    private UserDto user;

    private String token;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LoginDto that = (LoginDto) object;
        return Objects.equals(user, that.user) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, token);
    }

    @Override
    public String toString() {
        return "LoginQueryDto{" +
            "user=" + getUser() +
            '}';
    }
}