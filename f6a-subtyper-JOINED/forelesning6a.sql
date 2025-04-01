--
-- Eksempel f6a forelesning onsdag 2. april 2025.
--
-- Alternativ a: Subtyping med tabeller både for supertypen og 
-- subtypene, dvs. @Inheritance(strategy = InheritanceType.JOINED)
--

DROP SCHEMA IF EXISTS forelesning6a CASCADE;
CREATE SCHEMA forelesning6a;
SET search_path TO forelesning6a;

CREATE TABLE person
(
  fnr        CHAR(11), -- Kan også bruke UNSIGNED BIGINT(11). 
                       -- INTEGER er for liten for 11 sifre.
  fornavn    VARCHAR(30),
  etternavn  VARCHAR(30),
  dtype      VARCHAR(30),  -- NB! En reservert kolonne brukt av JPA ved arv.
                           -- Brukes for å markere hvilken (sub)klasse det er.
  CONSTRAINT person_pk PRIMARY KEY (fnr)
);

CREATE TABLE ansatt
(
  fnr        CHAR(11),
  stilling   VARCHAR(30),
  CONSTRAINT ansatt_pk PRIMARY KEY (fnr),
  CONSTRAINT person_fk FOREIGN KEY (fnr) REFERENCES person(fnr)
);

CREATE TABLE student
(
  fnr        CHAR(11),
  studium    VARCHAR(30),
  CONSTRAINT student_pk PRIMARY KEY (fnr),
  CONSTRAINT person_fk FOREIGN KEY (fnr) REFERENCES person(fnr)
);

INSERT INTO 
    person(fnr, fornavn, etternavn, dtype)
VALUES
    ('12345678901', 'Arne', 'Arnesen', 'Ansatt'),
    ('23456789012', 'Stig', 'Stigsen', 'Student'),
    ('34567890123', 'Pers', 'Perssen', 'Person');
    
INSERT INTO 
    ansatt(fnr, stilling)
VALUES
    ('12345678901', 'Lærer');
    
INSERT INTO 
    student(fnr, studium)
VALUES
    ('23456789012', 'Informasjonsteknologi');
    




