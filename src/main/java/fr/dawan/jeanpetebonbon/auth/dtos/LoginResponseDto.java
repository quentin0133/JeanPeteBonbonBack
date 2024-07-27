package fr.dawan.jeanpetebonbon.auth.dtos;

import fr.dawan.jeanpetebonbon.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record LoginResponseDto(LoginResponseDto.UserDto user,
                               String token) implements Serializable {
    public record UserDto(long id, String firstName, String lastName, String email, Set<Role> roles)
            implements Serializable {
    }
}
