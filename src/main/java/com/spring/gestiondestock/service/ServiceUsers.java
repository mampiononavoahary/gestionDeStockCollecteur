package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.responses.UsersResponse;

import java.sql.SQLException;
import java.util.List;

public interface ServiceUsers {
    List<UsersResponse> findAllUsers() throws SQLException, ClassNotFoundException;
}
