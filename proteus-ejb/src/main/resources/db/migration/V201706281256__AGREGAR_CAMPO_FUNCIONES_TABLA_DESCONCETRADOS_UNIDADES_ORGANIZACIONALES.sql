/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 28, 2017
 */

IF COL_LENGTH('sch_proteus.desconcentrados_unidades_organizacionales', 'funciones') IS NULL
BEGIN
    ALTER TABLE sch_proteus.desconcentrados_unidades_organizacionales
    ADD funciones VARCHAR(100) COLLATE Modern_Spanish_CI_AS NOT NULL
END

IF (OBJECT_ID('desconcentrado_unidad_organizacional_uk', 'UQ') IS NOT NULL)
BEGIN
    ALTER TABLE sch_proteus.desconcentrados_unidades_organizacionales_funciones 
    DROP CONSTRAINT desconcentrado_unidad_organizacional_uk
END
        
IF (OBJECT_ID('desconcentrado_unidad_organizacional_funciones_uk', 'UQ') IS NOT NULL)
BEGIN
ALTER TABLE sch_proteus.desconcentrados_unidades_organizacionales 
    ADD CONSTRAINT desconcentrado_unidad_organizacional_funciones_uk 
        UNIQUE (desconcentrado_id, unidad_organizacional_id, funciones)
END