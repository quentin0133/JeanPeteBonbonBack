package fr.dawan.jeankasskouille.business.component.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
