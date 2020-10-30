package no.hvl.dat250.gruppe9.feedapp.restapi.config.security;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DevicePrincipals implements UserDetails {

    private String id;
    private String name;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public DevicePrincipals(String id, String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public static DevicePrincipals create(IoT device) {
        List<GrantedAuthority> authorities = device.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new DevicePrincipals(
                device.getId(),
                device.getName(),
                device.getPassword(),
                authorities
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        DevicePrincipals that = (DevicePrincipals) o;
        return Objects.equals(id, that.id);
    }
}

