package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.mappers.ClientsMapper;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.repositories.InterfaceClients;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsRepositoriesImpl implements InterfaceClients<Clients> {

    private static Connection connection;
    private final ClientsMapper clientsMapper;

    public ClientsRepositoriesImpl(ClientsMapper clientsMapper) {
        this.clientsMapper = clientsMapper;
    }

    private static void getConnection() throws ClassNotFoundException, SQLException {
        Connect connect=new Connect();
        connection = connect.CreateConnection();
    }
    private Clients extractClients(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_clients");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String adresse = rs.getString("adresse");
        String telephone = rs.getString("telephone");
        List<DetailTransaction> detailTransactions = new ArrayList<>();

        return new Clients(id, nom, prenom, adresse, telephone,detailTransactions);
    }
    @Override
    public List<Clients> findAll() throws SQLException, ClassNotFoundException {
        List<Clients> clients=new ArrayList<>();
        String sql = "select * from clients";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                clients.add(extractClients(resultSet));
            }
            for (Clients client: clients) {
                System.out.println(client);
            }
        }
        return clients;
    }

    @Override
    public List<Clients> saveAll(List<Clients> toSave) throws SQLException, ClassNotFoundException {
        List<Clients> clients = new ArrayList<>();
        String sql = "INSERT INTO clients (nom, prenom, adresse, telephone) VALUES (?,?,?,?);";
        getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Clients client : toSave) {
                ps.setString(1, client.getNom());
                ps.setString(2, client.getPrenom());
                ps.setString(3, client.getAdresse());
                ps.setString(4, client.getTelephone());

                int resultSet = ps.executeUpdate();
                if (resultSet > 0) {
                    clients.add(client);  // Ajoute à la liste des clients sauvegardés
                    System.out.println("Client saved: " + client.getNom());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public List<Clients> findByName(String name) throws SQLException, ClassNotFoundException {
        List<Clients> clients=new ArrayList<>();
        String sql = "select * from clients where nom = ?";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                clients.add(extractClients(resultSet));
            }
            for (Clients client: clients) {
                System.out.println(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Clients findById(int id_clietns) throws SQLException, ClassNotFoundException {
        String sql = "select * from clients where id_clients = ?";
        getConnection();
        Clients clients = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_clietns);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                clients = extractClients(resultSet);
                System.out.println(clients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Clients save(Clients toSave) throws SQLException, ClassNotFoundException {
        String sql = "insert into clients(nom,prenom,adresse,telephone) values (?,?,?,?);";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toSave.getNom());
            ps.setString(2, toSave.getPrenom());
            ps.setString(3, toSave.getAdresse());
            ps.setString(4, toSave.getTelephone());
            int resultSet = ps.executeUpdate();
            if (resultSet > 0) {
                System.out.println("Client saved");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Clients update(Clients toUpdate) throws SQLException, ClassNotFoundException {
        String sql = "update clients set nom=?,prenom=?,adresse=?,telephone=? where id_clients=?";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toUpdate.getNom());
            ps.setString(2, toUpdate.getPrenom());
            ps.setString(3, toUpdate.getAdresse());
            ps.setString(4, toUpdate.getTelephone());
            ps.setInt(5, toUpdate.getId_clients());
            int resultSet = ps.executeUpdate();
            if (resultSet > 0) {
                System.out.println("Client updated");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return toUpdate;
    }

    @Override
    public Clients delete(int id_clients) throws SQLException, ClassNotFoundException {
        String sql = "delete from clients where id_clients = ?;";
        Clients client = null;
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,id_clients);
            int resultSet = ps.executeUpdate();
            if (resultSet > 0) {
                System.out.println("Client deleted");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return client;
    }
}
