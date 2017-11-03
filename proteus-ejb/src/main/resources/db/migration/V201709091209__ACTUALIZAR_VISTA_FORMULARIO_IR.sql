DROP VIEW sch_proteus.vista_formularioIR
GO
CREATE VIEW
    sch_proteus.vista_formularioIR
    (
        "ejercicio_fiscal",
        "ejercicio_fiscal_id",
        "institucion_ruc",
        "institucion_nombre",
        "institucion_ejercicio_fiscal_id",
        "unidad_organizacional",
        "codigo_unidad_organizacional",
        "tipo_identificacion",
        "numero_identificacion",
        "nombres",
        "servidor_id",
        "aporte_personal",
        "sueldos_salarios",
        "otros_ingresos",
        "fondo_reserva",
        "impuesto_renta",
        "decimo_tercero",
        "decimo_cuarto",
        "ingresos_otro_empleador",
        "vivienda",
        "alimentacion",
        "vestimenta",
        "educacion",
        "salud",
        "exoneracion_discapacidad",
        "exoneracion_tercera_edad"
    ) AS
SELECT
    ef.nombre AS ejercicio_fiscal,
    ef.id     AS ejercicio_fiscal_id,
    i.ruc     AS institucion_ruc,
    i.nombre  AS institucion_nombre,
    ief.id    AS institucion_ejercicio_fiscal_id,
    uo.ruta   AS unidad_organizacional,
    uo.codigo AS codigo_unidad_organizacional,
    nd.tipo_identificacion,
    nd.numero_identificacion,
    nd.nombres,
    nd.servidor_id,
    -- APORTE PERSONAL
    (
        SELECT ISNULL(SUM(ndd.valor_descontado_rubro_descuentos),0) - ISNULL(SUM(ndd.valor_descontado_rubro_ingreso),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE es_aporte_personal_iess=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_aporte_personal_iess=1) 
            )
        GROUP BY ndd.servidor_id 
    ) aporte_personal,
    -- SUELDOS Y SALARIOS
    (
        SELECT ISNULL(SUM(valor_descontado_rubro_ingreso),0) - ISNULL(SUM(valor_descontado_rubro_descuentos),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE es_rmu=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_rmu=1) 
            )
        GROUP BY ndd.servidor_id 
    ) sueldos_salarios,
    -- OTROS INGRESOS
     0 AS otros_ingresos,
    -- FONDO DE RESERVA
    (
        SELECT ISNULL(SUM(valor_descontado_rubro_ingreso),0) - ISNULL(SUM(valor_descontado_rubro_descuentos),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE es_fondos_reserva=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_fondos_reserva=1) 
            )
        GROUP BY ndd.servidor_id 
     ) fondo_reserva,
     -- IMPUESTO RENTA
     (
        SELECT ISNULL(SUM(valor_descontado_rubro_descuentos),0) - ISNULL(SUM(ndd.valor_descontado_rubro_ingreso),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE rubros.es_impuesto_renta=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_impuesto_renta=1) 
            )
        GROUP BY ndd.servidor_id 
     ) impuesto_renta,
     -- DECIMO TERCERO
     (
        SELECT ISNULL(SUM(valor_descontado_rubro_ingreso),0) - ISNULL(SUM(valor_descontado_rubro_descuentos),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE rubros.es_decimo_tercero=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_decimo_tercero=1) 
            )
        GROUP BY ndd.servidor_id 
     ) decimo_tercero,
     -- DECIMO CUARTO
     (
        SELECT ISNULL(SUM(valor_descontado_rubro_ingreso),0) - ISNULL(SUM(valor_descontado_rubro_descuentos),0)
        FROM
            sch_proteus.nominas_detalle ndd ,
            sch_proteus.nominas nn
        WHERE
            ndd.nomina_id=nn.id
        AND nn.institucion_ejercicio_fiscal_id = ef.id
        AND ndd.servidor_id=nd.servidor_id
        AND ( ndd.rubro_ingreso_id IN (SELECT id FROM sch_proteus.rubros WHERE rubros.es_decimo_cuarto=1 )
              OR  
              ndd.rubro_descuentos_id IN (SELECT id FROM sch_proteus.rubros WHERE es_decimo_cuarto=1) 
            )
        GROUP BY ndd.servidor_id 
     ) decimo_cuarto,
    -- INGRESOS OTROS EMPLEADORES
    ISNULL(gp.ingresos_otro_empleador,0) - ISNULL(gp.iess_personal_otro_empleador,0)  ingresos_otro_empleador,
    gp.vivienda,
    gp.alimentacion,
    gp.vestimenta,
    gp.educacion,
    gp.salud,
    gp.exoneracion_discapacidad,
    gp.exoneracion_tercera_edad
 FROM sch_proteus.nominas_detalle nd
 JOIN sch_proteus.nominas n ON nd.nomina_id=n.id AND nd. vigente=1  AND n.vigente=1
 JOIN sch_proteus.instituciones_ejercicios_fiscales ief ON  n.institucion_ejercicio_fiscal_id=ief.id
 JOIN sch_proteus.ejercicios_fiscales ef ON ief.ejercicio_fiscal_id=ef.id
 JOIN sch_proteus.instituciones i ON ief.institucion_id=i.id
 LEFT JOIN sch_proteus.gastos_personales gp ON gp.servidor_id = nd.servidor_id AND gp.institucion_ejercicio_fiscal_id = ief.id AND gp.vigente =1
 LEFT JOIN sch_proteus.distributivo_detalles dd ON nd.servidor_id=dd.servidor_id AND nd.servidor_id IS NOT NULL
 LEFT JOIN sch_proteus.distributivo d ON dd.distributivo_id=d.id
 LEFT JOIN sch_proteus.unidades_organizacionales uo ON d.unidad_organizacional_id=uo.id
GROUP BY
    ef.nombre,
    ef.id,
    i.ruc,
    i.nombre,
    ief.id,
    nd.tipo_identificacion,
    nd.numero_identificacion,
    nd.nombres,
    nd.servidor_id,
    gp.ingresos_otro_empleador,
    gp.vivienda,
    gp.alimentacion,
    gp.vestimenta,
    gp.educacion,
    gp.salud,
    gp.exoneracion_discapacidad,
    gp.exoneracion_tercera_edad,
    gp.iess_personal_otro_empleador,
    uo.ruta,
    uo.codigo
GO
