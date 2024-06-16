package fr.dawan.jeankasskouille.generic.filter;

import fr.dawan.jeankasskouille.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterService<D, F> extends GenericService<D> {
    Page<D> findFiltered(F filter, Pageable pageable);
}
