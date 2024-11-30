package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.DetailTransactionRequest;
import com.spring.gestiondestock.dtos.responses.DetailTransactionResponse;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetailTransactionMapper {
    public DetailTransaction toEntity(DetailTransactionRequest detailTransactionRequest){
        return DetailTransaction.builder()
                .type_de_transaction(TypeDeTransaction.valueOf(detailTransactionRequest.getType_de_transaction()))
                .date_de_transaction(detailTransactionRequest.getDate_de_transaction())
                .lieu_de_transaction(LieuDeTransaction.valueOf(detailTransactionRequest.getLieu_de_transaction()))
                .id_client(Clients.builder().id_clients(detailTransactionRequest.getId_client()).build())
                .build();
    }
    public DetailTransactionResponse toResponse(DetailTransaction detailTransaction){
        return DetailTransactionResponse.builder()
                .id_detail_transaction(detailTransaction.getId_detail_transaction())
                .id_detail_transaction_uuid(detailTransaction.getId_detail_transaction_uuid())
                .type_de_transaction(detailTransaction.getType_de_transaction())
                .date_de_transaction(detailTransaction.getDate_de_transaction())
                .lieu_de_transaction(detailTransaction.getLieu_de_transaction())
                .id_client(detailTransaction.getId_client())
                .build();
    }
}
