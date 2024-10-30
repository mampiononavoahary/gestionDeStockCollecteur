package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.requests.ClientsRequest;
import com.spring.gestiondestock.dtos.responses.ClientsResponse;
import com.spring.gestiondestock.model.Clients;

import java.sql.SQLException;
import java.util.List;

public interface ServiceClients {
    ClientsResponse createClients(ClientsRequest clientsRequest) throws SQLException, ClassNotFoundException;
    ClientsResponse updateClients(Clients clients, int id_clients) throws SQLException, ClassNotFoundException;
    ClientsResponse deleteClients(int id_clients) throws SQLException, ClassNotFoundException;
    List<ClientsResponse> getAllClients() throws SQLException, ClassNotFoundException;
    ClientsResponse getAllClientsByClientId(int id_clients) throws SQLException, ClassNotFoundException;
    List<ClientsResponse> saveAllClients(List<ClientsRequest> clientsRequests) throws SQLException, ClassNotFoundException;
}
