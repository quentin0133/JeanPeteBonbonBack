package fr.dawan.jeankasskouille.exception;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler({DateTimeBeforeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleValidationExceptions(
            RuntimeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({SecurityException.class, ServletException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleAuthenticationExceptions(
            RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoServerFound.class, NoTextChannelException.class,
            DisabledException.class, ResourceNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundExceptions(
            RuntimeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
