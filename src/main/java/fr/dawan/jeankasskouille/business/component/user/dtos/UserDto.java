package fr.dawan.jeankasskouille.business.component.user.dtos;

import fr.dawan.jeankasskouille.business.component.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record UserDto(long id, int version, String firstName, String lastName, String email, Set<Role> roles)
        implements Serializable {
}
