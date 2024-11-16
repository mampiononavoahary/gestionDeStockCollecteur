package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.Users;
import com.spring.gestiondestock.model.enums.RoleUser;
import com.spring.gestiondestock.repositories.InterfaceUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoriesImpl implements InterfaceUsers<Users> {
    private static Connection connection;

    private static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private Users extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_user");
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        String address = resultSet.getString("address");
        String contact = resultSet.getString("contact");
        String image = resultSet.getString("image");
        RoleUser role = RoleUser.valueOf(resultSet.getString("role"));
        String nom_d_utilisateur = resultSet.getString("nom_d_utilisateur");
        String mot_de_passe = resultSet.getString("mot_de_passe");

        return new Users(id,nom,prenom,address,contact,image,role,nom_d_utilisateur,mot_de_passe);
    }
    @Override
    public List<Users> getAll() throws SQLException, ClassNotFoundException {
        List<Users> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
            for (Users user : users) {
                System.out.println(user);
            }
        }
        return users;
    }

    @Override
    public Users getById(int id) {
        return null;
    }

    @Override
    public Users Update(Users toUpdate) {
        return null;
    }

    @Override
    public Users save(Users toSave) {
        return null;
    }

    @Override
    public Users delete(Users toDelete) {
        return null;
    }

    @Override
    public Users findByName(String name) {
        return null;
    }
}
