package fr.quentin.jeanpetebonbon.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * The type Login command dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommandDto {
    private String username;
    private String password;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LoginCommandDto that = (LoginCommandDto) object;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginCommandDto{" +
            "username='" + getUsername() + '\'' +
            '}';
    }
}
