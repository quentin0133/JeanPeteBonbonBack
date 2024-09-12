package fr.dawan.jeanpetebonbon.user;

import fr.dawan.jeanpetebonbon.auth.UserSecurity;
import fr.dawan.jeanpetebonbon.user.dtos.UserDto;
import fr.dawan.jeanpetebonbon.core.exception.DisabledException;
import fr.dawan.jeanpetebonbon.core.generic.GenericServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User, UserRepository, UserDto, UserMapper> implements UserService {
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper, User.class);
    }

    @Override
    public UserDto save(UserDto dto) {
        throw new DisabledException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).map(UserSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Page<UserDto> findFiltered(UserFilter filter, Pageable pageable) {
        if (filter == null)
            filter = new UserFilter();
        return repository.findBySearch(
                filter.firstName,
                filter.lastName,
                filter.email,
                filter.role,
                pageable
        ).map(mapper::toDto);
    }
}
