package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordDTO {
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 60)
    private String password;

    PasswordDTO(
            @NotBlank @Size(max = 60) @Email String email,
            @NotBlank @Size(min = 6, max = 60) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
