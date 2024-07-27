package fr.dawan.jeanpetebonbon.core.generic;

import fr.dawan.jeanpetebonbon.core.exception.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericService<D> {
    List<D> findAll();
    Page<D> findAll(Pageable pageable);
    Optional<D> findById(long id);
    D save(D entity);
    D update(long id, D entity) throws ResourceNotFound;
    void deleteById(long id);
}
