package fr.dawan.jeankasskouille.user.dtos;

import fr.dawan.jeankasskouille.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record UserDto(long id, int version, String firstName, String lastName, String email, Set<Role> roles)
        implements Serializable {
}
