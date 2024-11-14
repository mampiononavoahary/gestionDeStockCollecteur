package com.spring.gestiondestock.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@ToString
public class Users implements Serializable {
    private int id_user;
    private String nom;
    private String prenom;
    private String address;
    private String contact;
    private String image;
}
