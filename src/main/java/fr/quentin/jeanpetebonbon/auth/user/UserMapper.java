package fr.quentin.jeanpetebonbon.auth.user;

import fr.quentin.jeanpetebonbon.auth.user.dtos.UserDto;

/**
 * The interface User mapper.
 */
public interface UserMapper {
    /**
     * To dto user dto.
     *
     * @param userSecurity the user security
     * @return the user dto
     */
    UserDto toDto(UserSecurity userSecurity);
}
