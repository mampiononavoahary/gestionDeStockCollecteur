package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.PicSumBuy;
import com.spring.gestiondestock.repositories.extractRepository.PicSumBuyRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PicSumBuyService {
    private final PicSumBuyRepository picSumBuyRepository;
    public PicSumBuyService(PicSumBuyRepository picSumBuyRepository) {
        this.picSumBuyRepository = picSumBuyRepository;
    }
    public List<PicSumBuy> findAll() throws SQLException, ClassNotFoundException {
        return picSumBuyRepository.findAll();
    }
}
