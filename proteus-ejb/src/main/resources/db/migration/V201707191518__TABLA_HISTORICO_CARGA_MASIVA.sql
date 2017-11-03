CREATE TABLE sch_proteus.cargas_masivas_puestos
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    institucion_ejercicio_fiscal_id NUMERIC(19,0) NOT NULL,
    puestos TEXT COLLATE Modern_Spanish_CI_AS NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT cargas_masivas_puestos_pk PRIMARY KEY (id),
    CONSTRAINT cargas_masivas_puestos_institucion_ejercicio_fiscal_id_fk 
               FOREIGN KEY (institucion_ejercicio_fiscal_id) 
               REFERENCES sch_proteus.instituciones_ejercicios_fiscales ("id")          
);
