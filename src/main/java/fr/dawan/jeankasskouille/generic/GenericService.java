package fr.dawan.jeankasskouille.generic;

import fr.dawan.jeankasskouille.exception.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenericService<D> {
    Page<D> findAll(Pageable pageable); // Tous les enregistrements avec pagination
    Optional<D> findById(long id);
    D save(D entity);
    D update(long id, D entity) throws ResourceNotFound;
    void deleteById(long id);
}
