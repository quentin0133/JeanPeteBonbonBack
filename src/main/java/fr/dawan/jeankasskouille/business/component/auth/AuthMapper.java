package fr.dawan.jeankasskouille.business.component.auth;

import fr.dawan.jeankasskouille.business.component.auth.dtos.LoginResponseDto;
import fr.dawan.jeankasskouille.business.component.auth.dtos.RegisterDto;
import fr.dawan.jeankasskouille.business.component.auth.dtos.RegisterResponseDto;
import fr.dawan.jeankasskouille.business.component.user.User;
import fr.dawan.jeankasskouille.business.component.user.UserMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class)
public interface AuthMapper {
    @Mapping(target = "token", expression = "java(fr.dawan.jeankasskouille.tools.JwtUtils.generateToken(security))")
    LoginResponseDto toLoginResponse(UserSecurity security);

    User fromRegister(RegisterDto register);

    LoginResponseDto.UserDto toUserDto(User user);
}
