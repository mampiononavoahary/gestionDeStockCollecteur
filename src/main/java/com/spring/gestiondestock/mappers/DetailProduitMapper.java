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
    @Value("${aws.s3.access.key}")
    private String AwsAccesKey;

    @Value("${aws.s3.secret.key}")
    private String AwsSecretKey;
    public DetailProduit toDetailProduit(MultipartFile image_url,DetailProduitRequest detailProduitRequest) {
        return DetailProduit.builder()
                .nom_detail(detailProduitRequest.getNom_detail())
                .symbole(detailProduitRequest.getSymbole())
                .categorie_produit(detailProduitRequest.getCategorie_produit())
                .description(detailProduitRequest.getDescription())
                .prix_d_achat(detailProduitRequest.getPrix_d_achat())
                .prix_de_vente(detailProduitRequest.getPrix_de_vente())
                .unite(detailProduitRequest.getUnite())
                .image_url(saveFileToAwsBucket(image_url))
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
                detailProduit.getImage_url(),
                detailProduit.getListProduitAvecDetail()
        );
    }
    private String saveFileToAwsBucket(MultipartFile file) {
        try {
            String s3FileName = file.getOriginalFilename();
            System.out.println("🔄 Début de l'upload : " + s3FileName);

            BasicAWSCredentials credentials = new BasicAWSCredentials(AwsAccesKey, AwsSecretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_WEST_3)
                    .build();

            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            String bucketName = "file-upload-mi-collecte";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, objectMetadata);

            // ✅ Log avant l'upload
            System.out.println("📤 Envoi du fichier vers S3...");

            s3Client.putObject(putObjectRequest);

            // ✅ Log après l'upload
            System.out.println("✅ Fichier envoyé avec succès !");

            return "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'upload : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur d'upload vers AWS S3 : " + e.getMessage());
        }
    }
}
