package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.PicSumBuy;
import com.spring.gestiondestock.service.extractService.PicSumBuyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions/picsum")
public class PicSumBuyController {
    private final PicSumBuyService picSumBuyService;

    public PicSumBuyController(PicSumBuyService picSumBuyService) {
        this.picSumBuyService = picSumBuyService;
    }
    @GetMapping
    public List<PicSumBuy> getPicSumBuy() throws SQLException, ClassNotFoundException {
        return picSumBuyService.findAll();
    }
}
