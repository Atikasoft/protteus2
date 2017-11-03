ALTER TABLE [sch_proteus].[proceso_registro_migracion_marcaciones]
    ADD [usuario_creacion]               	varchar(40) NOT NULL,
        [usuario_actualizacion]               	varchar(40) NULL,
        [fecha_creacion]                 	datetime NOT NULL,
        [fecha_actualizacion]                 	datetime NULL,
        [vigente]                        	bit NOT NULL DEFAULT ((1))	
GO

ALTER TABLE [sch_proteus].[marcaciones_migradas]
    ADD [usuario_creacion]               	varchar(40) NOT NULL,
        [usuario_actualizacion]               	varchar(40) NULL,
        [fecha_creacion]                 	datetime NOT NULL,
        [fecha_actualizacion]                 	datetime NULL,
        [vigente]                        	bit NOT NULL DEFAULT ((1))	
GO


