/*
 *  AsistenciaServicio.java
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
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.BeneficiarioEspecialDao;
import ec.com.atikasoft.proteus.dao.BeneficiarioInstitucionalDao;
import ec.com.atikasoft.proteus.dao.CuentaBancariaDao;
import ec.com.atikasoft.proteus.dao.DenominacionPuestoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.EstadoPersonalDao;
import ec.com.atikasoft.proteus.dao.EstadoPuestoDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.UnidadPresupuestariaDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class MigracionDistributivoServicio extends BaseServicio {

    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    @EJB
    private ModalidadLaboralDao modalidadLaboralDao;

    @EJB
    private DenominacionPuestoDao denominacionPuestoDao;

    @EJB
    private EstadoPersonalDao estadoPersonalDao;

    @EJB
    private EstadoPuestoDao estadoPuestoDao;

    @EJB
    private EscalaOcupacionalDao escalaOcupacionalDao;

    @EJB
    private ServidorDao servidorDao;

    @EJB
    private DistributivoDao distributivoDao;

    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    @EJB
    private UnidadPresupuestariaDao unidadPresupuestariaDao;

    @EJB
    private BeneficiarioInstitucionalDao beneficiarioInstitucionalDao;

    @EJB
    private BeneficiarioEspecialDao beneficiarioEspecialDao;

    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    public void registrarCuentasBancarias() throws IOException, DaoException {
        List<String> errores = new ArrayList<String>();
        String dir = "/home/henry/Dropbox/Proyectos/municipio/";
        File file = new File(dir + "INFORMACION BANCARIA CIVIL ENERO 2014.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
        for (Long fila : datos.keySet()) {
            System.out.println(fila + ":" + datos.size());
            List<String> dato = datos.get(fila);
            ec.com.atikasoft.proteus.modelo.Servidor s = servidorDao.buscar("C", dato.get(0).trim());
            if (s == null) {
                errores.add("fila:" + fila + "==>Servidor no existe :" + dato.get(0));
            } else {
                CuentaBancaria cb = new CuentaBancaria();
                cb.setBancoId(Long.valueOf(dato.get(4)));
                cb.setFechaCreacion(new Date());
                cb.setNumerCuenta(dato.get(2));
                cb.setPagoNomina(Boolean.TRUE);
                cb.setServidorId(s.getId());
                cb.setTipoCuenta(dato.get(3));
                cb.setUsuarioCreacion("atk");
                cb.setVigente(Boolean.TRUE);
                cuentaBancariaDao.crear(cb);
            }
        }
        PrintWriter writer = new PrintWriter(dir + "errores-cuentas-bancarias.txt", "UTF-8");
        writer.println("ERRORES:" + errores.size());
        for (String e : errores) {
            writer.println(e);
        }
        writer.close();

    }

    public void registrarRmus() throws IOException, DaoException {
        String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/distributivo/";
        File file = new File(dir + "DISTRIBUTIVO-2014-05-06.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
        System.out.print("TOTAL REGISTROS:" + datos.size());
        for (Long fila : datos.keySet()) {
            System.out.print(fila + ":" + datos.size());
            List<String> dato = datos.get(fila);
            String cedula = (String) dato.get(0);
            if (cedula.length() == 9) {
                cedula = "0" + cedula;
            }
            DistributivoDetalle dd = distributivoDetalleDao.buscarPorServidor("C", cedula, 1l);
            if (dd != null) {
                BigDecimal rmu = new BigDecimal((String) dato.get(14));
                dd.setRmu(rmu);
                dd.setRmuOriginal(rmu);
                if (rmu.compareTo(dd.getEscalaOcupacional().getRmu()) == 1) {
                    dd.setRmuSobrevalorado(rmu);
                    distributivoDetalleDao.actualizar(dd);
                }
            } else {
                System.out.println("no existe :" + cedula);
            }
        }


    }

    public void generarPartidasIndividuales() throws DaoException {
        List<Distributivo> distributivos = distributivoDao.buscarVigente();
        for (Distributivo d : distributivos) {
            int pi = 5;
            List<DistributivoDetalle> dds = distributivoDetalleDao.buscarPorDistributivo(d.getId());
            for (DistributivoDetalle dd : dds) {
                if (dd.getVigente()) {
                    dd.setPartidaIndividual(String.valueOf(pi));
                    distributivoDetalleDao.actualizar(dd);
                    distributivoDetalleDao.flush();
                    pi = pi + 5;
                }
            }
        }
    }

    public void conciliacionDistributivo() throws IOException, DaoException, ParseException {
        List<String> errores = new ArrayList<String>();
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
        String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/nomina-abr/";
        File file = new File(dir + "ROL_ABRIL_2014_SIGEN.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 80);
        System.out.print("TOTAL REGISTROS:" + datos.size());
        int totalJornal = 0;
        int totalCivil = 0;
        for (Long fila : datos.keySet()) {
            System.out.print(fila + ":" + datos.size());
            List<String> dato = datos.get(fila);
            if ("2".equals(dato.get(5))) {
                totalJornal++;
            } else {
                totalCivil++;
                String cedula = dato.get(0).trim();
                DistributivoDetalle dd = distributivoDetalleDao.buscarPorServidor("C", cedula, 1l);
                if (dd == null) {
                    errores.add("fila:" + fila + "==>Servidor no existe :" + cedula);
                } else {
                    dd.setUsuarioActualizacion("99");
                    distributivoDetalleDao.actualizar(dd);
                    distributivoDetalleDao.flush();
                }
            }
        }
        errores.add("total jornal :" + totalJornal);
        errores.add("total civil :" + totalCivil);
        PrintWriter writer = new PrintWriter(dir + "errores-nomina-abr.txt", "UTF-8");
        writer.println("ERRORES:" + errores.size());
        for (String e : errores) {
            writer.println(e);
        }
        writer.close();

    }

    /**
     *
     * @throws IOException
     * @throws DaoException
     * @throws ParseException
     */
    public void migrarJubilados() throws IOException, DaoException, ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
        List<String> errores = new ArrayList<String>();
        String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/jubilados/";
        File file = new File(dir + "JUBILADOS.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
        System.out.print("TOTAL REGISTROS:" + datos.size());
        for (Long fila : datos.keySet()) {
            List<String> dato = datos.get(fila);
            ec.com.atikasoft.proteus.modelo.Servidor servidor = servidorDao.buscar("C", dato.get(0));
            if (servidor == null) {
                Date fechaIngreso = formato.parse("01/01/2014");
                servidor = new ec.com.atikasoft.proteus.modelo.Servidor();
                servidor.setTipoIdentificacion("C");
                servidor.setNumeroIdentificacion(dato.get(0));
                servidor.setApellidos(dato.get(1));
                servidor.setNombres("");
                servidor.setApellidosNombres(dato.get(1));
                servidor.setEstadoPersonalId(21l);
                servidor.setFechaCreacion(new Date());
                servidor.setUsuarioCreacion("atk");
                servidor.setVigente(Boolean.TRUE);
                servidor.setFechaIngresoInstitucion(fechaIngreso);
                servidor.setFechaIngresoSectorPublico(fechaIngreso);
                servidor.setRecibeFondoReserva(Boolean.TRUE);
                servidor = servidorDao.crear(servidor);
                servidorDao.flush();
            } else {
                servidor.setNombres(dato.get(1));
                servidor.setApellidosNombres(dato.get(1));
                servidorDao.actualizar(servidor);
                servidorDao.flush();
            }
            Distributivo distributivo = distributivoDao.buscar("3.1.2.11", "3.1", 1l);
            if (distributivo == null) {
                distributivo = new Distributivo();
                distributivo.setContadorPartida(0l);
                distributivo.setFechaCreacion(new Date());
                distributivo.setIdInstitucionEjercicioFiscal(1l);
                distributivo.setIdModalidadLaboral(27L);
                distributivo.setIdUnidadOrganizacional(323540L);
                distributivo.setNombre("");
                distributivo.setTotalDetalles(0l);
                distributivo.setUsuarioCreacion("atk");
                distributivo.setFechaCreacion(new Date());
                distributivo.setVigente(Boolean.TRUE);
                distributivo = distributivoDao.crear(distributivo);
                distributivoDao.flush();
            }
            distributivo.setContadorPartida(distributivo.getContadorPartida() + 5);
            distributivoDao.actualizar(distributivo);
            distributivoDao.flush();


            DistributivoDetalle dd = new DistributivoDetalle();
            dd.setIdDistributivo(distributivo.getId());
            dd.setIdDenominacionPuesto(38L);
            dd.setIdEscalaOcupacional(3192l);
            dd.setIdEstadoPuesto(9l);
            dd.setIdUbicacionGeografica(208L);
            dd.setPartidaIndividual(String.valueOf(distributivo.getContadorPartida()));
            dd.setFechaCreacion(new Date());
            dd.setSueldoBasico(BigDecimal.ZERO);
            dd.setRmuEscala(new BigDecimal("22.67"));
            dd.setRmuOriginal(new BigDecimal("22.67"));
            dd.setRmuSobrevalorado(BigDecimal.ZERO);
            dd.setUsuarioCreacion("atk");
            dd.setVigente(Boolean.TRUE);
            Date fechaInicio = formato.parse("01/01/2014");
            Date fechaFin = formato.parse("31/12/2014");
            dd.setFechaFin(fechaFin);
            dd.setFechaInicio(fechaInicio);
            dd.setCodigoPuesto(fila + 10000);
            // pos 30 fecha ingreso
            if (servidor != null) {
                Date fechaIngreso = formato.parse("01/03/2014");
                dd.setFechaIngreso(fechaIngreso);
                dd.setIdServidor(servidor.getId());
            }
            distributivoDetalleDao.crear(dd);
            distributivoDetalleDao.flush();
        }
    }

    /**
     *
     * @throws IOException
     * @throws DaoException
     */
    public void migrarRetenciones() throws IOException, DaoException {
        List<String> errores = new ArrayList<String>();
        Long rubroRetencionId = 72l;
        Long rubroSubFamiliarId = 73l;
        Long rubroSubEscolarId = 74l;

        String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/";
        File file = new File(dir + "RETENCIONES-JUDICIALES.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
        System.out.print("TOTAL REGISTROS:" + datos.size());
        for (Long fila : datos.keySet()) {
            System.out.print(fila + ":" + datos.size());
            List<String> dato = datos.get(fila);
            if (!dato.get(0).trim().isEmpty()) {
                // buscar servidor
                ec.com.atikasoft.proteus.modelo.Servidor s = servidorDao.buscar("C", dato.get(0));
                if (s == null) {
                    errores.add(
                            "fila:" + fila + "==>Servidor con identificación :" + dato.get(0) + " no existe en distributivo");
                } else {
                    try {
                        BigDecimal valorRetencion = BigDecimal.ZERO;
                        BigDecimal valorFamiliar = BigDecimal.ZERO;
                        BigDecimal valorEscolar = BigDecimal.ZERO;
                        if (!dato.get(1).trim().isEmpty()) {
                            valorRetencion = new BigDecimal(dato.get(1));
                        }
                        if (!dato.get(2).trim().isEmpty()) {
                            valorFamiliar = new BigDecimal(dato.get(2));
                        }
                        if (!dato.get(3).trim().isEmpty()) {
                            valorEscolar = new BigDecimal(dato.get(3));
                        }
                        String iden = "9999999999";
                        if (!dato.get(4).trim().isEmpty()) {
                            iden = dato.get(4);
                        }
                        if (iden.trim().length() == 10) {
                            // grabar
                            if (valorRetencion.compareTo(BigDecimal.ZERO) == 1) {
                                BeneficiarioInstitucional bi = new BeneficiarioInstitucional();
                                bi.setFechaCreacion(new Date());
                                bi.setIdInstitucion(1l);
                                bi.setIdRubro(rubroRetencionId);
                                bi.setIdServidor(s.getId());
                                bi.setUsuarioCreacion("atk");
                                bi.setVigente(Boolean.TRUE);
                                beneficiarioInstitucionalDao.crear(bi);
                                beneficiarioInstitucionalDao.flush();
                                BeneficiarioEspecial be = new BeneficiarioEspecial();
                                be.setFechaCreacion(new Date());
                                be.setFechaInicio(UtilFechas.formatear("01/01/2014"));
                                be.setFechaFin(UtilFechas.formatear("31/12/2014"));
                                be.setIdBeneficiarioInstitucion(bi.getId());
                                be.setNombreBeneficiario(dato.get(5));
                                be.setNumeroIdentificacion(iden);
                                be.setTipoIdentificacion("C");
                                be.setUsuarioCreacion("atk");
                                be.setValor(valorRetencion);
                                be.setVigente(Boolean.TRUE);
                                beneficiarioEspecialDao.crear(be);
                                beneficiarioEspecialDao.flush();
                            }
                            if (valorFamiliar.compareTo(BigDecimal.ZERO) == 1) {
                                BeneficiarioInstitucional bi = new BeneficiarioInstitucional();
                                bi.setFechaCreacion(new Date());
                                bi.setIdInstitucion(1l);
                                bi.setIdRubro(rubroSubFamiliarId);
                                bi.setIdServidor(s.getId());
                                bi.setUsuarioCreacion("atk");
                                bi.setVigente(Boolean.TRUE);
                                beneficiarioInstitucionalDao.crear(bi);
                                beneficiarioInstitucionalDao.flush();
                                BeneficiarioEspecial be = new BeneficiarioEspecial();
                                be.setFechaCreacion(new Date());
                                be.setFechaInicio(UtilFechas.formatear("01/01/2014"));
                                be.setFechaFin(UtilFechas.formatear("31/12/2014"));
                                be.setIdBeneficiarioInstitucion(bi.getId());
                                be.setNombreBeneficiario(dato.get(5));
                                be.setNumeroIdentificacion(iden);
                                be.setTipoIdentificacion("C");
                                be.setUsuarioCreacion("atk");
                                be.setValor(valorFamiliar);
                                be.setVigente(Boolean.TRUE);
                                beneficiarioEspecialDao.crear(be);
                                beneficiarioEspecialDao.flush();
                            }
                            if (valorEscolar.compareTo(BigDecimal.ZERO) == 1) {
                                BeneficiarioInstitucional bi = new BeneficiarioInstitucional();
                                bi.setFechaCreacion(new Date());
                                bi.setIdInstitucion(1l);
                                bi.setIdRubro(rubroSubEscolarId);
                                bi.setIdServidor(s.getId());
                                bi.setUsuarioCreacion("atk");
                                bi.setVigente(Boolean.TRUE);
                                beneficiarioInstitucionalDao.crear(bi);
                                beneficiarioInstitucionalDao.flush();
                                BeneficiarioEspecial be = new BeneficiarioEspecial();
                                be.setFechaCreacion(new Date());
                                be.setFechaInicio(UtilFechas.formatear("01/01/2014"));
                                be.setFechaFin(UtilFechas.formatear("31/12/2014"));
                                be.setIdBeneficiarioInstitucion(bi.getId());
                                be.setNombreBeneficiario(dato.get(5));
                                be.setNumeroIdentificacion(iden);
                                be.setTipoIdentificacion("C");
                                be.setUsuarioCreacion("atk");
                                be.setValor(valorEscolar);
                                be.setVigente(Boolean.TRUE);
                                beneficiarioEspecialDao.crear(be);
                                beneficiarioEspecialDao.flush();
                            }

                        } else {
                            errores.add("fila:" + fila + "==>Identificacion del beneficiario no tiene 10 dígitos :" + dato.
                                    get(4));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errores.add("fila:" + fila + "==>Error:" + dato.get(1) + "::" + e.getMessage());
                    }
                }
            }
        }
        PrintWriter writer = new PrintWriter(dir + "errores-retenciones.txt", "UTF-8");
        writer.println("ERRORES:" + errores.size());
        for (String e : errores) {
            writer.println(e);
        }
        writer.close();
    }

    public void migrarUnidadesPresupuestarias() throws IOException, DaoException {
        String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/";
        File file = new File(dir + "UNIDADES-PRESUPUESTARIAS-EMPLEADOS-FEB2014.csv");
        byte[] archivo = UtilArchivos.getBytesFromFile(file);
        Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
        int total = 0;
        System.out.print("TOTAL REGISTROS:" + datos.size());
        for (Long fila : datos.keySet()) {
            List<String> dato = datos.get(fila);
            UnidadPresupuestaria up = unidadPresupuestariaDao.buscar(dato.get(2).substring(0, 1), dato.get(2).substring(
                    1));
            ec.com.atikasoft.proteus.modelo.Servidor s = servidorDao.buscar(dato.get(0), dato.get(1));
            List<DistributivoDetalle> puestos = distributivoDetalleDao.buscarPorServidor(s.getId());
            for (DistributivoDetalle puesto : puestos) {
                System.out.print("FILA :" + fila);
                puesto.setUnidadPresupuestaria(up);
                distributivoDetalleDao.actualizar(puesto);
            }
        }
        System.out.print("TOTAL REGISTROS:" + total);
    }

    public void migrarDistrinutivo() throws ServicioException {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
            Map<String, Servidor> servidores = new HashMap<String, Servidor>();
            Map<String, Escala> escalas = new HashMap<String, Escala>();
            String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/";
            List<String> errores = new ArrayList<String>();
            File file = new File(dir + "NUEVOS-MARZO.csv");
            byte[] archivo = UtilArchivos.getBytesFromFile(file);
            Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 40);
            System.out.print("TOTAL REGISTROS:" + datos.size());
            long codigo = 1;
            for (Long fila : datos.keySet()) {
                List<String> error = new ArrayList<String>();
                System.out.print("FILA :" + fila);
                List<String> dato = datos.get(fila);
                Servidor s = null;
                EscalaOcupacional eo = null;
                ModalidadLaboral ml = null;
                UnidadOrganizacional uo = null;
                DenominacionPuesto dp = null;
                EstadoPuesto ep = null;
                EstadoPersonal ee = null;
                //------------------------------------------------------------------------//
                String cedula = dato.get(0).trim().length() == 9 ? "0" + dato.get(0).trim() : dato.get(0).trim();
                //System.out.print("cedula :" + cedula);
                ec.com.atikasoft.proteus.modelo.Servidor ser = servidorDao.buscar("C", cedula);
//                    error.add("fila:" + fila + ",Servidor NO EXISTE :" + cedula);
                // 4 modalidad, 
                String codML = dato.get(3);
                List<ModalidadLaboral> mls = modalidadLaboralDao.buscarTodosPorCodigo(codML);
                if (mls.isEmpty()) {
                    error.add("fila:" + fila + ",modalidad no existe :" + codML);
                } else {
                    ml = mls.get(0);
                }
                // 31 unidad
                String codUO = dato.get(4);
                List<UnidadOrganizacional> uos = unidadOrganizacionalDao.buscarPorNemonico(codUO);
                if (uos.isEmpty()) {
                    error.add("fila:" + fila + ",uo no existe :" + codUO);
                } else {
                    uo = uos.get(0);
                }
                // 8 grupo ocupacional.
                List<DenominacionPuesto> dns = denominacionPuestoDao.buscarPorNemonico(dato.get(5));
                if (dns.isEmpty()) {
                    error.add("fila:" + fila + ",grupo ocupacional no existe :" + dato.get(5));
                } else {
                    dp = dns.get(0);
                }
                // 13 estado del puesto.
                List<EstadoPuesto> eps = estadoPuestoDao.buscarPorNemonico(dato.get(6).trim());
                if (eps.isEmpty()) {
                    error.add("fila:" + fila + ",estado puesto no existe :" + dato.get(6));
                } else {
                    ep = eps.get(0);
                }
                // 14 estado personal
                if (!dato.get(7).trim().isEmpty()) {
                    List<EstadoPersonal> es = estadoPersonalDao.buscarPorNemonico(dato.get(7));
                    if (es.isEmpty()) {
                        error.add("fila:" + fila + ",estado prsonal no existe :" + dato.get(7));
                    } else {
                        ee = es.get(0);
                    }
                }
                // 6
                List<EscalaOcupacional> eos = escalaOcupacionalDao.buscarTodosPorCodigo(dato.get(8));
                if (eos.isEmpty()) {
                    error.add("fila:" + fila + ",eo existe :" + dato.get(8));
                } else {
                    eo = eos.get(0);
                }

                if (ser == null && cedula.trim().length() == 10) {
                    // validar servidores duplicados.
                    // tipo 1, numero 2, apellidos 3, nombres 4
                    if (!dato.get(0).trim().isEmpty()) {
                        if (servidores.containsKey(dato.get(0))) {
                            error.add("fila:" + fila + ",servidor duplicado :" + dato.get(0) + " - " + dato.get(1) + " " + dato.
                                    get(2));
                        } else {
                            s = new Servidor("C", cedula, dato.get(1), dato.get(2));
                            servidores.put(dato.get(0), s);
                        }
                    }
//                try {
//                    // validar rmu escalas.
//                    // 9,16,7,28 escala, nivel, regimen, rmu
//                    if (escalas.containsKey(dato.get(7) + dato.get(16) + dato.get(9))) {
//                        Escala escala = escalas.get(dato.get(7) + dato.get(16) + dato.get(9));
//                        if (!(escala.getRmu().compareTo(new BigDecimal(dato.get(28))) == 0)) {
//                            error.add("fila:" + fila + ",escala con valor diferente duplicado :" + dato.get(7) + " - " + dato.
//                                    get(16) + " - " + dato.get(9) + " - " + dato.get(28));
//                        }
//                    } else {
//                        escalas.put(dato.get(7) + dato.get(16) + dato.get(9), new Escala(dato.get(7), dato.get(16),
//                                dato.get(9), new BigDecimal(dato.get(28))));
//
//                    }
//                } catch (Exception e) {
//                    error.add("fila:" + fila + ",escala con valor error :" + dato.get(7) + " - " + dato.get(16) + " - " + dato.
//                            get(9) + " - " + dato.get(28));
//
//                }

                    //------------------------------------------------------------------------//
                    if (error.isEmpty()) {
                        // registros.
//                        eo.setRmu(new BigDecimal(dato.get(33)));
//                        escalaOcupacionalDao.actualizar(eo);
//                        escalaOcupacionalDao.flush();


                        // registro de distributivo.
                        ec.com.atikasoft.proteus.modelo.Servidor servidor = null;
                        if (s != null) {
                            Date fechaIngreso = formato.parse("01/03/2014");
                            servidor = new ec.com.atikasoft.proteus.modelo.Servidor();
                            servidor.setTipoIdentificacion(s.getTipo());
                            servidor.setNumeroIdentificacion(s.getNumero());
                            servidor.setApellidos(s.getApellidos());
                            servidor.setNombres(s.getNombres());
                            servidor.setApellidosNombres(s.getApellidos() + " " + s.getNombres());
                            servidor.setEstadoPersonalId(ee.getId());
                            servidor.setFechaCreacion(new Date());
                            servidor.setUsuarioCreacion("atk");
                            servidor.setVigente(Boolean.TRUE);
                            servidor.setFechaIngresoInstitucion(fechaIngreso);
                            servidor.setFechaIngresoSectorPublico(fechaIngreso);
                            servidor.setRecibeFondoReserva(Boolean.TRUE);
                            servidor = servidorDao.crear(servidor);
                            servidorDao.flush();
                            System.out.println("empleado nuevo:" + s.getNumero());
                        }

                        Distributivo distributivo = distributivoDao.buscar(codUO, codML, 1l);
                        if (distributivo == null) {
                            distributivo = new Distributivo();
                            distributivo.setContadorPartida(0l);
                            distributivo.setFechaCreacion(new Date());
                            distributivo.setIdInstitucionEjercicioFiscal(1l);
                            distributivo.setIdModalidadLaboral(ml.getId());
                            distributivo.setIdUnidadOrganizacional(uo.getId());
                            distributivo.setNombre("");
                            distributivo.setTotalDetalles(0l);
                            distributivo.setUsuarioCreacion("atk");
                            distributivo.setFechaCreacion(new Date());
                            distributivo.setVigente(Boolean.TRUE);
                            distributivo = distributivoDao.crear(distributivo);
                            distributivoDao.flush();
                            System.out.println("distributivo nuevo:" + ml.getCodigo() + ":" + uo.getCodigo());
                        }
                        distributivo.setContadorPartida(distributivo.getContadorPartida() + 5);
                        distributivoDao.actualizar(distributivo);
                        distributivoDao.flush();

                        DistributivoDetalle dd = new DistributivoDetalle();
                        dd.setIdDistributivo(distributivo.getId());
                        dd.setIdDenominacionPuesto(dp.getId());
                        dd.setIdEscalaOcupacional(eo.getId());
                        dd.setIdEstadoPuesto(ep.getId());
                        dd.setIdUbicacionGeografica(208L);
                        dd.setPartidaIndividual(String.valueOf(distributivo.getContadorPartida()));
                        dd.setFechaCreacion(new Date());
                        dd.setSueldoBasico(BigDecimal.ZERO);
                        dd.setRmuEscala(eo.getRmu());
                        dd.setRmuOriginal(eo.getRmu());
                        dd.setRmuSobrevalorado(BigDecimal.ZERO);
                        dd.setUsuarioCreacion("atk");
                        dd.setVigente(Boolean.TRUE);
                        Date fechaInicio = formato.parse("01/01/2014");
                        Date fechaFin = formato.parse("31/12/2014");
                        dd.setFechaFin(fechaFin);
                        dd.setFechaInicio(fechaInicio);
                        dd.setCodigoPuesto(codigo++);
                        // pos 30 fecha ingreso
                        if (s != null) {
                            Date fechaIngreso = formato.parse("01/03/2014");
                            dd.setFechaIngreso(fechaIngreso);
                            dd.setIdServidor(servidor.getId());
                        }
                        distributivoDetalleDao.crear(dd);
                        distributivoDetalleDao.flush();
                        System.out.println("distributivo detalle nuevo:" + servidor.getNumeroIdentificacion());
                    } else {
                        errores.addAll(error);
                    }
                } else {
                    // servidor ya existe.
                    if (ser != null) {
                        List<DistributivoDetalle> lista = distributivoDetalleDao.buscarPorServidor(ser.getId());
                        // verificar si existe en el distributivo.
                        if (lista.isEmpty()) {
                            System.out.println("empleado  sin dist:" + ser.getNumeroIdentificacion());
                            // crear distributivo.
                            Distributivo distributivo = distributivoDao.buscar(codUO, codML, 1l);
                            if (distributivo == null) {
                                distributivo = new Distributivo();
                                distributivo.setContadorPartida(0l);
                                distributivo.setFechaCreacion(new Date());
                                distributivo.setIdInstitucionEjercicioFiscal(1l);
                                distributivo.setIdModalidadLaboral(ml.getId());
                                distributivo.setIdUnidadOrganizacional(uo.getId());
                                distributivo.setNombre("");
                                distributivo.setTotalDetalles(0l);
                                distributivo.setUsuarioCreacion("atk");
                                distributivo.setFechaCreacion(new Date());
                                distributivo.setVigente(Boolean.TRUE);
                                distributivo = distributivoDao.crear(distributivo);
                                distributivoDao.flush();
                            }
                            distributivo.setContadorPartida(distributivo.getContadorPartida() + 5);
                            distributivoDao.actualizar(distributivo);
                            distributivoDao.flush();

                            DistributivoDetalle dd = new DistributivoDetalle();
                            dd.setIdDistributivo(distributivo.getId());
                            dd.setIdDenominacionPuesto(dp.getId());
                            dd.setIdEscalaOcupacional(eo.getId());
                            dd.setIdEstadoPuesto(ep.getId());
                            dd.setIdUbicacionGeografica(208L);
                            dd.setPartidaIndividual(String.valueOf(distributivo.getContadorPartida()));
                            dd.setFechaCreacion(new Date());
                            dd.setSueldoBasico(BigDecimal.ZERO);
                            dd.setRmuEscala(eo.getRmu());
                            dd.setRmuOriginal(eo.getRmu());
                            dd.setRmuSobrevalorado(BigDecimal.ZERO);
                            dd.setUsuarioCreacion("atk");
                            dd.setVigente(Boolean.TRUE);
                            Date fechaInicio = formato.parse("01/01/2014");
                            Date fechaFin = formato.parse("31/12/2014");
                            dd.setFechaFin(fechaFin);
                            dd.setFechaInicio(fechaInicio);
                            dd.setCodigoPuesto(codigo++);
                            if (ser != null) {
                                Date fechaIngreso = formato.parse("01/03/2014");
                                dd.setFechaIngreso(fechaIngreso);
                                dd.setIdServidor(ser.getId());
                            }
                            distributivoDetalleDao.crear(dd);
                            distributivoDetalleDao.flush();
                        }
                    }
                }
            }
            PrintWriter writer = new PrintWriter(dir + "errores.txt", "UTF-8");
            writer.println("ERRORES:" + errores.size());
            for (String e : errores) {
                writer.println(e);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Escala {

        private String regimen;

        private String nivel;

        private String escala;

        private BigDecimal rmu;

        public Escala(String regimen, String nivel, String escala, BigDecimal rmu) {
            this.regimen = regimen;
            this.nivel = nivel;
            this.escala = escala;
            this.rmu = rmu;
        }

        /**
         * @return the regimen
         */
        public String getRegimen() {
            return regimen;
        }

        /**
         * @return the nivel
         */
        public String getNivel() {
            return nivel;
        }

        /**
         * @return the escala
         */
        public String getEscala() {
            return escala;
        }

        /**
         * @return the rmu
         */
        public BigDecimal getRmu() {
            return rmu;
        }

        /**
         * @param regimen the regimen to set
         */
        public void setRegimen(String regimen) {
            this.regimen = regimen;
        }

        /**
         * @param nivel the nivel to set
         */
        public void setNivel(String nivel) {
            this.nivel = nivel;
        }

        /**
         * @param escala the escala to set
         */
        public void setEscala(String escala) {
            this.escala = escala;
        }

        /**
         * @param rmu the rmu to set
         */
        public void setRmu(BigDecimal rmu) {
            this.rmu = rmu;
        }
    }

    private class Servidor {

        private String tipo;

        private String numero;

        private String apellidos;

        private String nombres;

        public Servidor(String tipo, String numero, String apellidos, String nombres) {
            this.tipo = tipo;
            this.numero = numero;
            this.apellidos = apellidos;
            this.nombres = nombres;
        }

        /**
         * @return the tipo
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * @return the numero
         */
        public String getNumero() {
            return numero;
        }

        /**
         * @return the apellidos
         */
        public String getApellidos() {
            return apellidos;
        }

        /**
         * @return the nombres
         */
        public String getNombres() {
            return nombres;
        }

        /**
         * @param tipo the tipo to set
         */
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        /**
         * @param numero the numero to set
         */
        public void setNumero(String numero) {
            this.numero = numero;
        }

        /**
         * @param apellidos the apellidos to set
         */
        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        /**
         * @param nombres the nombres to set
         */
        public void setNombres(String nombres) {
            this.nombres = nombres;
        }
    }
}
