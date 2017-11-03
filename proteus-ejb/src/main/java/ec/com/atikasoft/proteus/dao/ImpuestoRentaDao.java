/*
 *  ImpuestoRentaDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ImpuestoRenta;
import ec.com.atikasoft.proteus.vo.EmpleadoRdepVO;
import ec.com.atikasoft.proteus.vo.InfoSriVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class ImpuestoRentaDao extends GenericDAO<ImpuestoRenta, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ImpuestoRentaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ImpuestoRentaDao() {
        super(ImpuestoRenta.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ImpuestoRenta que esten
     * vigentes true.
     *
     * @return Listado ImpuestoRenta
     * @throws DaoException En caso de error
     */
    public List<ImpuestoRenta> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ImpuestoRenta.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ImpuestoRentaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param ejercicioFiscalId
     * @param valor
     * @return
     * @throws DaoException
     */
    public ImpuestoRenta buscarPorEscala(final Long ejercicioFiscalId, final BigDecimal valor) throws DaoException {
        try {
            ImpuestoRenta entidad = null;
            List<ImpuestoRenta> lista = buscarPorConsultaNombrada(ImpuestoRenta.BUSCAR_ESCALA, ejercicioFiscalId, valor);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
        /**
     *
     * @param ejercicioFiscalId
     * @return
     * @throws DaoException
     */
    public ImpuestoRenta buscarFraccionBasicaPorExoneracion(final Long ejercicioFiscalId) throws DaoException {
        try {
            ImpuestoRenta entidad = null;
            List<ImpuestoRenta> lista = buscarPorConsultaNombrada(ImpuestoRenta.BUSCAR_FRACCION_BASICA_EXONERACIONES, ejercicioFiscalId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }


    /**
     * Buscar por ejercicio fiscal
     * @param idEjercicioFiscal
     * @return
     * @throws DaoException 
     */
    public List<ImpuestoRenta> listarPorEjercicioFiscal(final Long idEjercicioFiscal) throws DaoException {
        List<ImpuestoRenta> lista = buscarPorConsultaNombrada(ImpuestoRenta.BUSCAR_EJERCICIO_FISCAL, idEjercicioFiscal);
        if(lista == null){
            lista = new ArrayList<ImpuestoRenta>();
        }
        return lista;
    }
    
    
    /**
     * Obtiene los datos de la base de datos para generar el xml de RDEP
     * @param ruc
     * @param mes
     * @param ejercicioFiscalId
     * @return
     * @throws DaoException 
     */
    public List<InfoSriVO> obtenerDatosSRI(final String ruc, final Long ejercicioFiscalId, 
            final Integer mes) throws
            DaoException {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append("  i.ruc as NUMRUC , ");
            sql.append("  ef.nombre as ANIO, ");
            sql.append("  s.tipo_identificacion as TIPIDRET, ");
            sql.append("  s.numero_identificacion as IDRET, ");
            sql.append("  REPLACE(s.apellidos, CHAR(13) + CHAR(10) , ' ' ) as APELLIDOTRAB, ");
            sql.append("  REPLACE(s.nombres, CHAR(13) + CHAR(10) , ' ' ) as NOMBRETRAB, ");
            sql.append("  '001' as ESTAB, ");
            sql.append("  '01' as RESIDENCIATRAB, ");
            sql.append("  '593' as PAISRESIDENCIA, ");
            sql.append("  'NA' as APLICACONVENIO, ");
            sql.append("  case when s.porcentaje_discapacidad > 40 then '02' else '01' end as TIPOTRABAJDISCAP, ");
            sql.append("  case when s.porcentaje_discapacidad is null then 0 else s.porcentaje_discapacidad end ")
               .append("      as PORCENTAJEDISCAP, ");
            sql.append("  'N' as TIPIDDISCPA, ");
            sql.append("  '999' as IDDISCAP, ");
            sql.append("  sum(case when ri.es_rmu=1 then nd.valor_descontado_rubro_ingreso else 0 end) as SUELSUAL, ");
            sql.append("  sum(case when ri.gravable=1 and nd.valor_descontado_rubro_ingreso is not null then ")
               .append("                nd.valor_descontado_rubro_ingreso else 0 end ");
            sql.append("        - case when ri.gravable=1 and nd.valor_descontado_rubro_descuentos is not null then ")
               .append("                nd.valor_descontado_rubro_descuentos else 0 end ");
            sql.append("        - case when ri.es_rmu=1 then nd.valor_descontado_rubro_ingreso else 0 end) ")
               .append("      as SOBSUELCOMREMU, ");
            sql.append("  '0' as PARTUTIL, ");
            sql.append("  '0' as INTGRABGEN, ");
            sql.append("  '0' as IMPRENTEMPL, ");
            sql.append("  sum(case when ri.es_decimo_tercero=1 and nd.valor_descontado_rubro_ingreso is not null then ")
               .append("                nd.valor_descontado_rubro_ingreso else 0 end ");
            sql.append("        - case when ri.es_decimo_tercero=1 and nd.valor_descontado_rubro_descuentos ")
               .append("                is not null then nd.valor_descontado_rubro_descuentos else 0 end) ")
               .append("     as DECIMTER, ");
            sql.append("  sum(case when ri.es_decimo_cuarto=1 and nd.valor_descontado_rubro_ingreso is not null then ")
               .append("                nd.valor_descontado_rubro_ingreso else 0 end ");
            sql.append("        - case when ri.es_decimo_cuarto=1 and nd.valor_descontado_rubro_descuentos ")
               .append("               is not null then nd.valor_descontado_rubro_descuentos else 0 end) ")
               .append("     as DECIMCUAR, ");
            sql.append("  sum(case when ri.es_fondos_reserva=1 and nd.valor_descontado_rubro_ingreso is not null then ")
               .append("                nd.valor_descontado_rubro_ingreso else 0 end ");
            sql.append("        - case when ri.es_fondos_reserva=1 and nd.valor_descontado_rubro_descuentos ")
               .append("               is not null then nd.valor_descontado_rubro_descuentos else 0 end) ")
               .append("     as FONDORESERVA, ");
            sql.append("  '0' as SALARIODIGNO, ");
            sql.append("  sum(coalesce (gp.ingresos_otro_empleador,0)) as  OTROSINGRENGRAV, ");
            sql.append("  0 as INGGRAVCONESTEEMPL, ");
            sql.append("  '1' as SISSALNET, ");
            sql.append("  sum(case when rd.codigo in ('D36','D33') then nd.valor_descontado_rubro_descuentos ")
               .append("                else 0 end) as APOPERIESS, ");
            sql.append("  '0' as APORPERIESSCONOTROSEMPLS, ");
            sql.append("  sum(coalesce (round(gp.vivienda").append(mes!=null?"/12":"").append(",2),0))")
               .append("      as DEDUCVIVIENDA, ");
            sql.append("  sum(coalesce (round(gp.salud").append(mes!=null?"/12":"").append(",2),0)) ")
               .append("      as DEDUCSALUD, ");
            sql.append("  sum(coalesce (round(gp.educacion").append(mes!=null?"/12":"").append(",2),0)) ")
               .append("      as DEDUCEDUCA, ");
            sql.append("  sum(coalesce (round(gp.alimentacion").append(mes!=null?"/12":"").append(",2),0)) ")
               .append("      as DEDUCALIEMENT, ");
            sql.append("  sum(coalesce (round(gp.vestimenta").append(mes!=null?"/12":"").append(",2),0)) ")
               .append("      as DEDUCVESTIM, ");
            sql.append("  sum(coalesce (gp.exoneracion_discapacidad,0)) as EXODISCAP, ");
            sql.append("  sum(coalesce (gp.exoneracion_tercera_edad,0)) as EXOTERED, ");
            sql.append("  sum(case when ri.gravable=1 and nd.valor_descontado_rubro_ingreso is not null then ")
               .append("                nd.valor_descontado_rubro_ingreso else 0 end ");
            sql.append("        - case when rd.gravable=1 and nd.valor_descontado_rubro_descuentos is not null then ")
               .append("                    nd.valor_descontado_rubro_descuentos else 0 end)  ");
                                        /*Inicio Cambio*/
            sql.append("        - coalesce (round(gp.total_deducible").append(mes!=null?"/12":"").append(",2),0) ")
               .append("      as BASIMP, ");
            sql.append("  sum(case when rd.es_impuesto_renta=1 then nd.valor_descontado_rubro_descuentos else 0 end) ")
               .append("      as IMPUESTO_RENTA_CAUSADO,");
            sql.append("  '0' as VALOR_RETENIDO_OTROS_EMPLEADORES, ");
            sql.append("  '0' as VALOR_IMPUESTO_ASUMIDO_EMPLEADOS, ");
            sql.append("  sum(case when rd.es_impuesto_renta=1 then nd.valor_descontado_rubro_descuentos else 0 end) ")
               .append("      as VALOR_RETENIDO,");
            sql.append("  case when sum(case when rd.es_impuesto_renta=1 then nd.valor_descontado_rubro_descuentos ")
               .append("       else 0 end) > 0 then 'SI' else 'NO' end as TIPO_GENERA, ");
            sql.append("  i.nombre as NOMBRE_EMPLEADOR, ");
            sql.append("  '01' as PERIODO, ");
            sql.append("  rl.nombre as REGIMEN_LABORAL, ");
            sql.append("  up.nombre as UNIDAD_PRESUPUESTARIA ");
                                        /*Fin Cambio*/
            
            sql.append(" FROM sch_proteus.instituciones i");
            sql.append("   JOIN sch_proteus.instituciones_ejercicios_fiscales ief on ief.institucion_id = i.id ");
            sql.append("   JOIN sch_proteus.ejercicios_fiscales ef on ief.ejercicio_fiscal_id = ef.id ");
            sql.append("   JOIN sch_proteus.servidor_institucion si on si.institucion_id=i.id ");
            sql.append("   JOIN sch_proteus.servidor s on si.servidor_id=s.id ");
            sql.append("   JOIN sch_proteus.nominas_detalle nd on nd.servidor_id = s.id ");
            sql.append("   LEFT JOIN sch_proteus.rubros ri on nd.rubro_ingreso_id = ri.id ");
            sql.append("   LEFT JOIN sch_proteus.rubros rd on nd.rubro_descuentos_id = rd.id ");
            sql.append("   JOIN sch_proteus.nominas n on nd.nomina_id = n.id ")
               .append("        and n.institucion_ejercicio_fiscal_id=ief.id ");
            sql.append("   JOIN sch_proteus.periodos_nominas pn on n.periodo_nomina_id = pn.id ");
            sql.append("   LEFT JOIN  sch_proteus.gastos_personales gp on gp.servidor_id=s.id ")
               .append("              and gp.institucion_ejercicio_fiscal_id=ief.id ");
            ////////////////////////////
            sql.append("   JOIN sch_proteus.tipos_nominas tn on n.tipo_nomina_id = tn.id ");
            sql.append("   JOIN sch_proteus.regimenes_laborales rl on tn.regimen_laboral_id = rl.id ");
            sql.append("   JOIN sch_proteus.distributivo_detalles dd on nd.distributivo_detalle_id = dd.id ");
            sql.append("   JOIN sch_proteus.unidades_presupuestarias up on dd.unidad_presupuestaria_id = up.id ");
            //////////////////////////
            sql.append(" WHERE           ");
            sql.append("        i.ruc ='").append(ruc).append("'");
            sql.append("        and ef.id = ").append(ejercicioFiscalId);

            if(mes!=null){
                sql.append("        and pn.numero = '").append(mes).append("'");
            }
            
            sql.append(" GROUP BY        ")
               .append("            i.ruc,ef.nombre,s.tipo_identificacion,s.numero_identificacion,s.apellidos, ")
               .append("            s.nombres,s.porcentaje_discapacidad,s.apellidos_nombres,gp.total_deducible, ")
            ///////////
               .append("            i.nombre,rl.nombre,up.nombre ")
            //////////
               .append(" ORDER BY        ")
               .append("            s.apellidos_nombres");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<InfoSriVO> registros = new ArrayList<>();

            for (Object[] o : lista) {
                InfoSriVO b = new InfoSriVO();
                EmpleadoRdepVO e = new EmpleadoRdepVO();
                b.setRuc(o[0] == null ? null : o[0].toString());
                b.setAnio(o[1] == null ? null : o[1].toString());
                
                e.setTipoIdentificacion(o[2] == null ? null : o[2].toString());
                e.setNumeroIdentificacion(o[3] == null ? null : o[3].toString());
                e.setApellidos(o[4] == null ? null : o[4].toString());
                e.setNombres(o[5] == null ? null : o[5].toString());
                e.setEstab(o[6] == null ? null : o[6].toString());
                e.setResidencia(o[7] == null ? null : o[7].toString());
                e.setPaisResidencia(o[8] == null ? null : o[8].toString());
                e.setAplicaConvenio(o[9] == null ? null : o[9].toString());
                e.setTipoDiscapacidad(o[10] == null ? null : o[10].toString());
                e.setPorcientoDiscapacidad(o[11] == null ? null : o[11].toString());
                e.setTipoIdDiscapacidad(o[12] == null ? null : o[12].toString());
                e.setNumeroIdDiscapacidad(o[13] == null ? null : o[13].toString());
                
                b.setEmpleado(e);
                b.setSuelsual(o[14] == null ? null : o[14].toString());
                b.setSobsuelcomremu(o[15] == null ? null : o[15].toString());
                b.setPartutil(o[16] == null ? null : o[16].toString());
                b.setIntgrabgen(o[17] == null ? null : o[17].toString());
                b.setImprentempl(o[18] == null ? null : o[18].toString());
                b.setDecimoTercero(o[19] == null ? null : o[19].toString());
                b.setDecimoCuarto(o[20] == null ? null : o[20].toString());
                b.setFondoReserva(o[21] == null ? null : o[21].toString());
                b.setSalarioDigno(o[22] == null ? null : o[22].toString());
                b.setOtrosIngresosGravables(o[23] == null ? null : o[23].toString());
                b.setInggravconesteempl(o[24] == null ? null : o[24].toString());
                b.setSissalnet(o[25] == null ? null : o[25].toString());
                b.setApoperiess(o[26] == null ? null : o[26].toString());
                b.setAporperiessconotrosempls(o[27] == null ? null : o[27].toString());
                b.setDeducibleVivienda(o[28] == null ? null : o[28].toString());
                b.setDeducibleSalud(o[29] == null ? null : o[29].toString());
                b.setDeducibleEducacion(o[30] == null ? null : o[30].toString());
                b.setDeducibleAlimentacion(o[31] == null ? null : o[31].toString());
                b.setDeducibleVestimenta(o[32] == null ? null : o[32].toString());
                b.setExoneracionDiscapacidad(o[33] == null ? null : o[33].toString());
                b.setExoneracionTerceraEdad(o[34] == null ? null : o[34].toString());
                b.setBaseImponible(o[35] == null ? null : o[35].toString());
                b.setImpuestoRentaCausado(o[36] == null ? null : o[36].toString());
                b.setValorRetenidoOtrosEmpleadores(o[37] == null ? null : o[37].toString());
                b.setValorImpuestoAsumidoEmpleados(o[38] == null ? null : o[38].toString());
                b.setValorRetenido(o[39] == null ? null : o[39].toString());
                b.setTipoGenera(o[40] == null ? null : o[40].toString());
                b.setNombreEmpleador(o[41] == null ? null : o[41].toString());
                b.setPeriodo(o[42] == null ? null : o[42].toString());
                b.setRegimenLaboral(o[43] == null ? null : o[43].toString());
                b.setUnidadPresupuestaria(o[44] == null ? null : o[44].toString());

                registros.add(b);
            }
            return registros;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
