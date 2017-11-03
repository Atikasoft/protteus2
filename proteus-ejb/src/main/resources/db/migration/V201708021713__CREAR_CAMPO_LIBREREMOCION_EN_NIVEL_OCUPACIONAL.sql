ALTER TABLE sch_proteus.niveles_ocupacionales ADD es_libre_remocion bit;
UPDATE  sch_proteus.niveles_ocupacionales SET es_libre_remocion=0;
ALTER TABLE sch_proteus.niveles_ocupacionales ALTER COLUMN es_libre_remocion bit NOT NULL;

