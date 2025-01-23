package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExtractEnterAndExitCount {
    private  Double sum_entre;
    private  Double sum_sortie;
    private int total_entre;
    private int total_sortie;
}
