package fr.quentin.jeanpetebonbon.auth.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type User dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private long id;

    private String username;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDto userDto = (UserDto) object;
        return id == userDto.id && Objects.equals(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "id=" + getId() +
            ", username='" + getUsername() + '\'' +
            '}';
    }
}