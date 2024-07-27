package fr.dawan.jeanpetebonbon.core.generic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class GenericController<
        D,
        S extends GenericService<D>
        > {
    protected final S service;

    @GetMapping(params = {"page", "size"})
    public Page<D> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping
    public List<D> findAllList() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<D> findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public D saveOrUpdate(@RequestBody D dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public D update(@PathVariable long id, @RequestBody D dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
