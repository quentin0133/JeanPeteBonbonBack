package fr.quentin.jeanpetebonbon.auth;

import fr.quentin.jeanpetebonbon.auth.dtos.LoginCommandDto;
import fr.quentin.jeanpetebonbon.auth.dtos.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * The interface Auth controller.
 */
@Tag(name = "Authentication", description = "API for user authentication")
public interface AuthController {

    /**
     * Authenticate response entity.
     *
     * @param login the login
     * @return the response entity
     */
    @Operation(
        summary = "Authenticate a user",
        description = "Authenticates a user with the provided credentials and returns a login token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    ResponseEntity<LoginDto> authenticate(
        @Parameter(description = "User login credentials", required = true) LoginCommandDto login
    );
}
