package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.responses.UsersResponse;
import com.spring.gestiondestock.mappers.UsersMapper;
import com.spring.gestiondestock.model.Users;
import com.spring.gestiondestock.repositories.impl.UsersRepositoriesImpl;
import com.spring.gestiondestock.service.ServiceUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements ServiceUsers {
    private static UsersRepositoriesImpl usersRepositories;
    private static UsersMapper usersMapper;

    public UsersServiceImpl(UsersRepositoriesImpl usersRepositories, UsersMapper usersMapper) {
        UsersServiceImpl.usersMapper = usersMapper;
        UsersServiceImpl.usersRepositories = usersRepositories;
    }
    @Override
    public List<UsersResponse> findAllUsers() throws SQLException, ClassNotFoundException {
        var Allusers = usersRepositories.getAll();
        List<UsersResponse> usersResponses = new ArrayList<>();
        for (Users user : Allusers) {
            usersResponses.add(usersMapper.toUsersResponse(user));
        }
        return usersResponses;
    }
}
