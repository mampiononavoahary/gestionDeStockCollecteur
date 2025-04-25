package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.StockRequest;
import com.spring.gestiondestock.dtos.responses.StockResponse;
import com.spring.gestiondestock.mappers.StockMapper;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.model.extractModel.ExtractProduitWitDetail;
import com.spring.gestiondestock.repositories.impl.DetailProduitRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.StockRepositoriesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class StockServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    private final StockRepositoriesImpl stockRepositories;
    private final DetailProduitRepositoriesImpl detailProduitRepositories;
    private final ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepositoriesImpl stockRepositories, ProduitRepositoriesImpl produitRepositories, DetailProduitRepositoriesImpl detailProduitRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories, StockMapper stockMapper) {
        this.stockRepositories = stockRepositories;
        this.detailProduitRepositories = detailProduitRepositories;
        this.produitAvecDetailRepositories = produitAvecDetailRepositories;
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
    public String produitPremierToFinie(String lieu, int id_produit_avec_detail_premier, int id_produit_avec_deatil_finie, Double quantite) throws SQLException, ClassNotFoundException {
        try {

        Stock produit_premier = stockRepositories.findByLieuAndProduit(lieu,id_produit_avec_detail_premier);
        Stock produit_finie = stockRepositories.findByLieuAndProduit(lieu,id_produit_avec_deatil_finie);
        DetailProduit produit = detailProduitRepositories.getById(id_produit_avec_detail_premier);
        ProduitAvecDetail produitAvecDetail = produitAvecDetailRepositories.findProduitAvecDetailById(id_produit_avec_deatil_finie);
        Stock apombo_malemy_in_stock = stockRepositories.findByLieuAndNameProduct(lieu,"Apombo malemy");
        Stock apombo_akofa_in_stock = stockRepositories.findByLieuAndNameProduct(lieu,"Apombo akofa");
        ProduitAvecDetail apombo_malemy = produitAvecDetailRepositories.findByName("Apombo malemy");
        ProduitAvecDetail apombo_akofa = produitAvecDetailRepositories.findByName("Apombo akofa");


            if (Objects.equals(produit.getNom_detail(),"Vary") || Objects.equals(produit.getNom_detail(),"Vary tsipala")){
            Double quantite_finie = quantite * 0.67;
            Double update_apombo_malemy = quantite * 0.09;
            Double update_apombo_akofa = quantite * 0.24;
            if (produit_finie == null){
                stocks(lieu, produitAvecDetail, quantite_finie);
                produit_premier.setQuantite_stock(produit_premier.getQuantite_stock()-quantite);
                stockRepositories.updateStock(produit_premier, produit_premier.getId_stock());
            } else {
                produit_finie.setQuantite_stock(produit_finie.getQuantite_stock()+quantite_finie);
                produit_premier.setQuantite_stock(produit_premier.getQuantite_stock()-quantite);
                stockRepositories.updateStock(produit_premier, produit_premier.getId_stock());
                stockRepositories.updateStock(produit_finie,produit_finie.getId_stock());
            }
            if (apombo_malemy_in_stock == null) {
                stocks(lieu, apombo_malemy, update_apombo_malemy);
            }else {
                apombo_malemy_in_stock.setQuantite_stock(apombo_malemy_in_stock.getQuantite_stock() + update_apombo_malemy);
                stockRepositories.updateStock(apombo_malemy_in_stock, apombo_malemy_in_stock.getId_stock());
            }
            if (apombo_akofa_in_stock == null) {
                stocks(lieu, apombo_akofa, update_apombo_akofa);
            }else {
                apombo_akofa_in_stock.setQuantite_stock(apombo_akofa_in_stock.getQuantite_stock() + update_apombo_akofa);
                stockRepositories.updateStock(apombo_akofa_in_stock, apombo_akofa_in_stock.getId_stock());
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Transformation de produit premier en produit finie succes";
    }

    private void stocks(String lieu, ProduitAvecDetail apombo_akofa, Double update_apombo_akofa) throws SQLException, ClassNotFoundException {
        Stock stock = new Stock();
        stock.setProduitAvecDetail(apombo_akofa);
        stock.setQuantite_stock(update_apombo_akofa);
        stock.setUnite(Unite.KG);
        stock.setLieu_de_transaction(LieuDeTransaction.valueOf(lieu));
        var newSrock = stockRepositories.saveStock(stock);
        System.out.println(newSrock.getProduitAvecDetail());
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
    public Double updateQuantiteStock(Double quantite,int id) throws SQLException, ClassNotFoundException {
        return stockRepositories.updateQuantiteStock(quantite,id);
    }
}
