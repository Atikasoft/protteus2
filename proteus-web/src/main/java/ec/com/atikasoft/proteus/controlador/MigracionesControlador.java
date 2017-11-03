/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import com.Ostermiller.util.CSVParser;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.servicio.MigracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador de Migración Vacaciones.
 *
 *
 */
@ManagedBean(name = "migracionesBean")
@ViewScoped
public class MigracionesControlador extends BaseControlador {

    /**
     *
     */
    @EJB
    private MigracionServicio migracionServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;

    /**
     *
     */
    @Override
    public void init() {

    }

    /**
     * Migracion Saldos
     */
    public void migracionSaldos() {
        try {
            mostrarMensajeEnPantalla("Migrando saldo de vacaciones...por favor espere", FacesMessage.SEVERITY_INFO);
            migrarVacaciones();
            mostrarMensajeEnPantalla("Migracion de saldos de vacaciones finalizo correctamente", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            error(getClass().getName(), "Se presento un error al realizar la migracion de saldo de vacaciones.", e);
        }
    }

    /**
     * Migracion Trayectoria
     */
    public void migracionTrayectoria() {
        try {
            mostrarMensajeEnPantalla("Migrando solicitudes de vacaciones en trayectoria laboral...por favor espere",
                    FacesMessage.SEVERITY_INFO);
            migrarSolicitudesVacaciones();
            mostrarMensajeEnPantalla("Migracion solicitudes de vacaciones en trayectoria laboral finalizo "
                    + "correctamente", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            error(getClass().getName(), "Se presento un error al realizar la migracion de solicitudes de vacaciones en"
                    + " trayectoria laboral.", e);
        }

    }

    /**
     *
     */
    private void migrarSolicitudesVacaciones() {
        OutputStream log = null;
        InputStream in = null;
        try {
            File dir = new File("/root/migracion/trayectoria");
            for (File a : dir.listFiles()) {
                String csv = a.getCanonicalPath();
                String csvLog = a.getCanonicalPath() + ".LOG";
                log = new FileOutputStream(csvLog);
                in = new FileInputStream(csv);
                CSVParser parser = new CSVParser(in);
                parser.changeDelimiter(';');
                migracionServicio.logger("", log, "INICIANDO MIGRACION TRAYECTORIA LABORAL DE VACACIONES");
                int fila = 1;
                String r[] = parser.getLine();
                while (r != null && r.length > 0) {
                    System.out.println(csv + ":" + fila);
                    fila++;
                    Servidor s = null;
                    TrayectoriaLaboral tl = new TrayectoriaLaboral();
                    boolean registro = true;
                    String cedula;
                    if (r[1].trim().length() == 9) {
                        cedula = "0" + r[1];
                    } else {
                        cedula = r[1];
                    }
                    if (r[1].trim().isEmpty()) {
                        registro = false;
                        migracionServicio.logger(cedula, log, "fila;", Integer.toString(fila), ";col;2 ;",
                                "falta numero identificacion;", r[1], ";", r[10]);
                    } else {

                        s = servidorDao.buscar("C", cedula);
                        if (s == null) {
                            registro = false;
                            migracionServicio.logger(cedula, log, "fila;", Integer.toString(fila), ";col;2 ;",
                                    "No existe servidor con " + "numero identificacion;", r[1], ";", r[10]);

                        } else {
                            if (r[7].trim().isEmpty()) {
                                registro = false;
                                migracionServicio.logger(cedula, log, "fila;", Integer.toString(fila), ";col;7 ;",
                                        "falta fecha de " + "inicio;", r[1], ";", r[10]);
                            }
                            if (r[8].trim().isEmpty()) {
                                registro = false;
                                migracionServicio.logger(cedula, log, "fila;", Integer.toString(fila), ";col;8 ;",
                                        "falta fecha fin;", r[1], ";", r[10]);
                            }
                        }
                    }
                    if (registro) {
                        migracionServicio.migrarSolicitudVacacionTrayectoriaIndividual(tl, r, s);
                    }
                    r = parser.getLine();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, e);
            try {
                migracionServicio.logger("xxxx", log, "Error:", e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                log.close();
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     *
     */
    public void migrarVacaciones() {
        File dir = new File("/root/migracion/saldos");
        for (File a : dir.listFiles()) {
            if (!a.getName().contains(".LOG")) {
                OutputStream log = null;
                InputStream in = null;
                try {
                    String csv = a.getCanonicalPath();
                    String csvLog = csv + ".LOG";
                    log = new FileOutputStream(csvLog);
                    in = new FileInputStream(csv);
                    CSVParser parser = new CSVParser(in);
                    parser.changeDelimiter(';');
                    migracionServicio.logger(null, log, "INICIANDO MIGRACION VACACIONES");
                    String[][] datos = parser.getAllValues();
                    List<String> cedulas = new ArrayList<String>();

                    int fila = 1;
                    for (String[] r : datos) {
                        // numero identificacion
                        String cedula;
                        if (r[0].trim().length() == 9) {
                            cedula = "0" + r[0];
                        } else {
                            cedula = r[0];
                        }
                        System.out.println("cedula:" + cedula + ",fila:" + fila);
                        if (cedulas.contains(cedula)) {
                            migracionServicio.logger(cedula, log, "fila:", Integer.toString(fila), ",col:1 ;",
                                    "Identificacion repetida:", r[0], ";", r[4]);
                        } else {
                            cedulas.add(cedula);
                            Servidor s = servidorDao.buscar("C", cedula);
                            if (s == null) {
                                migracionServicio.logger(cedula, log, "fila:", Integer.toString(fila), ",col:1 ;",
                                        "Identificacion no existe en servidores:", r[0], ";", r[4]);
                            } else {
                                migracionServicio.migrarVacacionesIndividual(fila, cedula, r, s, log);
                            }
                        }
                        fila++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, e);
                    try {
                        migracionServicio.logger("xxxx", log, "Error:", e.getMessage());
                    } catch (IOException ex) {
                        Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    log.close();
                    in.close();

                } catch (IOException ex) {
                    Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            vacacionDao.totalizarSaldosVacaciones();
        } catch (DaoException ex) {
            Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
