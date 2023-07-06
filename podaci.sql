use restoran;

insert into meni(DatumObjavljivanja) values (sysdate());
insert into mjesto(BrojPoste, Naziv) value ('78000', 'Banja Luka');
insert into restoran(Naziv, Adresa, BrojPosteMjesta,IdMenija) value ('N&N','KPPK 12','78000',1);

insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values(23,'Piletina',14.00,'Grilovana pileća prsa',0,1);
insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values(45,'Ćevapi',9.00,'Velika porcija',0,1);
insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values(52,'Bečka šnicla',15.00,'U sosu od gljiva',0,1);
insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values(111,'Karađorđeva šnicla',13.00,'Punjena kajmako, pohovana sa slaninom',0,1);
insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values(78,'Šampinjoni',10.00,'Grilovana pileća prsa',1,1);

insert into piće(IdPića,Naziv,Cijena,Opis,JeAlkoholno,IdMenija) values(21,'Coca-Cola',2.50,'Gazirano piće',0,1);


insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891015', 'Marko', 'Marković', 'Sime Matavulja 12', 1200,1);
insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891016', 'Lazar', 'Lazarević', 'Alekse Šantića 19', 1200,1);
insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891017', 'Mirko', 'Mirković', 'Milovana Glišića 5', 1200,1);
insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891018', 'Nikola', 'Nikolić', 'Vojvode Stepe 45', 1400,1);
insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891019', 'Petar', 'Petrović', 'Krajiških Korpusa 74', 1300,1);
insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values ('1234567891011', 'Ognjen', 'Ognjenović', 'Vuka Karadžića 1', 1800,1);

insert into konobar(JMBZaposlenog) values ('1234567891015');
insert into konobar(JMBZaposlenog) values ('1234567891016');
insert into konobar(JMBZaposlenog) values ('1234567891017');
insert into kuvar(JMBZaposlenog) values ('1234567891018');
insert into šanker(JMBZaposlenog, IskustvoSaKoktelima) values ('1234567891019', 1);
insert into menadžer(JMBZaposlenog) values ('1234567891011');

insert into strani_jezik(Naziv,JMBKonobara) values ('Engleski', '1234567891017');
insert into strani_jezik(Naziv,JMBKonobara) values ('Francuski', '1234567891017');
insert into strani_jezik(Naziv,JMBKonobara) values ('Engleski', '1234567891015');
insert into strani_jezik(Naziv,JMBKonobara) values ('Engleski', '1234567891016');

insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)66 452 365', '1234567891015');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)66 745 336', '1234567891015');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)66 741 521', '1234567891016');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)66 225 889', '1234567891017');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)65 444 333', '1234567891018');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)65 215 555', '1234567891019');
insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values ('+387 (0)65 489 224', '1234567891011');


insert into specijalizacija_jelo(JMBKuvara,Naziv) values ('1234567891018', 'Jela sa mesom');
insert into specijalizacija_jelo(JMBKuvara,Naziv) values ('1234567891018', 'Salate');

insert into radna_obaveza(JMBMenadžera,Naziv) value ('1234567891011','Dekoracije');
insert into radna_obaveza(JMBMenadžera,Naziv) value ('1234567891011','Prihodi i rashodi');

insert into sto(Kapacitet, JeRezervisan, IdRestorana) values (5,0,1);
insert into sto(Kapacitet, JeRezervisan, IdRestorana) values (2,0,1);

insert into narudžba(VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara) values (sysdate(),0,1,'1234567891017');
insert into narudžba(VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara) values (sysdate(),0,2,'1234567891015');
insert into narudžba(VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara) values (sysdate(),0,1,'1234567891016');

insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdJela) values (2,1,23);
insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdJela) values (1,1,45); #45 52 74
insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdPića) values (1,1,21);
insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdJela) values (1,1,23);












