/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 20, 2017
 */

CREATE TABLE sch_proteus.vacaciones_solicitud_liquidacion 
    (
        id NUMERIC(19,0) NOT NULL IDENTITY, 
        servidor_institucion_id NUMERIC(19,0) NOT NULL,
        servidor_inst_elaborador_id NUMERIC(19,0) NOT NULL,
        regimen_laboral_id NUMERIC(19,0) NOT NULL,
        unidad_organizacional_id NUMERIC(19,0) NOT NULL,
        ultimo_puesto_ocupado_id NUMERIC(19,0) NOT NULL,
        rmu NUMERIC(16,2) NOT NULL,
        saldo_vacaciones_efectivas BIGINT NOT NULL DEFAULT 0, 
        saldo_vacaciones_proporcionales BIGINT NOT NULL DEFAULT 0,
        fecha_inicio DATE NOT NULL, 
        fecha_fin DATE NOT NULL,
        
        explicacion_liquidacion_vacacion VARCHAR(500) COLLATE Modern_Spanish_CI_AS NOT NULL,
        codigo_documento_previo VARCHAR(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
        numero_documento_previo VARCHAR(20) COLLATE Modern_Spanish_CI_AS NOT NULL,
        fecha_documento_previo DATE NOT NULL,
        numero_accion_personal VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
        archivo_accion_personal_id NUMERIC(19,0),

        fecha_creacion DATETIME NOT NULL, 
        usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL, 
        fecha_actualizacion DATETIME, 
        usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS, 
        vigente BIT DEFAULT 0 NOT NULL, 
        
        CONSTRAINT vacaciones_solicitud_liquidacion_pk PRIMARY KEY (id), 
        CONSTRAINT vacaciones_solicitud_liquidacion_serv_instit_id_fk FOREIGN KEY (servidor_institucion_id) REFERENCES sch_proteus.servidor_institucion ("id"), 
        CONSTRAINT vacaciones_solicitud_liquidacion_serv_inst_elab_id_fk FOREIGN KEY (servidor_inst_elaborador_id) REFERENCES sch_proteus.servidor_institucion ("id"), 
        CONSTRAINT vacaciones_solicitud_liquidacion_regimen_laboral_id_fk FOREIGN KEY (regimen_laboral_id) REFERENCES sch_proteus.regimenes_laborales ("id"),
        CONSTRAINT vacaciones_solicitud_liquidacion_unidad_organizacional_id_fk FOREIGN KEY (unidad_organizacional_id) REFERENCES sch_proteus.unidades_organizacionales ("id"),
        CONSTRAINT vacaciones_solicitud_liquidacion_ultimo_puesto_ocupado_id_fk FOREIGN KEY (ultimo_puesto_ocupado_id) REFERENCES sch_proteus.distributivo_detalles ("id"),
        CONSTRAINT vacaciones_solicitud_liquidacion_archivo_accion_personal_id_fk FOREIGN KEY (archivo_accion_personal_id) REFERENCES sch_proteus.archivos ("id")
    );