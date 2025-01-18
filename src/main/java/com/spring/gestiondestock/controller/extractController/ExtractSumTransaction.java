package com.spring.gestiondestock.controller.extractController;

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
    @GetMapping("/enter")
    public Double SumTransactionEnter(@RequestParam (required = false) String lieu, @RequestParam (required = false) String date) throws SQLException, ClassNotFoundException {
        Date formatterDate = null;

        if (date != null && !date.isEmpty()) {
            // Convertir la date en LocalDate
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            // Convertir LocalDate en java.sql.Date
            formatterDate = Date.valueOf(localDate);
        }

        return extraxtSumTransactionService.SumTransactionEnter(lieu, formatterDate);
    }
    @GetMapping("/exit")
    public Double SumTransactionExit(@RequestParam (required = false) String lieu, @RequestParam (required = false) String date) throws SQLException, ClassNotFoundException {
        Date formatterDate = null;

        if (date != null && !date.isEmpty()) {
            // Convertir la date en LocalDate
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            // Convertir LocalDate en java.sql.Date
            formatterDate = Date.valueOf(localDate);
        }

        return extraxtSumTransactionService.SumTransactionExit(lieu, formatterDate);
    }
}
