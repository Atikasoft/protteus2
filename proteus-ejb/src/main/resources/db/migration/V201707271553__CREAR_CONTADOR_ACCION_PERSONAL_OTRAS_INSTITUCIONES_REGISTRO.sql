ALTER TABLE  sch_proteus.instituciones ADD contador_accion_personal_otras_instituciones_registro NUMERIC(19,0),
                                           contador_accion_personal_otras_instituciones_terminacion NUMERIC(19,0);

UPDATE sch_proteus.instituciones SET contador_accion_personal_otras_instituciones_registro=0,
                                     contador_accion_personal_otras_instituciones_terminacion=0;

ALTER TABLE sch_proteus.instituciones ALTER COLUMN contador_accion_personal_otras_instituciones_registro NUMERIC(19,0) NOT NULL;
ALTER TABLE sch_proteus.instituciones ALTER COLUMN contador_accion_personal_otras_instituciones_terminacion NUMERIC(19,0) NOT NULL;
