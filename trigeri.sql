use restoran;

delimiter $$
create trigger provjeri_novi_racun before insert on račun 
for each row
begin
	declare broj_računa_narudžbe int;
    select count(*) into broj_računa_narudžbe from račun where BrojNarudžbe=new.BrojNarudžbe; # ispitujemo da li novododana torka(BrojNarudžbe) već postoji
    
    if broj_računa_narudžbe > 0 then
		signal sqlstate '45000' set message_text='Narudžba je već procesirana';
	end if;
end$$
delimiter ;

delimiter $$
create trigger provjeri_novog_radnika before insert on kuvar 
for each row
begin
	declare broj_JMB int;
    select count(*) into broj_JMB from zaposleni where JMB=new.JMBZaposlenog;
    
    if broj_JMB = 0 then
		signal sqlstate '45000' set message_text='Ne postoji zaposleni!';
	end if;
end$$
delimiter ;

