package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractClients;
import com.spring.gestiondestock.model.extractModel.ExtractLatestTransaction;
import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractClientRepositories {
  private static Connection connection;
  public void getConnection()throws SQLException, ClassNotFoundException {
    Connect connect = new Connect();
    connection = connect.CreateConnection();
  }
  private ExtractClients extractClients(ResultSet resultSet) throws SQLException {
    int id_client = resultSet.getInt("id_client");
    String nom = resultSet.getString("nom_client");
    String prenom = resultSet.getString("prenom_client");
    int total_transaction = resultSet.getInt("total_transaction");
    Double total_paye = resultSet.getDouble("total_paye");
    Double total_en_attente = resultSet.getDouble("total_en_attente");

    return new ExtractClients(id_client,nom,prenom,total_transaction,total_paye,total_en_attente);

  }
  public List<ExtractClients> getExtractClients(String query, int currentPage) throws SQLException, ClassNotFoundException {
    List<ExtractClients> extractClients = new ArrayList<>();
    String sql = """
        SELECT
            c.id_clients AS id_client,
            c.nom AS nom_client,
            c.prenom AS prenom_client,
            COUNT(t.id_transaction) AS total_transaction,
            SUM(CASE WHEN t.status = 'PAYE' THEN t.quantite ELSE 0 END) AS total_paye,
            SUM(CASE WHEN t.status = 'EN_ATTENTE' THEN t.quantite ELSE 0 END) AS total_en_attente
        FROM
            clients c
        JOIN
            detail_transaction dt ON c.id_clients = dt.id_client
        JOIN
            transactions t ON dt.id_detail_transaction = t.id_detail_transaction
        WHERE
            c.nom ILIKE ? OR
            c.prenom ILIKE ?
        GROUP BY
            c.id_clients, c.nom, c.prenom
        ORDER BY
            c.nom, c.prenom
        LIMIT 6 OFFSET ?;
        """;
    getConnection();
    try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
      preparedStatement.setString(1, "%" + query + "%");
      preparedStatement.setString(2, "%" + query + "%");
      preparedStatement.setInt(3, (currentPage - 1) * 6);
      ResultSet resultSet = preparedStatement.executeQuery();
      while(resultSet.next()){
        extractClients.add(extractClients(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return extractClients;
  }
}
