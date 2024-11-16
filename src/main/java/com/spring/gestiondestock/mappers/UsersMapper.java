package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.UsersRequest;
import com.spring.gestiondestock.dtos.responses.UsersResponse;
import com.spring.gestiondestock.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsersMapper {
    public Users toUsers(UsersRequest usersRequest) {
        return Users.builder()
                .nom(usersRequest.getNom())
                .prenom(usersRequest.getPrenom())
                .address(usersRequest.getAddress())
                .contact(usersRequest.getContact())
                .image(usersRequest.getImage())
                .role(usersRequest.getRole())
                .nom_d_utilisateur(usersRequest.getNom_d_utilisateur())
                .mot_de_passe(usersRequest.getMot_de_passe())
                .build();
    }
    public UsersResponse toUsersResponse(Users users) {
        return new UsersResponse(
                users.getId_user(),
                users.getNom(),
                users.getPrenom(),
                users.getAddress(),
                users.getContact(),
                users.getImage(),
                users.getRole(),
                users.getNom_d_utilisateur(),
                users.getMot_de_passe()
        );
    }
}
