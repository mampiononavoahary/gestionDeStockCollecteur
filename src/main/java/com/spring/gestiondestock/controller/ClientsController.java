package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.ClientsRequest;
import com.spring.gestiondestock.dtos.responses.ClientsResponse;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.repositories.impl.ClientsRepositoriesImpl;
import com.spring.gestiondestock.service.impl.CLientsServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/clients") // Centraliser le chemin des endpoints
@CrossOrigin(origins = "http://localhost:63342")
public class ClientsController {

    private final CLientsServiceImpl clientsService;

    public ClientsController(CLientsServiceImpl clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public List<ClientsResponse> getAllClients() throws SQLException, ClassNotFoundException {
        return clientsService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientsResponse getClientById(@PathVariable("id") int id) throws SQLException, ClassNotFoundException {
        return clientsService.getAllClientsByClientId(id);
    }

    @PostMapping
    public ClientsResponse createClient(@RequestBody @Validated ClientsRequest client) throws SQLException, ClassNotFoundException {
        return clientsService.createClients(client);
    }

    @PostMapping("/batch")
    public List<ClientsResponse> addManyClients(@RequestBody @Validated List<ClientsRequest> clients) throws SQLException, ClassNotFoundException {
        return clientsService.saveAllClients(clients);
    }

    @PutMapping("/{id}")
    public ClientsResponse updateClient(@PathVariable("id") int id, @RequestBody @Validated Clients client) throws SQLException, ClassNotFoundException {
        return clientsService.updateClients(client, id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable("id") int id) throws SQLException, ClassNotFoundException {
        clientsService.deleteClients(id);
    }
}
