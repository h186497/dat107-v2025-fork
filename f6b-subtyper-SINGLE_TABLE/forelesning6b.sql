--
-- Eksempel f6b forelesning onsdag 2. april 2025.
--
-- Alternativ b: Subtyping med tabell kun for supertypen,
-- dvs. @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
--

DROP SCHEMA IF EXISTS forelesning6b CASCADE;
CREATE SCHEMA forelesning6b;
SET search_path TO forelesning6b;

CREATE TABLE person
(
  fnr        CHAR(11), -- Kan også bruke UNSIGNED BIGINT(11). 
                       -- INTEGER er for liten for 11 sifre.
  fornavn    VARCHAR(30),
  etternavn  VARCHAR(30),
  persontype CHAR(1),
  stilling   VARCHAR(30),
  studium    VARCHAR(30),
  CONSTRAINT person_pk PRIMARY KEY (fnr)
);

INSERT INTO 
    person(fnr, fornavn, etternavn, persontype, stilling, studium)
VALUES
    ('12345678901', 'Arne', 'Arnesen', 'A', 'Lærer', NULL),
    ('23456789012', 'Stig', 'Stigsen', 'S', NULL, 'Dataingeniør'),
    ('34567890123', 'Pers', 'Perssen', 'P', NULL, NULL);
    

