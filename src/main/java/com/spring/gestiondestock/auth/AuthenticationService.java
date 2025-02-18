package com.spring.gestiondestock.auth;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.gestiondestock.model.Users;
import com.spring.gestiondestock.model.enums.RoleUser;
import com.spring.gestiondestock.repositories.InterfaceUser;
import com.spring.gestiondestock.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final InterfaceUser interfaceUser;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Value("${aws.s3.access.key}")
    private String AwsAccesKey;

    @Value("${aws.s3.secret.key}")
    private String AwsSecretKey;

    public AuthenticationResponse register(MultipartFile file, RegisterRequest request) {
        var user = Users.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .address(request.getAddress())
                .contact(request.getContact())
                .role(RoleUser.valueOf(request.getRole()))
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .image(saveFileToAwsBucket(file))
                .build();
        interfaceUser.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = interfaceUser.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
