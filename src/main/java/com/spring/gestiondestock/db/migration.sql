create database gestiondestock;

\c gestiondestock;

create type role_user as enum ('USER','ADMIN');
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create type unite as enum('KG','T','AR');
create type type_de_transaction as enum ('ENTRE', 'SORTIE');
create type categorie_produit as enum('PRODUIT_PREMIER','PRODUIT_FINI');
create type lieu_de_transaction as enum('ITAOSY','ALATSINAINIKELY','AMBATONDRAZAKA','ANOSIZATO','AMPASIKA');
create type status as enum('PAYE','EN_ATTENTE');
create table if not exists users(
                      id_user serial primary key ,
                      nom varchar(100),
                      prenom varchar(100),
                      address varchar(100),
                      contact varchar(100),
                      image varchar(200),
                      role role_user,
                      username varchar(100) unique,
                      password varchar(100)
);
create table if not exists detail_produit(
                     id_detail_produit serial primary key ,
                     nom_detail varchar(200),
                    symbole varchar(5),
                    description varchar(200),
                    categorie_produit categorie_produit,
                    prix_d_achat double precision check ( prix_d_achat >=0),
                    prix_de_vente double precision check ( prix_de_vente >=0 ),
                    unite unite,
                    image_url text
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
    uuid_detail UUID DEFAULT uuid_generate_v4(),
    type_de_transaction type_de_transaction,
    date_de_transaction timestamp,
    lieu_de_transaction lieu_de_transaction,
    id_client integer references clients(id_clients)
);
create table if not exists produit(
    id_produit serial primary key ,
    nom_produit varchar(100)
);
create table if not exists type_produit(
  id_type_produit serial primary key,
  nom_type_produit varchar(100),
  symbole varchar(5)
);

create table if not exists produit_avec_detail(
    id_produit_avec_detail serial primary key ,
    id_produit integer references produit(id_produit),
    id_type_produit integer references type_produit(id_type_produit) null,
    id_detail_produit integer references detail_produit(id_detail_produit)
);
create table if not exists transactions(
    id_transaction serial primary key ,
    id_produit_avec_detail integer references produit_avec_detail(id_produit_avec_detail),
    id_detail_transaction integer references  detail_transaction(id_detail_transaction),
    quantite double precision check ( quantite>0 ),
    unite unite,
    prix_unitaire double precision check ( prix_unitaire>0 ),
    status status,
    lieu_stock lieu_de_transaction
);
create table if not exists stock(
    id_stock serial primary key ,
    lieu_stock lieu_de_transaction,
    id_produit_avec_detail integer references produit_avec_detail(id_produit_avec_detail),
    quantite_stock double precision,
    unite unite
);
CREATE table if not exists pret_bancaire(
  id_pret_bancaire serial PRIMARY KEY,
  date_de_pret timestamp,
  produit integer references produit(id_produit),
  quantite double precision,
  unite unite,
  prix_unitaire double precision,
  taux_augmentation numeric(5,3),
  taux_mensuel numeric(5,3),
  date_de_remboursement timestamp
);
CREATE TABLE IF NOT EXISTS collecteur(
  id_collecteur serial PRIMARY KEY,
  nom varchar(100),
  prenom varchar(100),
  adresse varchar(100),
  telephone varchar(100)
);
CREATE TABLE IF NOT EXISTS credit_collecteur(
  id_credit_collecteur serial PRIMARY KEY,
  date_de_credit timestamp,
  montant double precision,
  description varchar(200),
  referance_credit varchar(200) unique,
  status boolean,
  id_collecteur integer references collecteur(id_collecteur)
);
CREATE TABLE IF NOT EXISTS debit_collecteur(
  id_debit_collecteur serial PRIMARY KEY,
  date_de_debit timestamp,
  lieu_de_collection varchar(100),
  description varchar(200),
  id_credit_collecteur integer references credit_collecteur(id_credit_collecteur)
);
CREATE TABLE IF NOT EXISTS produits_collecter(
  id_produits_collecter serial PRIMARY KEY,
  id_debit_collecteur integer references debit_collecteur(id_debit_collecteur),
  id_produit_avec_detail integer references produit_avec_detail(id_produit_avec_detail),
  quantite double precision check ( quantite>0 ),
  unite unite,
  prix_unitaire double precision check ( prix_unitaire>0 ),
  lieu_stock lieu_de_transaction
);
