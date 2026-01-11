package fr.quentin.jeanpetebonbon.auth.user;

import fr.quentin.jeanpetebonbon.core.utils.LogUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LogUtils.logEnter(log, username);

        UserDetails user = userRepository.findByUsername(username).map(UserSecurity::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LogUtils.logExit(log, username);

        return user;
    }
}
