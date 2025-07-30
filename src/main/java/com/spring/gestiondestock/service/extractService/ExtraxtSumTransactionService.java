package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractEnterAndExitCount;
import com.spring.gestiondestock.repositories.extractRepository.ExtractSumTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;

@Service
public class ExtraxtSumTransactionService {
    private static ExtractSumTransactionRepository extractSumTransactionRepository;
    public ExtraxtSumTransactionService(ExtractSumTransactionRepository extractSumTransactionRepository) {
        ExtraxtSumTransactionService.extractSumTransactionRepository = extractSumTransactionRepository;
    }
    public ExtractEnterAndExitCount SumTransactionEnterAndExit(String lieu, Date date, Date dateBebut,Date dateFin) throws SQLException, ClassNotFoundException {
        return extractSumTransactionRepository.SumTransactionEnterAndExit(lieu, date,dateBebut,dateFin);
    }
}
