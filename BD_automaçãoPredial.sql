create database automaçãoPredial;
use automaçãoPredial;

create table sala ( id char (18) not null,
 numeroSala char (25), horaLigar datetime not null,
 horaDesligar datetime not null, primary key (id));
 
