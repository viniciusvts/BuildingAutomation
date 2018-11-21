create database automaçãoPredial;
use automaçãoPredial;

create table sala ( id char (18) not null,
 numeroSala char (25), horaLigar datetime not null,
 horaDesligar datetime not null, primary key (id));
 
INSERT INTO sala values ('18A101', 'A1', '2018-11-27 21:35:00',
 '2018-11-27 18:35:00');
 
INSERT INTO sala values ('18B606', 'B606', '2018-11-27 14:12:00',
 '2018-11-27 20:35:00');
 
 INSERT INTO sala values ('18B201', 'A201', '2018-11-27 19:15:00',
 '2018-11-27 21:35:00');
