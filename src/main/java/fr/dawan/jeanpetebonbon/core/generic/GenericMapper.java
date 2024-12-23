package fr.dawan.jeanpetebonbon.core.generic;

public interface GenericMapper<E,D> {
    D toDto(E entity);
    E toEntity(D dto);
}
