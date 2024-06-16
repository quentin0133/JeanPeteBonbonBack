package fr.dawan.jeankasskouille.exception;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String label, long id) {
        super("%s not found with id %d".formatted(label, id));
    }
}
