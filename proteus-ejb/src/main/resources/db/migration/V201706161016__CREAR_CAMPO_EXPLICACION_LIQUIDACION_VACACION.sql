ALTER TABLE sch_proteus.regimenes_laborales ADD explicacion_liquidacion_vacacion VARCHAR(500);
update sch_proteus.regimenes_laborales set explicacion_liquidacion_vacacion='LA DIRECCIÓN METROPOLITANA DE RECURSOS HUMANOS, LUEGO DE LA REVISIÓN DE LOS REGISTROS CORRESPONDIENTES, TIENE A BIEN CERTIFICAR QUE EL MENCIONADO SERVIDOR/A LABORO EN ESTA INSTITUCIÓN DESDE EL #fecha_inicio AL #fecha_final; Y SU SALDO DE VACACIONES A LIQUIDAR SON #saldo_vacacion';
ALTER TABLE sch_proteus.regimenes_laborales ALTER COLUMN explicacion_liquidacion_vacacion VARCHAR(500) COLLATE Modern_Spanish_CI_AS NOT NULL;





