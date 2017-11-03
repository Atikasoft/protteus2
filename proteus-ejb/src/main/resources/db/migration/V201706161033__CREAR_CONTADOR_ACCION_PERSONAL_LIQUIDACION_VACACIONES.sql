ALTER TABLE  sch_proteus.instituciones ADD contador_accion_personal_liquidacion_vacaciones NUMERIC(19,0);
UPDATE sch_proteus.instituciones SET contador_accion_personal_liquidacion_vacaciones=0;
ALTER TABLE sch_proteus.instituciones ALTER COLUMN contador_accion_personal_liquidacion_vacaciones NUMERIC(19,0) NOT NULL;
