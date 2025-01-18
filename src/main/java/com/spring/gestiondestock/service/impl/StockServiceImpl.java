package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.StockRequest;
import com.spring.gestiondestock.dtos.responses.StockResponse;
import com.spring.gestiondestock.mappers.StockMapper;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.repositories.impl.StockRepositoriesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class StockServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    private final StockRepositoriesImpl stockRepositories;
    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepositoriesImpl stockRepositories, StockMapper stockMapper) {
        this.stockRepositories = stockRepositories;
        this.stockMapper = stockMapper;
    }
    public StockResponse findById(int id) throws SQLException, ClassNotFoundException {
        var getById = stockRepositories.findById(id);
        if (getById == null){
            log.error("stock with id_detail_produit: {}",id+" not found");
        }
        assert getById != null;
        return stockMapper.toResponse(getById);
    }
    public StockResponse findByLieuAndIdProduit(String lieu, int id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        var getByLieuAndIdProduit = stockRepositories.findByLieuAndProduit(lieu,id_produit_avec_detail);
        if (getByLieuAndIdProduit == null){
            log.error("stock with lieu_de_transaction: {} and id_produit_avec_detail: {} not found", lieu, id_produit_avec_detail);
        }
        assert getByLieuAndIdProduit != null;
        return stockMapper.toResponse(getByLieuAndIdProduit);
    }
    public StockResponse save(StockRequest stockRequest) throws SQLException, ClassNotFoundException {
        var toEntity = stockMapper.toEntity(stockRequest);
        var save = stockRepositories.saveStock(toEntity);
        return stockMapper.toResponse(save);
    }
    public Stock update(Stock stock, int id) throws SQLException, ClassNotFoundException {
        var findById = stockRepositories.findById(id);
        if (findById == null){
            log.error("stock with id_produit_avec_detail: {}", id+" not found");
        }
        return stockRepositories.updateStock(stock,id);
    }
}
