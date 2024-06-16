package fr.dawan.jeankasskouille.business.component.user;

import fr.dawan.jeankasskouille.business.component.user.dtos.UserDto;
import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends GenericMapper<User, UserDto> {
}
