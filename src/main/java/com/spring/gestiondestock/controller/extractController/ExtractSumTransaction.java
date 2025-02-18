package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractEnterAndExitCount;
import com.spring.gestiondestock.service.extractService.ExtraxtSumTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/transactions/sum")
public class ExtractSumTransaction {
    private static ExtraxtSumTransactionService extraxtSumTransactionService;
    public ExtractSumTransaction(ExtraxtSumTransactionService extraxtSumTransactionService) {
        this.extraxtSumTransactionService = extraxtSumTransactionService;
    }
    @GetMapping("/enterandexit")
     public ExtractEnterAndExitCount sumTransactionEnterAndExit(
            @RequestParam(required = false) String lieu,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin
    ) throws SQLException, ClassNotFoundException {
        Date formattedDate = null;
        Date dateDebutFormatted = null;
        Date dateFinFormatted = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        if (date != null && !date.isEmpty()) {
            formattedDate = Date.valueOf(LocalDate.parse(date, formatter));
        }
        if (dateDebut != null && !dateDebut.isEmpty()) {
            dateDebutFormatted = Date.valueOf(LocalDate.parse(dateDebut, formatter));
        }
        if (dateFin != null && !dateFin.isEmpty()) {
            dateFinFormatted = Date.valueOf(LocalDate.parse(dateFin, formatter));
        }

        return extraxtSumTransactionService.SumTransactionEnterAndExit(lieu, formattedDate, dateDebutFormatted, dateFinFormatted);
    }
}
