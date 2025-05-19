package com.spring.gestiondestock.mappers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class DetailProduitMapper {
    public DetailProduit toDetailProduit(DetailProduitRequest detailProduitRequest) {
        return DetailProduit.builder()
                .nom_detail(detailProduitRequest.getNom_detail())
                .symbole(detailProduitRequest.getSymbole())
                .categorie_produit(detailProduitRequest.getCategorie_produit())
                .description(detailProduitRequest.getDescription())
                .prix_d_achat(detailProduitRequest.getPrix_d_achat())
                .prix_de_vente(detailProduitRequest.getPrix_de_vente())
                .unite(detailProduitRequest.getUnite())
                .build();
    }
    public DetailProduitResponse toDetailProduitResponse(DetailProduit detailProduit) {
        return new DetailProduitResponse(
                detailProduit.getId_detail_produit(),
                detailProduit.getNom_detail(),
                detailProduit.getSymbole(),
                detailProduit.getCategorie_produit(),
                detailProduit.getDescription(),
                detailProduit.getPrix_d_achat(),
                detailProduit.getPrix_de_vente(),
                detailProduit.getUnite(),
                detailProduit.getListProduitAvecDetail()
        );
    }
}
