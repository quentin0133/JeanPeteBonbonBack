package fr.quentin.jeanpetebonbon.core.generic;

import fr.quentin.jeanpetebonbon.core.exception.ResourceNotFound;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class CrudServiceImpl<
        E, R extends JpaRepository<E, Long>, D, M extends GenericMapper<E, D>>
    implements GenericService<E, D> {

  protected final R repository;
  protected final M mapper;
  protected final Class<E> entityClazz;

  @Override
  public List<D> findAll() {
    return repository.findAll().stream().map(mapper::toDto).toList();
  }

  @Override
  public Page<D> findAll(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toDto);
  }

  @Override
  public D findById(long id) throws ResourceNotFound {
    return repository
        .findById(id)
        .map(mapper::toDto)
        .orElseThrow(() -> new ResourceNotFound(entityClazz.getName(), id));
  }

  @Override
  public D save(D dto) {
    return saveEntity(mapper.toEntity(dto));
  }

  @Override
  public D update(long id, D dto) throws ResourceNotFound {
    return saveEntity(mapper.toEntity(dto));
  }

  @Override
  public D saveEntity(E entity) {
    return mapper.toDto(repository.saveAndFlush(entity));
  }

  @Override
  public D updateEntity(long id, E entity) throws ResourceNotFound {
    if (!repository.existsById(id)) throw new ResourceNotFound(entityClazz.getName(), id);
    return mapper.toDto(repository.saveAndFlush(entity));
  }

  @Override
  public void deleteById(long id) {
    repository.deleteById(id);
  }
}
