package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.service.extractService.TotalTransactionService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/transactions/total")
public class TotalTransactionController {
    private static TotalTransactionService totalTransactionService;
    public TotalTransactionController(TotalTransactionService totalTransactionService) {
        this.totalTransactionService = totalTransactionService;
    }
    @GetMapping("/enter")
    public int enterTotalTransaction(@RequestParam(required = false) String location, @RequestParam(required = false) String date) throws SQLException, ClassNotFoundException {
        Date formatterDate = null;

        if (date != null && !date.isEmpty()) {
            // Convertir la date en LocalDate
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            // Convertir LocalDate en java.sql.Date
            formatterDate = Date.valueOf(localDate);
        }
        return totalTransactionService.totalTransactionEnter(location, formatterDate);
    }
    @GetMapping("/exit")
    public int exitTotalTransaction(@RequestParam(required = false) String location, @RequestParam(required = false) String date) throws SQLException, ClassNotFoundException {
        Date formatterDate = null;

        if (date != null && !date.isEmpty()) {
            // Convertir la date en LocalDate
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            // Convertir LocalDate en java.sql.Date
            formatterDate = Date.valueOf(localDate);
        }
        return  totalTransactionService.totalTransactionExit(location, formatterDate);
    }
}
