package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.responses.UsersResponse;
import com.spring.gestiondestock.service.impl.UsersServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        UsersController.usersService = usersService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsersResponse> findAllUsers() throws SQLException, ClassNotFoundException {
        return usersService.findAllUsers();
    }
}
