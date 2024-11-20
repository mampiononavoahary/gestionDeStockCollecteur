create database gestiondestock;

\c gestiondestock;

create type role_user as enum ('USER','ADMIN');
create type unite as enum('KG','T','AR');
create type type_de_transaction as enum ('ENTRE', 'SORTIE');
create type lieu_transaction as enum('ITAOSY','ALATSINAINIKELY','AMBATONDRAZAKA');
create type status as enum('PAYE','EN_ATTENTE');
create table if not exists users(
                      id_user serial primary key ,
                      nom varchar(100),
                      prenom varchar(100),
                      address varchar(100),
                      contact varchar(100),
                      image varchar(200),
                      role role_user,
                      username varchar(100),
                      password varchar(100)
);
create table if not exists detail_produit(
                     id_detail_produit serial primary key ,
                     nom_detail varchar(200),
                    symbole varchar(5),
                    description varchar(200),
                    prix_d_achat double precision,
                    prix_de_vente double precision,
                    unite unite
);
create table if not exists clients(
    id_clients serial primary key ,
    nom varchar(200),
    prenom varchar(200),
    adresse varchar(200),
    telephone varchar(200)
);
create table if not exists detail_transaction(
    id_detail_transaction serial primary key ,
    type_de_transaction type_de_transaction,
    date_de_transaction timestamp,
    lieu_de_transaction lieu_transaction,
    id_client serial references clients(id_clients),
    id_user serial references users(id_user)
);
create table if not exists produit(
    id_produit serial primary key ,
    nom_produit varchar(100)
);
create table if not exists produit_avec_detail(
    id_produit_avec_detail serial primary key ,
    id_produit serial references produit(id_produit),
    id_detail_produit serial references detail_produit(id_detail_produit)
);
create table if not exists transactions(
    id_transaction serial primary key ,
    id_produit_avec_detail serial references produit_avec_detail(id_produit_avec_detail),
    id_detail_transaction serial references  detail_transaction(id_detail_transaction),
    quantite double precision,
    unite unite,
    status status
);
create table if not exists stock(
    id_stock serial primary key ,
    lieu_stock lieu_transaction,
    id_produit_avec_detail serial references produit_avec_detail(id_produit_avec_detail),
    quantite_stock double precision,
    unite unite
);


