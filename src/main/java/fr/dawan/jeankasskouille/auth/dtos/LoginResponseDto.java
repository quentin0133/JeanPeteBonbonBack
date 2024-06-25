package fr.dawan.jeankasskouille.auth.dtos;

import fr.dawan.jeankasskouille.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record LoginResponseDto(LoginResponseDto.UserDto user,
                               String token) implements Serializable {
    public record UserDto(long id, String firstName, String lastName, String email, Set<Role> roles)
            implements Serializable {
    }
}
