package fr.dawan.jeanpetebonbon.user.dtos;

import fr.dawan.jeanpetebonbon.user.enums.Role;

import java.io.Serializable;
import java.util.Set;

public record UserDto(long id, int version, String firstName, String lastName, String email, Set<Role> roles)
        implements Serializable {
}
