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

import javax.sql.DataSource;

@Repository
public class ExtractClientRepositories {
  private static Connection connection;
  
  private final DataSource dataSource;
  public ExtractClientRepositories(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  private ExtractClients extractClients(ResultSet resultSet) throws SQLException {
    int id_client = resultSet.getInt("id_client");
    String nom = resultSet.getString("nom_client");
    String prenom = resultSet.getString("prenom_client");
    int total_transaction = resultSet.getInt("total_transaction");
    int total_vente = resultSet.getInt("total_vente");
    Double total_vente_paye = resultSet.getDouble("total_vente_paye");
    Double total_vente_en_attente = resultSet.getDouble("total_vente_en_attente");
    int total_achat = resultSet.getInt("total_achat");
   Double total_achat_paye = resultSet.getDouble("total_achat_paye");
    Double total_achat_en_attente = resultSet.getDouble("total_achat_en_attente");

    return new ExtractClients(id_client,nom,prenom,total_transaction,total_vente,total_vente_paye,total_vente_en_attente,total_achat,total_achat_paye,total_achat_en_attente);

  }
  public List<ExtractClients> getExtractClients(String query, int currentPage) throws SQLException, ClassNotFoundException {
    List<ExtractClients> extractClients = new ArrayList<>();
    String sql = """
       
            SELECT
                            c.id_clients AS id_client,
                            c.nom AS nom_client,
                            c.prenom AS prenom_client,
                            COUNT(t.id_transaction) AS total_transaction,
                            COUNT(CASE WHEN dt.type_de_transaction = 'SORTIE' THEN 1 ELSE NULL END) AS total_vente,
                            SUM(
                                CASE\s
                                    WHEN t.status = 'PAYE' AND dt.type_de_transaction = 'SORTIE' THEN\s
                                        CASE\s
                                            WHEN t.unite = 'T' THEN t.quantite * 1000\s
                                            ELSE t.quantite\s
                                        END
                                    ELSE 0\s
                                END
                            ) AS total_vente_paye,
                            SUM(
                                CASE\s
                                    WHEN t.status = 'EN_ATTENTE' AND dt.type_de_transaction = 'SORTIE' THEN\s
                                        CASE\s
                                            WHEN t.unite = 'T' THEN t.quantite * 1000\s
                                            ELSE t.quantite\s
                                        END
                                    ELSE 0\s
                                END
                            ) AS total_vente_en_attente,
                            COUNT(CASE WHEN dt.type_de_transaction = 'ENTRE' THEN 1 ELSE NULL END) AS total_achat,
                            SUM(
                                CASE\s
                                    WHEN t.status = 'PAYE' AND dt.type_de_transaction = 'ENTRE' THEN\s
                                        CASE\s
                                            WHEN t.unite = 'T' THEN t.quantite * 1000\s
                                            ELSE t.quantite\s
                                        END
                                    ELSE 0\s
                                END
                            ) AS total_achat_paye,
                            SUM(
                                CASE\s
                                    WHEN t.status = 'EN_ATTENTE' AND dt.type_de_transaction = 'ENTRE' THEN\s
                                        CASE\s
                                            WHEN t.unite = 'T' THEN t.quantite * 1000\s
                                            ELSE t.quantite\s
                                        END
                                    ELSE 0\s
                                END
                            ) AS total_achat_en_attente
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
    try(
    Connection connection = dataSource.getConnection();     
    PreparedStatement preparedStatement = connection.prepareStatement(sql)){
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
