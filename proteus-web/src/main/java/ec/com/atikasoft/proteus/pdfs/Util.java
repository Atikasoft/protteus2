/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.pdfs;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
public class Util {

    private static final int CALENDAR_FONT_SIZE = 9;

    /**
     * Obtener las fechas agrupadas por meses
     *
     * @param vacacionSolicitud
     * @return
     */
    private static Map<String, List<Date>> obtenerMapaDeMeses(final VacacionSolicitud vacacionSolicitud) {
        Map<String, List<Date>> mapa = new HashMap<>();
        try {
            List<Date> fechas = null;

            if (vacacionSolicitud.getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
                fechas = UtilFechas.parsearLista(Arrays.asList(vacacionSolicitud.getDiasPlanificados().split(",")),
                        "dd/MM/yyyy");
            } else {
                fechas = new ArrayList<Date>();
                fechas.add(vacacionSolicitud.getFecha());
            }

            Calendar c = Calendar.getInstance();
            for (Date fecha : fechas) {
                c.setTime(fecha);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);
                String key = anio + "," + mes;
                List<Date> misFechas = null;
                if (mapa.containsKey(key)) {
                    misFechas = mapa.get(key);
                } else {
                    misFechas = new ArrayList<Date>();
                }
                misFechas.add(fecha);
                mapa.put(key, misFechas);
            }
        } catch (ParseException ex) {
            Logger.getLogger(GenerarSolicitudVacacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    /**
     * Grafica los calendarios que correspondan a las fechas de vacaciones
     *
     * @param vacacionSolicitud
     * @return
     * @throws DocumentException
     */
    public static PdfPTable dibujarCalendarios(final VacacionSolicitud vacacionSolicitud) throws DocumentException {
        Map<String, List<Date>> mapa = obtenerMapaDeMeses(vacacionSolicitud);

        PdfPTable tabla = new PdfPTable(5);
        tabla.getDefaultCell().setBorder(0);
        tabla.setWidths(new float[]{12.5f, 25f, 25f, 25f, 12.5f});
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.setWidthPercentage(100);

        int col = 0;
        for (Map.Entry<String, List<Date>> entry : mapa.entrySet()) {
            col++;
            if (col == 1) {
                tabla.addCell(escribirCelda("", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
                col++;
            }
            if (col == 5) {
                tabla.addCell(escribirCelda("", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
                col = 0;
                continue;
            }

            String[] key_ = entry.getKey().split(",");
            int anio = Integer.parseInt(key_[0]);
            int mes = Integer.parseInt(key_[1]);

            tabla.addCell(construirCalendario(anio, mes, entry.getValue()));
        }

        for (int i = col + 1; i <= 5; i++) {
            tabla.addCell(escribirCelda("", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        }

        return tabla;
    }

    /**
     * Constuye concretamente un calendario
     *
     * @param anio
     * @param mes
     * @param fechas
     * @return
     * @throws DocumentException
     */
    private static PdfPTable construirCalendario(int anio, int mes, List<Date> fechas) throws DocumentException {

        PdfPTable tablaCalendar = new PdfPTable(1);
        tablaCalendar.addCell(escribirCelda(UtilFechas.obtenerNombreMes(mes + 1) + " "
                + anio, CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));

        PdfPTable tabla = new PdfPTable(7);
        tabla.getDefaultCell().setBorder(0);
        tabla.setWidths(new float[]{16, 14, 14, 14, 14, 14, 14});
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.setWidthPercentage(90);

        construirCabeceraCalendario(tabla);
        construirCuerpoCalendario(tabla, anio, mes, fechas);

        tablaCalendar.addCell(tabla);

        return tablaCalendar;
    }

    /**
     * Contruye la linea de nombre de dias
     *
     * @param tabla
     * @throws DocumentException
     */
    private static void construirCabeceraCalendario(PdfPTable tabla) throws DocumentException {
        tabla.addCell(escribirCelda("D", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("L", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("M", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("M", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("J", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("V", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        tabla.addCell(escribirCelda("S", CALENDAR_FONT_SIZE, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
    }

    /**
     * Construye el cuerpo del calendario
     *
     * @param tabla
     * @param anio
     * @param mes
     * @param fechas
     * @throws DocumentException
     */
    private static void construirCuerpoCalendario(PdfPTable tabla, int anio, int mes, List<Date> fechas)
            throws DocumentException {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, anio);
        c.set(Calendar.DAY_OF_MONTH, 1);

        int diaFinMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        int diaW = c.get(Calendar.DAY_OF_WEEK);
        int iDia = 1;

        while (iDia < diaW) {
            tabla.addCell(escribirCelda("", 8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
            iDia++;
        }

        if (iDia > 7) {
            iDia = 1;
        }
        Calendar cD = Calendar.getInstance();
        for (int i = 1; i <= diaFinMes; i++) {
            boolean esta = false;
            for (Date d : fechas) {
                cD.setTime(d);
                if (cD.get(Calendar.DAY_OF_MONTH) == i) {
                    esta = true;
                    break;
                }
            }

            tabla.addCell(construirCeldaCalendario(i, esta));

            iDia++;
            if (iDia > 7) {
                iDia = 1;
            }
        }

        if (iDia != 7) {
            while (iDia <= 7) {
                tabla.addCell(escribirCelda("", 8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
                iDia++;
            }
        }
    }

    /**
     * Construye una celda que corresponde a 1 dia dentro del calendario
     *
     * @param dia
     * @param seleccionado
     * @return
     * @throws DocumentException
     */
    private static PdfPTable construirCeldaCalendario(int dia, boolean seleccionado) throws DocumentException {
        PdfPTable celda = new PdfPTable(1);
        celda.setWidths(new float[]{100f});
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setWidthPercentage(100);
        celda.getDefaultCell().setBorder(0);
        if (seleccionado) {
            celda.addCell(escribirCelda(dia + "", CALENDAR_FONT_SIZE, Font.BOLD,
                    Element.ALIGN_CENTER, Rectangle.BOX, BaseColor.RED));
        } else {
            celda.addCell(escribirCelda(dia + "", CALENDAR_FONT_SIZE, Font.NORMAL,
                    Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        }
        return celda;
    }

    /**
     *
     * @param texto
     * @param size
     * @param propiedadFont
     * @param propiedadElement
     * @param propiedadCelda
     * @param fontColor
     * @return
     * @throws DocumentException
     */
    private static PdfPCell escribirCelda(String texto, int size, int propiedadFont, int propiedadElement,
            int propiedadCelda, BaseColor fontColor) throws DocumentException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, propiedadFont, fontColor);
        Paragraph p = new Paragraph(texto, font);
        PdfPCell cell = new PdfPCell(p);
        p.setAlignment(propiedadElement);
        cell.setBorder(propiedadCelda);
        cell.setHorizontalAlignment(propiedadElement);
        return cell;
    }

    /**
     *
     * @param texto
     * @param size
     * @param propiedadFont
     * @param propiedadElement
     * @param propiedadCelda
     * @return
     * @throws DocumentException
     */
    private static PdfPCell escribirCelda(String texto, int size, int propiedadFont, int propiedadElement,
            int propiedadCelda) throws DocumentException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, propiedadFont);
        Paragraph p = new Paragraph(texto, font);
        PdfPCell cell = new PdfPCell(p);
        p.setAlignment(propiedadElement);
        cell.setBorder(propiedadCelda);
        cell.setHorizontalAlignment(propiedadElement);
        return cell;
    }

}
