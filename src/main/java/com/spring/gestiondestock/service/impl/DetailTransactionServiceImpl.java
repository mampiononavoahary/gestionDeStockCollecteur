package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.DetailTransactionRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.dtos.responses.DetailTransactionResponse;
import com.spring.gestiondestock.mappers.DetailTransactionMapper;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.repositories.impl.DetailTransactionRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetailTransactionServiceImpl {
    private static DetailTransactionRepositoriesImpl detailTransactionRepositories;
    private static DetailTransactionMapper detailTransactionMapper;
    public DetailTransactionServiceImpl(DetailTransactionRepositoriesImpl detailTransactionRepositories, DetailTransactionMapper detailTransactionMapper) {
        DetailTransactionServiceImpl.detailTransactionRepositories = detailTransactionRepositories;
        DetailTransactionServiceImpl.detailTransactionMapper = detailTransactionMapper;
    }
    public DetailTransactionResponse addDetailTransaction(DetailTransactionRequest detailTransaction) throws SQLException, ClassNotFoundException {
        var toEntity = detailTransactionMapper.toEntity(detailTransaction);
        var save = detailTransactionRepositories.SaveDetailTransaction(toEntity);
        return detailTransactionMapper.toResponse(save);
    }
    public List<DetailTransactionResponse> getAllDetailTransaction() throws SQLException, ClassNotFoundException {
        List<DetailTransactionResponse> detailTransactionResponseList = new ArrayList<>();
        var getAll = detailTransactionRepositories.findAllDetailTransaction();
        for (DetailTransaction detailTransaction : getAll) {
            detailTransactionResponseList.add(detailTransactionMapper.toResponse(detailTransaction));
        }
        return detailTransactionResponseList;
    }
    public DetailTransaction getLasteDetailTransactionId() throws SQLException, ClassNotFoundException {
        return detailTransactionRepositories.lastDetailTransaction();
    }
}
