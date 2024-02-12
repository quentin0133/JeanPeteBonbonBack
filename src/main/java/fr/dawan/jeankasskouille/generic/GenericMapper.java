package fr.dawan.jeankasskouille.generic;

import org.mapstruct.Mapper;

@Mapper
public interface GenericMapper<E,D> {
    D toDto(E entity);
    E toEntity(D dto);
}
