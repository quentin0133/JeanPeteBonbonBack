package fr.dawan.jeanpetebonbon.core.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    @ExceptionHandler({SecurityException.class, ServletException.class, JwtException.class})
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
        String errorMessage = "%s (error catched in GlobalExceptionHandler)".formatted(ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errors", status.value());
        body.put("status", errorMessage);

        System.err.println(errorMessage);

        return new ResponseEntity<>(body, status);
    }
}
