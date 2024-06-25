package fr.dawan.jeankasskouille.auth.dtos;

import java.io.Serializable;

public record LoginDto(String email, String password) implements Serializable {
}
