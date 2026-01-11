package fr.quentin.jeanpetebonbon.auth.user;

import fr.quentin.jeanpetebonbon.auth.user.dtos.UserDto;
import fr.quentin.jeanpetebonbon.core.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(UserSecurity userSecurity) {
        if (userSecurity == null) {
            LogUtils.logEnter(log, (Object) null);
            LogUtils.logExit(log, (Object) null);
            return null;
        }

        User user = userSecurity.getUser();

        LogUtils.logEnter(log, user.getUsername());

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        LogUtils.logExit(log, dto);

        return dto;
    }
}
