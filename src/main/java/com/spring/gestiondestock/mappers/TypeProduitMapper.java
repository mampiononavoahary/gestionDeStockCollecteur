package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.TypeProduitRequest;
import com.spring.gestiondestock.dtos.responses.TypeProduitResponse;
import com.spring.gestiondestock.model.TypeProduit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeProduitMapper {
    public TypeProduit toEntity(TypeProduitRequest typeProduitRequest) {
        return TypeProduit.builder()
                .nom_type_produit(typeProduitRequest.getNom_type_produit())
                .symbole(typeProduitRequest.getSymbole())
                .build();
    }
    public TypeProduitResponse toResponse(TypeProduit typeProduit) {
        return TypeProduitResponse.builder()
                .id_type_produit(typeProduit.getId_type_produit())
                .nom_type_produit(typeProduit.getNom_type_produit())
                .symbole(typeProduit.getSymbole())
                .build();
    }
}
