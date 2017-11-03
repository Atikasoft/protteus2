
update sch_proteus.tareas set asignado=0 where asignado=1 and identificador_externo in (
select ID from sch_proteus.tramites where codigo_fase='REG'
) 
GO

