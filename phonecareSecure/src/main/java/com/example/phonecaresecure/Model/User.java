package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @JsonIgnore
    @Transient
    private final String rol1="CUSTOMER";
    @JsonIgnore
    @Transient
    private final String rol2="PROVIDER";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "user name should not be empty")
    @Column(name = "username",unique = true)
    private String username;
    @NotEmpty(message = "password should not be empty")
//    @Pattern(regexp = "^(?=.?[A-Z])(?=.?[a-z])(?=.*?[0-9]).{8,}$", message = "Password should be Minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    private String password;
    @NotEmpty(message = "role should not be empty")
    @Pattern(regexp = "^("+rol1+"|"+rol2+")",message = "Role should only be ether 1.CUSTOMER 2.PROVIDER")
    private String role;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Provider provider;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Customer customer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
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
}
