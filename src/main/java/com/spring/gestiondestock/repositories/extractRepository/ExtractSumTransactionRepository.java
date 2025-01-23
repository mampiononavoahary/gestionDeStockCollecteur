package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractEnterAndExitCount;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ExtractSumTransactionRepository {
    private static Connection connection;

    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    private ExtractEnterAndExitCount extractEnterAndExitCount(ResultSet resultSet) throws SQLException {
        Double sum_entre = resultSet.getDouble("sum_entre");
        Double sum_sortie = resultSet.getDouble("sum_sortie");
        int total_entre = resultSet.getInt("total_entre");
        int total_sortie = resultSet.getInt("total_sortie");

        return new ExtractEnterAndExitCount(sum_entre,sum_sortie,total_entre,total_sortie);
    }

public ExtractEnterAndExitCount SumTransactionEnterAndExit(String lieu, Date date) throws SQLException, ClassNotFoundException {
    StringBuilder sql = new StringBuilder("SELECT " +
        " SUM(CASE WHEN dt.type_de_transaction = 'SORTIE' THEN " +
        "  t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END " +
        " END) AS sum_sortie, " +
        " SUM(CASE WHEN dt.type_de_transaction = 'ENTRE' THEN " +
        "  t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END " +
        " END) AS sum_entre, " +
        " SUM(CASE WHEN dt.type_de_transaction = 'SORTIE' THEN 1 ELSE 0 END) AS total_sortie, " +
        " SUM(CASE WHEN dt.type_de_transaction = 'ENTRE' THEN 1 ELSE 0 END) AS total_entre " +
        " FROM transactions t " +
        " JOIN detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction " +
        " JOIN produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail " +
        " JOIN detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit " +
        " WHERE t.status = 'PAYE' " +
        " AND dt.type_de_transaction IN ('SORTIE', 'ENTRE')");

    // Ajout du filtre sur lieu si nécessaire
    if (lieu != null && !lieu.isEmpty()) {
        sql.append(" AND COALESCE(dt.lieu_de_transaction::TEXT, '') ILIKE ?");
    }

    // Ajout du filtre sur la date si nécessaire
    if (date != null) {
        sql.append(" AND DATE(dt.date_de_transaction) = ?");
    }

    // Connexion à la base de données et préparation de la requête
    getConnection();
    ExtractEnterAndExitCount enterAndExitCount = null;
    try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        int paramIndex = 1;

        // Si lieu est fourni, on ajoute le paramètre pour le lieu
        if (lieu != null && !lieu.isEmpty()) {
            ps.setString(paramIndex++, "%" + lieu + "%");
        }

        // Si date est fournie, on ajoute le paramètre pour la date
        if (date != null) {
            ps.setDate(paramIndex++, date);
        }

        // Exécution de la requête
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                enterAndExitCount = extractEnterAndExitCount(rs);
                System.out.println(enterAndExitCount); // Log pour le débogage
            }
        }
    } finally {
        if (connection != null) {
            connection.close();
        }
    }

    // Si aucun résultat trouvé, on retourne des valeurs par défaut
    return enterAndExitCount != null ? enterAndExitCount : new ExtractEnterAndExitCount(0.0, 0.0, 0, 0);
}
}

