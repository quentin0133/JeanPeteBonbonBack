package fr.dawan.jeanpetebonbon.user;

import fr.dawan.jeanpetebonbon.user.dtos.UserDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericService;
import fr.dawan.jeanpetebonbon.core.generic.filter.FilterService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<UserDto>, FilterService<UserDto, UserFilter> {
}
