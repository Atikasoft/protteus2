CREATE TABLE [sch_proteus].[proceso_registro_migracion_marcaciones] ( 
    [id]                             	numeric(19,0) IDENTITY NOT NULL,
    [fecha]                          	datetime NOT NULL,
    [hora_inicio]                       datetime NOT NULL,
    [hora_fin]                          datetime NOT NULL,
    [fecha_desde]                    	datetime NOT NULL,
    [fecha_hasta]                    	datetime NOT NULL,
    [proceso_registro]               	bit NOT NULL DEFAULT ((0)),
    [proceso_seleccion_marcadas]     	bit NOT NULL DEFAULT ((0)),
    [proceso_determinacion_atrasos]  	bit NOT NULL DEFAULT ((0)),
    [proceso_determinacion_horas_ext]	bit NOT NULL DEFAULT ((0)),
    [ejecucion_exito]                	bit NOT NULL DEFAULT ((0)),
    [errores]                        	varchar(2000) NULL,
    CONSTRAINT [proceso_registro_migracion_marcaciones_pk] PRIMARY KEY([id])
)
GO

CREATE TABLE [sch_proteus].[marcaciones_migradas] ( 
    [id]                                       	numeric(19,0) IDENTITY NOT NULL,
    [codigo_empleado]                          	varchar(30) NOT NULL,
    [cedula]                                   	varchar(30) NOT NULL,
    [nombre]                                   	varchar(100) NOT NULL,
    [dia]                                      	varchar(15) NOT NULL,
    [fecha]                                    	datetime NOT NULL,
    [e1]                                       	varchar(30) NULL,
    [m1]                                       	datetime NULL,
    [e2]                                       	varchar(30) NULL,
    [m2]                                       	datetime NULL,
    [e3]                                       	varchar(30) NULL,
    [m3]                                       	datetime NULL,
    [e4]                                       	varchar(30) NULL,
    [m4]                                       	datetime NULL,
    [e5]                                       	varchar(30) NULL,
    [m5]                                       	datetime NULL,
    [e6]                                       	varchar(30) NULL,
    [m6]                                       	datetime NULL,
    [e7]                                       	varchar(30) NULL,
    [m7]                                       	datetime NULL,
    [e8]                                       	varchar(30) NULL,
    [m8]                                       	datetime NULL,
    [e9]                                       	varchar(30) NULL,
    [m9]                                       	datetime NULL,
    [e10]                                      	varchar(30) NULL,
    [m10]                                      	datetime NULL,
    [proceso_registro_migracion_marcaciones_id]	numeric(19,0) NOT NULL,
    CONSTRAINT [marcaciones_migradas_pk] PRIMARY KEY([id])
)
GO


ALTER TABLE [sch_proteus].[marcaciones_migradas]
    ADD CONSTRAINT [proceso_registro_migracion_marcaciones_fk]
	FOREIGN KEY([proceso_registro_migracion_marcaciones_id])
	REFERENCES [sch_proteus].[proceso_registro_migracion_marcaciones]([id])
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION 
GO
