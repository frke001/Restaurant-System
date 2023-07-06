use restoran;

create view zaposleni_info as 
	select z.JMB,Ime,Prezime,Adresa,Plata,IdRestorana,
		case 
			when k.JMBZaposlenog is not null then 'Konobar'
			when ku.JMBZaposlenog is not null then 'Kuvar'
            when s.JMBZaposlenog is not null then 'Šanker'
            when m.JMBZaposlenog is not null then 'Menadžer'
            else 'Nepoznato'
		end as Tip
    from zaposleni z 
    left join konobar k on k.JMBZaposlenog=z.JMB
    left join kuvar ku on ku.JMBZaposlenog=z.JMB
    left join šanker s on s.JMBZaposlenog=z.JMB
    left join menadžer m on m.JMBZaposlenog=z.JMB;
    
create view stavke_menija_info as 
	select IdJela as IdStavke, Naziv, Cijena, concat(Opis, if(JePosno,' (Posno)', ' (Nije posno)')) as Op, IdMenija from jelo
    union
	select IdPića as IdStavke, Naziv, Cijena, concat(Opis, if(JeAlkoholno,' (Alkoholno)', ' (Bezalkoholno)')) as Op, IdMenija from piće;
    
