package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.TypeProduitRequest;
import com.spring.gestiondestock.dtos.responses.TypeProduitResponse;
import com.spring.gestiondestock.service.impl.TypeProduitServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/produit/type")
public class TypeProduitController {
    private static TypeProduitServiceImpl typeProduitService;
    public TypeProduitController(TypeProduitServiceImpl typeProduitService) {
        TypeProduitController.typeProduitService = typeProduitService;
    }
    @GetMapping
    public List<TypeProduitResponse> getAllTypeProduit() throws SQLException, ClassNotFoundException {
        return typeProduitService.getTypeProduit();
    }
    @PostMapping("/post")
    public TypeProduitResponse createTypeProduit(@RequestBody TypeProduitRequest typeProduit) throws SQLException, ClassNotFoundException {
        return typeProduitService.save(typeProduit);
    }
}
