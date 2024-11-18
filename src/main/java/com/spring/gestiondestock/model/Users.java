package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@ToString
@Entity // Indique que c'est une entité JPA
@Table(name = "users") // Spécifie la table correspondante
public class Users implements UserDetails, Serializable {

    @Id // Indique le champ ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    private int id_user;

    private String nom;
    private String prenom;
    private String address;
    private String contact;
    private String image;

    @Enumerated(EnumType.ORDINAL) // Enregistre l'enum comme un entier dans la base
    private RoleUser role;

    @Column(unique = true, nullable = false)
    private String username;


    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
