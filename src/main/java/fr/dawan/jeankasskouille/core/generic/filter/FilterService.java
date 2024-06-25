package fr.dawan.jeankasskouille.core.generic.filter;

import fr.dawan.jeankasskouille.core.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterService<D, F> extends GenericService<D> {
    Page<D> findFiltered(F filter, Pageable pageable);
}
