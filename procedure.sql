use restoran;

delimiter $$
create procedure dobavi_stavke_menija(in pIdMenija int)
begin
     select IdStavke, Naziv, Cijena,Op from stavke_menija_info where IdMenija=pIdmenija ;
end$$
delimiter ;

delimiter $$
create procedure dodaj_jelo(in pIdJela int, in pNaziv varchar(45), in pCijena decimal(5,2), in pOpis varchar(200), in pJePosno tinyint, in pIdMenija int)
begin
	insert into jelo(IdJela,Naziv,Cijena,Opis,JePosno,IdMenija) values (pIdJela,pNaziv,pCijena,pOpis,pJePosno,pIdMenija);
end$$
delimiter ;



delimiter $$
create procedure dodaj_piće(in pIdPića int, in pNaziv varchar(45), in pCijena decimal(5,2), in pOpis varchar(200), in pJeAlkoholno tinyint, in pIdMenija int)
begin
	insert into piće(IdPića,Naziv,Cijena,Opis,JeAlkoholno,IdMenija) values (pIdPića,pNaziv,pCijena,pOpis,pJeAlkoholno,pIdMenija);
end$$
delimiter ;



delimiter $$
create procedure obriši_jelo(in pIdJela int, in pIdMenija int)
begin
	delete from jelo where IdJela=pIdJela and IdMenija=pIdMenija;
end$$
delimiter ;

delimiter $$
create procedure obriši_piće(in pIdPića int, in pIdMenija int)
begin
	delete from piće where IdPića=pIdPića and IdMenija=pIdMenija;
end$$
delimiter ;



delimiter $$
create procedure ažuriraj_jelo(in pIdJela int, in pIdMenija int, in pNaziv varchar(45), in pCijena decimal(5,2), in pOpis varchar(200))
begin
	update jelo set Naziv=pNaziv, Cijena=pCijena, Opis=pOpis where IdJela=pIdJela and IdMenija=pIdMenija;
end$$
delimiter ;



delimiter $$
create procedure ažuriraj_piće(in pIdPića int, in pIdMenija int, in pNaziv varchar(45), in pCijena decimal(5,2), in pOpis varchar(200))
begin
	update piće set Naziv=pNaziv, Cijena=pCijena, Opis=pOpis where IdPića=pIdPića and IdMenija=pIdMenija;
end$$
delimiter ;

delimiter $$
create procedure dobavi_meni(in pIdMenija int, out pDatumObjavljivanja date)
begin
	select DatumObjavljivanja into pDatumObjavljivanja from meni where IdMenija=pIdMenija;
end$$
delimiter ;


delimiter $$
create procedure dobavi_radnike(in pIdRestorana int)
begin
	select JMB,Ime,Prezime,Adresa,Plata,Tip from zaposleni_info
    where IdRestorana=pIdRestorana;
end$$
delimiter ;


delimiter $$
create procedure dobavi_jezike(in pJMBKonobara char(13))
begin
	select Naziv from strani_jezik where JMBKonobara=pJMBKonobara;
end$$
delimiter ;


delimiter $$
create procedure dobavi_brojeve(in pJMBZaposlenog char(13))
begin
	select BrojTelefona from telefon_zaposleni where JMBZaposlenog=pJMBZaposlenog;
end$$
delimiter ;


delimiter $$
create procedure dobavi_specijalizacije(in pJMBKuvara char(13))
begin
	select Naziv from specijalizacija_jelo where JMBKuvara=pJMBKuvara;
end$$
delimiter ;


delimiter $$
create procedure dobavi_obaveze(in pJMBMenadžera char(13))
begin
	select Naziv from radna_obaveza where JMBMenadžera=pJMBMenadžera;
end$$
delimiter ;

delimiter $$
create procedure dobavi_iskustvo_kokteli(in pJMBŠankera char(13), out pIskustvoSaKoktelima tinyint)
begin
	select IskustvoSaKoktelima into pIskustvoSaKoktelima from šanker where JMBZaposlenog=pJMBŠankera;
end$$
delimiter ;


delimiter $$
create procedure dodaj_zaposlenog(in pJMBZaposlenog char(13), in pIme varchar(20), in pPrezime varchar(20), in pAdresa varchar(45),
	in pPlata int, in pIdRestorana int, in pTip varchar(45),in pIskustvoSaKoktelima tinyint)
