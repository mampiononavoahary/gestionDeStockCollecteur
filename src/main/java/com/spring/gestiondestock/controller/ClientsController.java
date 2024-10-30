package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.ClientsRequest;
import com.spring.gestiondestock.dtos.responses.ClientsResponse;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.repositories.impl.ClientsRepositoriesImpl;
import com.spring.gestiondestock.service.impl.CLientsServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ClientsController {
    private static CLientsServiceImpl cLientsService;

    public ClientsController(CLientsServiceImpl cLientsService) {
        ClientsController.cLientsService = cLientsService;
    }
    @GetMapping("/clients")
    public List<ClientsResponse> getAllClients() throws SQLException, ClassNotFoundException {
        return cLientsService.getAllClients();
    }
    @PostMapping("/client/post")
    public ClientsResponse createClient(@RequestBody ClientsRequest client) throws SQLException, ClassNotFoundException {
        return cLientsService.createClients(client);
    }
    @PostMapping("/clients/posts")
    public List<ClientsResponse> addManyClients(@RequestBody List<ClientsRequest> clients) throws SQLException, ClassNotFoundException {
        return cLientsService.saveAllClients(clients);
    }
    @GetMapping("/client/{id_clients}")
    public ClientsResponse getClientById(@PathVariable String id_clients) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(id_clients);
        return cLientsService.getAllClientsByClientId(id);
    }
    @PutMapping("/client")
    public ClientsResponse updateClient(@RequestBody Clients client) throws SQLException, ClassNotFoundException {
        return cLientsService.updateClients(client,client.getId_clients());
    }
    @DeleteMapping("/client/{id_clients}")
    public ClientsResponse deleteClient(@PathVariable String id_clients) throws SQLException, ClassNotFoundException {
        return cLientsService.deleteClients(Integer.parseInt(id_clients));
    }
}
