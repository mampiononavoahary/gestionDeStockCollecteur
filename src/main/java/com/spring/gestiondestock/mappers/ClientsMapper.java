package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.ClientsRequest;
import com.spring.gestiondestock.dtos.responses.ClientsResponse;
import com.spring.gestiondestock.model.Clients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClientsMapper {
    public Clients toEntity(ClientsRequest clientsRequest){
        return Clients.builder()
                .nom(clientsRequest.getNom())
                .prenom(clientsRequest.getPrenom())
                .adresse(clientsRequest.getAdresse())
                .telephone(clientsRequest.getTelephone())
                .build();
    }

    public ClientsResponse toResponse(Clients clients){
        return new ClientsResponse(
                clients.getId_clients(),
                clients.getNom(),
                clients.getPrenom(),
                clients.getAdresse(),
                clients.getTelephone(),
                clients.getDetailTransactions()
        );
    }
}
