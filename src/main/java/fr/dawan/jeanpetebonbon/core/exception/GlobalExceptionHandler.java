package fr.dawan.jeanpetebonbon.core.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DateTimeBeforeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleValidationExceptions(
            RuntimeException ex) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JwtException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleAuthenticationExceptions(RuntimeException ex) {
        return getResponseEntity(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoServerFound.class, NoTextChannelException.class,
            DisabledException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceNotFound.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleUnprocessableEntityExceptions(RuntimeException ex) {
        return getResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleGenericExceptions(Exception ex) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponseEntity(Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", ex.getMessage());

        System.err.println(ex.getMessage());
        Arrays.stream(ex.getStackTrace()).forEach(System.err::println);

        return new ResponseEntity<>(body, status);
    }
}