begin
	insert into zaposleni(JMB,Ime,Prezime,Adresa,Plata,Idrestorana) values (pJMBZaposlenog,pIme,pPrezime,pAdresa,pPlata,pIdRestorana);
    if pTip='Konobar' then
		insert into konobar(JMBZaposlenog) values (pJMBZaposlenog);
    end if;
    if pTip='Menadžer' then
		insert into menadžer(JMBZaposlenog) values (pJMBZaposlenog);
    end if;
    if pTip='Kuvar' then
		insert into kuvar(JMBZaposlenog) values (pJMBZaposlenog);
    end if;
    if pTip='Šanker' then
		insert into šanker(JMBZaposlenog, IskustvoSaKoktelima) values (pJMBZaposlenog, pIskustvoSaKoktelima);
    end if;
    
end$$
delimiter ;

delimiter $$
create procedure dodaj_jezik(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	insert into strani_jezik(Naziv,JMBKonobara) values (pNaziv, pJMBZaposlenog);
end$$
delimiter ;


delimiter $$
create procedure dodaj_specijalizaciju(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	insert into specijalizacija_jelo(JMBKuvara,Naziv) values (pJMBZaposlenog, pNaziv);
end$$
delimiter ;

delimiter $$
create procedure dodaj_obavezu(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	insert into radna_obaveza(JMBMenadžera,Naziv) values (pJMBZaposlenog, pNaziv);
end$$
delimiter ;

delimiter $$
create procedure dodaj_broj(in pJMBZaposlenog char(13), in pBrojTelefona varchar(20))
begin
	insert into telefon_zaposleni(BrojTelefona, JMBZaposlenog) values (pBrojTelefona, pJMBZaposlenog);
end$$
delimiter ;

delimiter $$
create procedure dodaj_kuvara(in pJMBZaposlenog char(13))
begin
	insert into kuvar(JMBZaposlenog) values (pJMBZaposlenog);
end$$
delimiter ;

delimiter $$
create procedure dodaj_menadžera(in pJMBZaposlenog char(13))
begin
	insert into menadžer(JMBZaposlenog) values (pJMBZaposlenog);
end$$
delimiter ;


delimiter $$
create procedure dodaj_konobara(in pJMBZaposlenog char(13))
begin
	insert into konobar(JMBZaposlenog) values (pJMBZaposlenog);
end$$
delimiter ;

delimiter $$
create procedure dodaj_šankera(in pJMBZaposlenog char(13), in pIskustvoSaKoktelima tinyint)
begin
	insert into šanker(JMBZaposlenog, IskustvoSaKoktelima) values (pJMBZaposlenog, pIskustvoSaKoktelima);
end$$
delimiter ;

delimiter $$
create procedure obriši_zaposlenog(in pJMBZaposlenog char(13), in pIdRestorana int)
begin
	delete from zaposleni where JMB=pJMBZaposlenog and IdRestorana=pIdRestorana;
end$$
delimiter ;




delimiter $$
create procedure obriši_jezike(in pJMBZaposlenog char(13))
begin
	delete from strani_jezik where JMBKonobara=pJMBZaposlenog;
end$$
delimiter ;


delimiter $$
create procedure obriši_specijalizacije(in pJMBZaposlenog char(13))
begin
	delete from specijalizacija_jelo where JMBKuvara=pJMBZaposlenog;
end$$
delimiter ;

delimiter $$
create procedure obriši_obaveze(in pJMBZaposlenog char(13))
begin
	delete from radna_obaveza where JMBMenadžera=pJMBZaposlenog;
end$$
delimiter ;

delimiter $$
create procedure obriši_brojeve(in pJMBZaposlenog char(13))
begin
	delete from telefon_zaposleni where JMBZaposlenog=pJMBZaposlenog;
end$$
delimiter ;


delimiter $$
create procedure ažuriraj_zaposlenog(in pJMBZaposlenog char(13), in pIdRestorana int, in pPlata int, in pAdresa varchar(45))
begin
	update zaposleni set Plata=pPlata, Adresa=pAdresa where JMB=pJMBZaposlenog and IdRestorana=pIdRestorana;
end$$
delimiter ;


delimiter $$
create procedure obriši_jezik(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	delete from strani_jezik where JMBKonobara=pJMBZaposlenog and Naziv=pNaziv;
end$$
delimiter ;


delimiter $$
create procedure obriši_specijalizaciju(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	delete from specijalizacija_jelo where JMBKuvara=pJMBZaposlenog and Naziv=pNaziv;
end$$
delimiter ;

delimiter $$
create procedure obriši_obavezu(in pJMBZaposlenog char(13), in pNaziv varchar(20))
begin
	delete from radna_obaveza where JMBMenadžera=pJMBZaposlenog and Naziv=pNaziv;
end$$
delimiter ;

delimiter $$
create procedure obriši_broj(in pJMBZaposlenog char(13), in pBrojTelefona varchar(20))
begin
	delete from telefon_zaposleni where JMBZaposlenog=pJMBZaposlenog and BrojTelefona=pBrojTelefona;
end$$
delimiter ;


delimiter $$
create procedure dobavi_narudžbe()
begin
	select BrojNarudžbe, VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara from narudžba;
end$$
delimiter ;

delimiter $$
create procedure dobavi_stavke(in pBrojNarudžbe int)
begin
	select IdStavke, Kolicina,BrojNarudžbe,IdJela,IdPića from stavka_narudžbe where BrojNarudžbe=pBrojNarudžbe;
end$$
delimiter ;


delimiter $$
create procedure dobavi_jelo_ime(in pIdJela int, out pNaziv varchar(45))
begin
	select Naziv into pNaziv from jelo where IdJela=pIdJela;
end$$
delimiter ;

delimiter $$
create procedure dobavi_piće_ime(in pIdPića int,  out pNaziv varchar(45))
begin
	select Naziv into pNaziv from piće where IdPića=pIdPića;
end$$
delimiter ;

delimiter $$
create procedure dobavi_ime_zaposlenog(in pJMBZaposlenog char(13),  out pIme varchar(20))
begin
	select Ime into pIme from zaposleni where JMB=pJMBZaposlenog;
end$$
delimiter ;


delimiter $$
create procedure dobavi_JMB_konobara(in pIdRestorana int)
begin
	select JMB from zaposleni z inner join konobar k on k.JMBZaposlenog=z.JMB where IdRestorana=pIdRestorana;
end$$
delimiter ;


delimiter $$
create procedure dobavi_oznake_stolova(in pIdRestorana int)
begin
	select IdStola from sto where IdRestorana=pIdRestorana;
end$$
delimiter ;


delimiter $$
create procedure dodaj_narudžbu(in pJeProcesirana tinyint, in pIdStola int, in JMBKonobara char(13), out pBrojNarudzbe int)
begin
	insert into narudžba(VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara) values (sysdate(), pJeProcesirana, pIdStola, JMBKonobara);
    set pBrojNarudzbe = last_insert_id();
end$$
delimiter ;


delimiter $$
create procedure dobavi_id_jela(in pNaziv varchar(45), out pIdJela int)
begin
	select IdJela into pIdJela from jelo where Naziv=pNaziv;
end$$
delimiter ;



delimiter $$
create procedure dobavi_id_pića(in pNaziv varchar(45), out pIdPića int)
begin
	select IdPića into pIdPića from piće where Naziv=pNaziv;
end$$
delimiter ;


delimiter $$
create procedure dodaj_stavku_narudžbe_jelo(in pKolicina int, in pBrojNarudžbe int, in pIdJela int)
begin
	insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdJela) values (pKolicina,pBrojNarudžbe,pIdJela);
end$$
delimiter ;

delimiter $$
create procedure dodaj_stavku_narudžbe_piće(in pKolicina int, in pBrojNarudžbe int, in pIdPića int)
begin
	insert into stavka_narudžbe(Kolicina, BrojNarudžbe,IdPića) values (pKolicina,pBrojNarudžbe,pIdPića);
end$$
delimiter ;


delimiter $$
create procedure dobavi_narudžbu(in pBrojNarudžbe int)
begin
	select BrojNarudžbe, VrijemeIzdavanja, JeProcesirana, IdStola, JMBKonobara from narudžba where BrojNarudžbe=pBrojNarudžbe; 
end$$
delimiter ;

delimiter $$
create procedure obriši_narudžbu(in pBrojNarudžbe int)
begin
	delete from narudžba where BrojNarudžbe=pBrojNarudžbe; 
end$$
delimiter ;


delimiter $$
create procedure procesiraj_narudžbu(in pBrojNarudžbe int, in pSaGotovinom tinyint)
begin
	update narudžba set JeProcesirana=true where BrojNarudžbe=pBrojNarudžbe; 
    insert into račun(VrijemeIzdavanja,SaGotovinom,BrojNarudžbe) values (sysdate(),pSaGotovinom,pBrojNarudžbe);
end$$
delimiter ;

delimiter $$
create procedure dobavi_račune()
begin
	select IdRačuna, VrijemeIzdavanja, SaGotovinom from račun;
end$$
delimiter ;

delimiter $$
create procedure dobavi_račun(in pIdRačuna int)
begin
	select IdRačuna, VrijemeIzdavanja, SaGotovinom from račun where IdRačuna=pIdRačuna;
end$$
delimiter ;

