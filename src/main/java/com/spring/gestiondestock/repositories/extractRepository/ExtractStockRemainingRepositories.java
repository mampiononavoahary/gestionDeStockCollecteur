package com.spring.gestiondestock.repositories.extractRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.LigneStock;
import com.spring.gestiondestock.model.extractModel.ExtractStockRemaining;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtractStockRemainingRepositories {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
     Connect connect = new Connect();
     connection = connect.CreateConnection();
    }
    private ExtractStockRemaining extractStockRemaining(ResultSet resultSet) throws SQLException {
        ExtractStockRemaining extractStockRemaining = new ExtractStockRemaining();
        extractStockRemaining.setLieu_stock(resultSet.getString("lieu_stock"));

        String stockJson = resultSet.getString("ligne_stock");
        if (stockJson != null && !stockJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<LigneStock> ligneStocks = objectMapper.readValue(
                        stockJson,
                        new TypeReference<List<LigneStock>>() {}
                );
                extractStockRemaining.setLigne_stock(ligneStocks);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error lors du parsing to json :"+e.getMessage(),e);
            }
        }
        return extractStockRemaining;
    }
    public List<ExtractStockRemaining> getAllStockRemaining() throws SQLException, ClassNotFoundException {
        String sql = "select s.lieu_stock,JSON_AGG(JSON_BUILD_OBJECT('quantite',s.quantite_stock,'unite',s.unite,'produit',dp.nom_detail))AS ligne_stock from stock s inner join " +
                "produit_avec_detail pdt on s.id_produit_avec_detail = pdt.id_produit_avec_detail inner join detail_produit dp on " +
                "dp.id_detail_produit = pdt.id_detail_produit GROUP BY s.lieu_stock;\n";
        getConnection();
        try (PreparedStatement stm = connection.prepareStatement(sql)){
            ResultSet resultSet = stm.executeQuery();
            List<ExtractStockRemaining> extractStockRemainings = new java.util.ArrayList<>();
            while (resultSet.next()) {
                extractStockRemainings.add(extractStockRemaining(resultSet));
            }
            return extractStockRemainings;

        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
