ALTER TABLE sch_proteus.vacaciones ADD saldo_mes_actual bigint;
ALTER TABLE sch_proteus.vacaciones ADD mes_actual INT;
update sch_proteus.vacaciones set saldo_mes_actual=0, mes_actual=0;
ALTER TABLE sch_proteus.vacaciones ALTER COLUMN saldo_mes_actual bigint NOT NULL;
ALTER TABLE sch_proteus.vacaciones ALTER COLUMN mes_actual INT NOT NULL;