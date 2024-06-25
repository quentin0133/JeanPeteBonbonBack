package fr.dawan.jeankasskouille.user;

import fr.dawan.jeankasskouille.user.dtos.UserDto;
import fr.dawan.jeankasskouille.core.generic.GenericService;
import fr.dawan.jeankasskouille.core.generic.filter.FilterService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<UserDto>, FilterService<UserDto, UserFilter> {
}
