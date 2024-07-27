package fr.dawan.jeanpetebonbon.core.generic.filter;

import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class FilterController<D, F, S extends FilterService<D, F>> extends GenericController<D, S> {
    protected FilterController(S service) {
        super(service);
    }

    @GetMapping(value = "/filter", params = {"page", "size"})
    public Page<D> findFiltered(@RequestBody F filter, Pageable pageable) {
        return service.findFiltered(filter, pageable);
    }

    @GetMapping("/filter")
    public List<D> findFiltered(@RequestBody F filter) {
        return service.findFiltered(filter, Pageable.unpaged()).getContent();
    }
}
