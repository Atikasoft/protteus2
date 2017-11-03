ALTER TABLE sch_proteus.vacaciones_solicitudes ADD cantidad_tomada_minutos INT;
UPDATE sch_proteus.vacaciones_solicitudes SET cantidad_tomada_minutos=0;
ALTER TABLE sch_proteus.vacaciones_solicitudes ALTER COLUMN cantidad_tomada_minutos INT NOT NULL;

