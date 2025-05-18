package com.spring.gestiondestock.dtos.requests;

import com.spring.gestiondestock.dtos.responses.ProduitsCollecterResponse;
import com.spring.gestiondestock.model.DebitCollecteur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DebitWrapper {
    private List<ProduitsCollecterRequest> produitsCollecterRequests;
    private DebitCollecteur debitCollecteur;
}
