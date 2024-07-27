package fr.dawan.jeanpetebonbon.auth;

import fr.dawan.jeanpetebonbon.auth.dtos.LoginResponseDto;
import fr.dawan.jeanpetebonbon.auth.dtos.RegisterDto;
import fr.dawan.jeanpetebonbon.user.User;
import fr.dawan.jeanpetebonbon.user.UserMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface AuthMapper {
    @Mapping(target = "token", expression = "java(fr.dawan.jeanpetebonbon.core.tools.JwtUtils.generateToken(security))")
    LoginResponseDto toLoginResponse(UserSecurity security);

    User fromRegister(RegisterDto register);

    LoginResponseDto.UserDto toUserDto(User user);
}
