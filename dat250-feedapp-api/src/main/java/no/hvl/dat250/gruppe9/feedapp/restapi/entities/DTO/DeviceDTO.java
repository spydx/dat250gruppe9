package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

import javax.validation.constraints.NotBlank;

public class DeviceDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
