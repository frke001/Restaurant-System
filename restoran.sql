-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema restoran
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema restoran
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restoran` DEFAULT CHARACTER SET utf8 ;
USE `restoran` ;

-- -----------------------------------------------------
-- Table `restoran`.`MJESTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`MJESTO` (
  `BrojPoste` INT NOT NULL,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`BrojPoste`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`MENI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`MENI` (
  `IdMenija` INT NOT NULL AUTO_INCREMENT,
  `DatumObjavljivanja` DATE NOT NULL,
  PRIMARY KEY (`IdMenija`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`RESTORAN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`RESTORAN` (
  `Naziv` VARCHAR(45) NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `BrojPosteMjesta` INT NOT NULL,
  `IdMenija` INT NOT NULL,
  `IdRestorana` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdRestorana`),
  INDEX `fk_RESTORAN_MJESTO1_idx` (`BrojPosteMjesta` ASC) VISIBLE,
  INDEX `fk_RESTORAN_MENI1_idx` (`IdMenija` ASC) VISIBLE,
  CONSTRAINT `fk_RESTORAN_MJESTO1`
    FOREIGN KEY (`BrojPosteMjesta`)
    REFERENCES `restoran`.`MJESTO` (`BrojPoste`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_RESTORAN_MENI1`
    FOREIGN KEY (`IdMenija`)
    REFERENCES `restoran`.`MENI` (`IdMenija`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`TELEFON_RESTORAN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`TELEFON_RESTORAN` (
  `Brojtelefona` VARCHAR(20) NOT NULL,
  `IdRestorana` INT NOT NULL,
  PRIMARY KEY (`Brojtelefona`, `IdRestorana`),
  INDEX `fk_TELEFON_RESTORAN_RESTORAN1_idx` (`IdRestorana` ASC) VISIBLE,
  CONSTRAINT `fk_TELEFON_RESTORAN_RESTORAN1`
    FOREIGN KEY (`IdRestorana`)
    REFERENCES `restoran`.`RESTORAN` (`IdRestorana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`STO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`STO` (
  `IdStola` INT NOT NULL AUTO_INCREMENT,
  `Kapacitet` INT NOT NULL,
  `JeRezervisan` TINYINT NOT NULL,
  `IdRestorana` INT NOT NULL,
  PRIMARY KEY (`IdStola`),
  INDEX `fk_STO_RESTORAN1_idx` (`IdRestorana` ASC) VISIBLE,
  CONSTRAINT `fk_STO_RESTORAN1`
    FOREIGN KEY (`IdRestorana`)
    REFERENCES `restoran`.`RESTORAN` (`IdRestorana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`REZERVACIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`REZERVACIJA` (
  `Datum` DATE NOT NULL,
  `Vrijeme` TIME NOT NULL,
  `ImeOsobe` VARCHAR(45) NOT NULL,
  `IdStola` INT NOT NULL,
  `IdRezervacije` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdRezervacije`),
  CONSTRAINT `fk_REZERVACIJA_STO1`
    FOREIGN KEY (`IdStola`)
    REFERENCES `restoran`.`STO` (`IdStola`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`JELO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`JELO` (
  `IdJela` INT NOT NULL,
  `Naziv` VARCHAR(45) NOT NULL,
  `Cijena` DECIMAL(5,2) NOT NULL,
  `Opis` VARCHAR(200) NULL,
  `JePosno` TINYINT NOT NULL,
  `IdMenija` INT NOT NULL,
  PRIMARY KEY (`IdJela`),
  INDEX `fk_JELO_MENI1_idx` (`IdMenija` ASC) VISIBLE,
  UNIQUE INDEX `Naziv_UNIQUE` (`Naziv` ASC) VISIBLE,
  CONSTRAINT `fk_JELO_MENI1`
    FOREIGN KEY (`IdMenija`)
    REFERENCES `restoran`.`MENI` (`IdMenija`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`PIĆE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`PIĆE` (
  `IdPića` INT NOT NULL,
  `Naziv` VARCHAR(45) NOT NULL,
  `Cijena` DECIMAL(5,2) NOT NULL,
  `JeAlkoholno` TINYINT NOT NULL,
  `IdMenija` INT NOT NULL,
  `Opis` VARCHAR(200) NULL,
  PRIMARY KEY (`IdPića`),
  INDEX `fk_PIĆE_MENI1_idx` (`IdMenija` ASC) VISIBLE,
  UNIQUE INDEX `Naziv_UNIQUE` (`Naziv` ASC) VISIBLE,
  CONSTRAINT `fk_PIĆE_MENI1`
    FOREIGN KEY (`IdMenija`)
    REFERENCES `restoran`.`MENI` (`IdMenija`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`ZAPOSLENI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`ZAPOSLENI` (
  `JMB` CHAR(13) NOT NULL,
  `Ime` VARCHAR(20) NOT NULL,
  `Prezime` VARCHAR(20) NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `Plata` INT NOT NULL,
  `IdRestorana` INT NOT NULL,
  PRIMARY KEY (`JMB`),
  INDEX `fk_ZAPOSLENI_RESTORAN1_idx` (`IdRestorana` ASC) VISIBLE,
  CONSTRAINT `fk_ZAPOSLENI_RESTORAN1`
    FOREIGN KEY (`IdRestorana`)
    REFERENCES `restoran`.`RESTORAN` (`IdRestorana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`KUVAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`KUVAR` (
  `JMBZaposlenog` CHAR(13) NOT NULL,
  PRIMARY KEY (`JMBZaposlenog`),
  CONSTRAINT `fk_KUVAR_ZAPOSLENI1`
    FOREIGN KEY (`JMBZaposlenog`)
    REFERENCES `restoran`.`ZAPOSLENI` (`JMB`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`KONOBAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`KONOBAR` (
  `JMBZaposlenog` CHAR(13) NOT NULL,
  PRIMARY KEY (`JMBZaposlenog`),
  CONSTRAINT `fk_KONOBAR_ZAPOSLENI1`
    FOREIGN KEY (`JMBZaposlenog`)
    REFERENCES `restoran`.`ZAPOSLENI` (`JMB`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`ŠANKER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`ŠANKER` (
  `JMBZaposlenog` CHAR(13) NOT NULL,
  `IskustvoSaKoktelima` TINYINT NOT NULL,
  PRIMARY KEY (`JMBZaposlenog`),
  CONSTRAINT `fk_ŠANKER_ZAPOSLENI1`
    FOREIGN KEY (`JMBZaposlenog`)
    REFERENCES `restoran`.`ZAPOSLENI` (`JMB`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`MENADŽER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`MENADŽER` (
  `JMBZaposlenog` CHAR(13) NOT NULL,
  PRIMARY KEY (`JMBZaposlenog`),
  CONSTRAINT `fk_MENADŽER_ZAPOSLENI1`
    FOREIGN KEY (`JMBZaposlenog`)
    REFERENCES `restoran`.`ZAPOSLENI` (`JMB`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`STRANI_JEZIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`STRANI_JEZIK` (
  `Naziv` VARCHAR(20) NOT NULL,
  `JMBKonobara` CHAR(13) NOT NULL,
  PRIMARY KEY (`Naziv`, `JMBKonobara`),
  INDEX `fk_STRANI_JEZIK_KONOBAR1_idx` (`JMBKonobara` ASC) VISIBLE,
  CONSTRAINT `fk_STRANI_JEZIK_KONOBAR1`
    FOREIGN KEY (`JMBKonobara`)
    REFERENCES `restoran`.`KONOBAR` (`JMBZaposlenog`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`SPECIJALIZACIJA_JELO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`SPECIJALIZACIJA_JELO` (
  `JMBKuvara` CHAR(13) NOT NULL,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`JMBKuvara`, `Naziv`),
  CONSTRAINT `fk_SPECIJALIZACIJA_JELO_KUVAR1`
    FOREIGN KEY (`JMBKuvara`)
    REFERENCES `restoran`.`KUVAR` (`JMBZaposlenog`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`RADNA_OBAVEZA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`RADNA_OBAVEZA` (
  `Naziv` VARCHAR(45) NOT NULL,
  `JMBMenadžera` CHAR(13) NOT NULL,
  PRIMARY KEY (`Naziv`, `JMBMenadžera`),
  INDEX `fk_RADNA_OBAVEZA_MENADŽER1_idx` (`JMBMenadžera` ASC) VISIBLE,
  CONSTRAINT `fk_RADNA_OBAVEZA_MENADŽER1`
    FOREIGN KEY (`JMBMenadžera`)
    REFERENCES `restoran`.`MENADŽER` (`JMBZaposlenog`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`NARUDŽBA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`NARUDŽBA` (
  `BrojNarudžbe` INT NOT NULL AUTO_INCREMENT,
  `VrijemeIzdavanja` DATETIME NOT NULL,
  `JeProcesirana` TINYINT NOT NULL,
  `IdStola` INT NOT NULL,
  `JMBKonobara` CHAR(13) NOT NULL,
  PRIMARY KEY (`BrojNarudžbe`),
  INDEX `fk_NARUDŽBA_STO1_idx` (`IdStola` ASC) VISIBLE,
  INDEX `fk_NARUDŽBA_KONOBAR1_idx` (`JMBKonobara` ASC) VISIBLE,
  CONSTRAINT `fk_NARUDŽBA_STO1`
    FOREIGN KEY (`IdStola`)
    REFERENCES `restoran`.`STO` (`IdStola`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NARUDŽBA_KONOBAR1`
    FOREIGN KEY (`JMBKonobara`)
    REFERENCES `restoran`.`KONOBAR` (`JMBZaposlenog`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`STAVKA_NARUDŽBE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`STAVKA_NARUDŽBE` (
  `Kolicina` INT NOT NULL,
  `BrojNarudžbe` INT NOT NULL,
  `IdJela` INT NULL,
  `IdPića` INT NULL,
  `IdStavke` INT NOT NULL AUTO_INCREMENT,
  INDEX `fk_STAVKA_NARUDŽBE_JELO1_idx` (`IdJela` ASC) VISIBLE,
  INDEX `fk_STAVKA_NARUDŽBE_PIĆE1_idx` (`IdPića` ASC) VISIBLE,
  PRIMARY KEY (`IdStavke`),
  CONSTRAINT `fk_STAVKA_NARUDŽBE_NARUDŽBA1`
    FOREIGN KEY (`BrojNarudžbe`)
    REFERENCES `restoran`.`NARUDŽBA` (`BrojNarudžbe`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_STAVKA_NARUDŽBE_JELO1`
    FOREIGN KEY (`IdJela`)
    REFERENCES `restoran`.`JELO` (`IdJela`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_STAVKA_NARUDŽBE_PIĆE1`
    FOREIGN KEY (`IdPića`)
    REFERENCES `restoran`.`PIĆE` (`IdPića`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`RAČUN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`RAČUN` (
  `IdRačuna` INT NOT NULL AUTO_INCREMENT,
  `VrijemeIzdavanja` DATETIME NOT NULL,
  `SaGotovinom` TINYINT NOT NULL,
  `BrojNarudžbe` INT NOT NULL,
  PRIMARY KEY (`IdRačuna`),
  INDEX `fk_RAČUN_NARUDŽBA1_idx` (`BrojNarudžbe` ASC) VISIBLE,
  CONSTRAINT `fk_RAČUN_NARUDŽBA1`
    FOREIGN KEY (`BrojNarudžbe`)
    REFERENCES `restoran`.`NARUDŽBA` (`BrojNarudžbe`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`OCJENE_JELA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`OCJENE_JELA` (
  `IdOcjene` INT NOT NULL,
  `Datum` DATE NULL,
  `Ocjena` INT NULL,
  `IdJela` INT NOT NULL,
  PRIMARY KEY (`IdOcjene`),
  INDEX `fk_OCJENE_JELA_JELO1_idx` (`IdJela` ASC) VISIBLE,
  CONSTRAINT `fk_OCJENE_JELA_JELO1`
    FOREIGN KEY (`IdJela`)
    REFERENCES `restoran`.`JELO` (`IdJela`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`RECENZIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`RECENZIJA` (
  `IdRecenzije` INT NOT NULL,
  `Ocjena` INT NOT NULL,
  `Datum` DATE NOT NULL,
  `Komentar` VARCHAR(200) NOT NULL,
  `IdRestorana` INT NOT NULL,
  PRIMARY KEY (`IdRecenzije`),
  INDEX `fk_RECENZIJA_RESTORAN1_idx` (`IdRestorana` ASC) VISIBLE,
  CONSTRAINT `fk_RECENZIJA_RESTORAN1`
    FOREIGN KEY (`IdRestorana`)
    REFERENCES `restoran`.`RESTORAN` (`IdRestorana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restoran`.`TELEFON_ZAPOSLENI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restoran`.`TELEFON_ZAPOSLENI` (
  `BrojTelefona` VARCHAR(20) NOT NULL,
  `JMBZaposlenog` CHAR(13) NOT NULL,
  PRIMARY KEY (`BrojTelefona`, `JMBZaposlenog`),
  INDEX `fk_TELEFON_ZAPOSLENI_ZAPOSLENI1_idx` (`JMBZaposlenog` ASC) VISIBLE,
  UNIQUE INDEX `BrojTelefona_UNIQUE` (`BrojTelefona` ASC) VISIBLE,
  CONSTRAINT `fk_TELEFON_ZAPOSLENI_ZAPOSLENI1`
    FOREIGN KEY (`JMBZaposlenog`)
    REFERENCES `restoran`.`ZAPOSLENI` (`JMB`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
