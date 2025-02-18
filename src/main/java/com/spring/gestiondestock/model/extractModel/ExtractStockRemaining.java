package com.spring.gestiondestock.model.extractModel;

import com.spring.gestiondestock.model.LigneStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExtractStockRemaining {
    private String lieu_stock;
    private List<LigneStock> ligne_stock;
}
