package com.group8.appuser;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
abstract public class AppUser implements UserDetails {


    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String password;
    private Boolean locked=false,enabled=false;
    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=
                new SimpleGrantedAuthority(getClass().getSimpleName());
        return Collections.singletonList(authority);
    }
    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
