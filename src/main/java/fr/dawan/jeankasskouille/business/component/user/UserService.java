package fr.dawan.jeankasskouille.business.component.user;

import fr.dawan.jeankasskouille.business.component.user.dtos.UserDto;
import fr.dawan.jeankasskouille.generic.GenericService;
import fr.dawan.jeankasskouille.generic.filter.FilterService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<UserDto>, FilterService<UserDto, UserFilter> {
}
