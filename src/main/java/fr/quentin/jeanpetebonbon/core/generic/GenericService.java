package fr.quentin.jeanpetebonbon.core.generic;

import fr.quentin.jeanpetebonbon.core.exception.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<E, D> {
  List<D> findAll();

  Page<D> findAll(Pageable pageable);

  D findById(long id) throws ResourceNotFound;

  D save(D dto);

  D update(long id, D dto) throws ResourceNotFound;

  D saveEntity(E entity);

  D updateEntity(long id, E entity) throws ResourceNotFound;

  void deleteById(long id);
}
