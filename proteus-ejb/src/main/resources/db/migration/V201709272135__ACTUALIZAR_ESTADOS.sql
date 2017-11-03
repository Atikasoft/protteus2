update sch_proteus.distributivo_detalles set estado_puesto_id=2  where servidor_id is null and estado_puesto_id =1
GO
update sch_proteus.distributivo_detalles set estado_puesto_id=1  where servidor_id is not null and estado_puesto_id =2
GO



