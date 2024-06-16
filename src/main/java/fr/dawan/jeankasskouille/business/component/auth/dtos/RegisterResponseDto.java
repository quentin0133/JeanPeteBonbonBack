package fr.dawan.jeankasskouille.business.component.auth.dtos;

import fr.dawan.jeankasskouille.business.component.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record RegisterResponseDto(fr.dawan.jeankasskouille.business.component.auth.dtos.LoginResponseDto.UserDto user)
        implements Serializable {
    public record UserDto(long id, String firstName, String lastName, String email, Set<Role> roles)
            implements Serializable {
    }
}
