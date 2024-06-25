package fr.dawan.jeankasskouille.auth;

import fr.dawan.jeankasskouille.auth.dtos.LoginResponseDto;
import fr.dawan.jeankasskouille.auth.dtos.RegisterDto;
import fr.dawan.jeankasskouille.user.User;
import fr.dawan.jeankasskouille.user.UserMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface AuthMapper {
    @Mapping(target = "token", expression = "java(fr.dawan.jeankasskouille.tools.JwtUtils.generateToken(security))")
    LoginResponseDto toLoginResponse(UserSecurity security);

    User fromRegister(RegisterDto register);

    LoginResponseDto.UserDto toUserDto(User user);
}
