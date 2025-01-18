create database gestiondestock;

\c gestiondestock;

create type role_user as enum ('USER','ADMIN');
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create type unite as enum('KG','T','AR');
create type type_de_transaction as enum ('ENTRE', 'SORTIE');
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
    lieu_de_transaction lieu_transaction,
    id_client integer references clients(id_clients)
);
create table if not exists produit(
    id_produit serial primary key ,
    nom_produit varchar(100)
);
create table if not exists produit_avec_detail(
    id_produit_avec_detail serial primary key ,
    id_produit integer references produit(id_produit),
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
    lieu_stock lieu_transaction,
    id_produit_avec_detail integer references produit_avec_detail(id_produit_avec_detail),
    quantite_stock double precision,
    unite unite
);

SELECT 
    t.id_transaction,
    dt.id_detail_transaction,
    t.quantite,
    t.unite,
    t.status,
    t.lieu_stock,
    dp.nom_detail,
    dp.symbole,
    dp.description,
    dp.prix_d_achat,
    dp.prix_de_vente,
    c.nom AS client_nom,
    c.prenom AS client_prenom,
    c.adresse AS client_adresse,
    c.telephone AS client_telephone,
    dt.type_de_transaction,
    dt.date_de_transaction,
    dt.lieu_de_transaction,
    p.nom_produit
FROM 
    transactions t
JOIN 
    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail
JOIN 
    detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit
JOIN 
    produit p ON pad.id_produit = p.id_produit
JOIN 
    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction
JOIN 
    clients c ON dt.id_client = c.id_clients
WHERE 
    dt.type_de_transaction = 'SORTIE' ORDER BY dt.date_de_transaction DESC; 

