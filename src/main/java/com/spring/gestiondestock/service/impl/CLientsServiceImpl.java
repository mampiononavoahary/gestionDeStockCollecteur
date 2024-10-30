package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.ClientsRequest;
import com.spring.gestiondestock.dtos.responses.ClientsResponse;
import com.spring.gestiondestock.mappers.ClientsMapper;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.repositories.impl.ClientsRepositoriesImpl;
import com.spring.gestiondestock.service.ServiceClients;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CLientsServiceImpl implements ServiceClients{
    private static ClientsRepositoriesImpl clientsRepositories;
    private final ClientsMapper clientsMapper;
    public CLientsServiceImpl(ClientsRepositoriesImpl clientsRepositories, ClientsMapper clientsMapper) {
        this.clientsRepositories = clientsRepositories;
        this.clientsMapper = clientsMapper;
    }
    @Override
    public ClientsResponse createClients(ClientsRequest clientsRequest) throws SQLException, ClassNotFoundException {
        var client = clientsMapper.toEntity(clientsRequest);
        var save = clientsRepositories.save(client);
        return clientsMapper.toResponse(save);
    }

    @Override
    public ClientsResponse updateClients(Clients clients, int id_clients) throws SQLException, ClassNotFoundException {
        Clients client = clientsRepositories.findById(id_clients);
        if (client == null) {
            System.out.println("Client not found");
            throw new IllegalArgumentException("Client with ID " + id_clients + " not found");
        }
        var clientUpdate = clientsRepositories.update(clients);
        Clients client2 = clientsRepositories.findById(id_clients);
        return clientsMapper.toResponse(client2);
    }

    @Override
    public ClientsResponse deleteClients(int id_clients) throws SQLException, ClassNotFoundException {
        var client = clientsRepositories.findById(id_clients);
        if (client == null) {
            System.out.println("Client not found");
            throw new IllegalArgumentException("Client with ID " + id_clients + " not found");
        }
        clientsRepositories.delete(id_clients);
        return null;
    }


    @Override
    public List<ClientsResponse> getAllClients() throws SQLException, ClassNotFoundException {
        var clients = clientsRepositories.findAll();
        List<ClientsResponse> clientsResponses = new ArrayList<>();
        for (Clients client : clients) {
            clientsResponses.add( clientsMapper.toResponse(client));
        }
        return clientsResponses;
    }

    @Override
    public ClientsResponse getAllClientsByClientId(int id_clients) throws SQLException, ClassNotFoundException {
        var client = clientsRepositories.findById(id_clients);

        return clientsMapper.toResponse(client);
    }

    @Override
    public List<ClientsResponse> saveAllClients(List<ClientsRequest> clientsRequests) throws SQLException, ClassNotFoundException {
        var allClients = new ArrayList<ClientsResponse>();

        for (ClientsRequest clientRequest : clientsRequests) {
            // Mapper la requête vers une entité
            var newClient = clientsMapper.toEntity(clientRequest);

            // Sauvegarder l'entité et vérifier qu'elle n'est pas nulle
            var savedClient = clientsRepositories.save(newClient);
            if (savedClient == null) {
                throw new IllegalStateException("Failed to save client: " + clientRequest.getNom());
            }

            // Mapper l'entité sauvegardée en réponse
            var response = clientsMapper.toResponse(savedClient);
            allClients.add(response);
        }

        return allClients;
    }
}
