update sch_proteus.distributivo_detalles set certificacion_presupuestaria='#####'
where certificacion_presupuestaria is not null and len(certificacion_presupuestaria)>0
;

update sch_proteus.distributivo_detalles set distributivo_detalles.grupo_presupuestario='##'
;
