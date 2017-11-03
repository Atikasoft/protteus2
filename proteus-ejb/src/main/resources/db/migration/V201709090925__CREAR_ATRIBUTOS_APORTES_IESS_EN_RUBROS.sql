ALTER TABLE sch_proteus.rubros ADD es_aporte_personal_iess bit DEFAULT 0;
ALTER TABLE sch_proteus.rubros ADD es_aporte_patronal_iess bit DEFAULT 0;
UPDATE sch_proteus.rubros SET es_aporte_personal_iess=0;
UPDATE sch_proteus.rubros SET es_aporte_patronal_iess=0;
ALTER TABLE sch_proteus.rubros ALTER COLUMN es_aporte_personal_iess bit NOT NULL;
ALTER TABLE sch_proteus.rubros ALTER COLUMN es_aporte_patronal_iess bit NOT NULL;

