/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.pdfs;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henry
 */
public class GenerarSolicitudAnticipoVacacion {

    /**
     *
     */
    private final VacacionSolicitud vacacionSolicitud;

    /**
     *
     */
    private final byte[] logo;

    /**
     *
     */
    private final VacacionServicio vacacionServicio;

    /**
     *
     * @param vacacionSolicitud
     * @param logo
     */
    public GenerarSolicitudAnticipoVacacion(VacacionSolicitud vacacionSolicitud, final byte[] logo,
            final VacacionServicio vacacionServicio) {
        this.vacacionSolicitud = vacacionSolicitud;
        this.logo = logo;
        this.vacacionServicio = vacacionServicio;
    }

    public File generar() throws IOException, DocumentException {
        File pdf = File.createTempFile("ANTICIPO_SOLICITUD_VACACION", ".pdf");
        Document document = new Document(PageSize.A4, 30, 30, 10, 10);
        PdfWriter.getInstance(document, new FileOutputStream(pdf));
        document.open();

        Image imagen = Image.getInstance(logo);
        imagen.setAlignment(Element.ALIGN_CENTER);
        imagen.scaleAbsoluteHeight(80);
        imagen.scaleAbsoluteWidth(130);
        document.add(imagen);
        document.add(escribir("MUNICIPIO DEL DISTRITO METROPOLITANO DE QUITO", 11, Font.BOLD, Element.ALIGN_CENTER));
        document.add(escribir("DIRECCIÓN DE RECURSOS HUMANOS", 11, Font.BOLD, Element.ALIGN_CENTER));
        document.add(escribir("SOLICITUD PARA AUSENTARSE  DEL LUGAR DEL TRABAJO CON CARGO A VACACIONES", 11, Font.BOLD,
                Element.ALIGN_CENTER));

        document.add(Chunk.NEWLINE);

        PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(100);
        table2.getDefaultCell().setBorder(0);
        table2.setWidths(new float[]{30, 70});

        table2.addCell(escribirCelda("Revisado el expediente del señor/a:", 10, Font.NORMAL, Element.ALIGN_LEFT, 
                Rectangle.NO_BORDER));
        table2.addCell(escribirCelda(vacacionSolicitud.getDistributivoDetalle().getServidor().getApellidosNombres(), 10, 
                Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER));
        table2.addCell(escribirCelda("Quien ocupa el puesto de:", 10, Font.NORMAL, Element.ALIGN_LEFT, 
                Rectangle.NO_BORDER));
        table2.addCell(escribirCelda(vacacionSolicitud.getDistributivoDetalle().getEscalaOcupacional().getNombre(), 10, 
                Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER));
        table2.addCell(escribirCelda("En el área de:", 10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER));
        table2.addCell(escribirCelda(vacacionSolicitud.getDistributivoDetalle().getDistributivo().
                getUnidadOrganizacional().getRuta(), 10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER));
        table2.addCell(escribirCelda("Se registran los siguientes datos:", 10, Font.NORMAL, Element.ALIGN_LEFT, 
                Rectangle.NO_BORDER));
        document.add(table2);

        Date fechaInicial = null;
        try {
            fechaInicial = vacacionServicio.obtenerFechaInicioVacaciones(vacacionSolicitud);
        } catch (ServicioException ex) {
            Logger.getLogger(GenerarSolicitudAnticipoVacacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        document.add(escribir("FECHA PARA GENERACIÓN DE PERMISO:" + new SimpleDateFormat("dd/MM/yyyy").format(fechaInicial), 10, Font.NORMAL, Element.ALIGN_LEFT));

        LineSeparator ls = new LineSeparator();
        LineSeparator ls2 = new LineSeparator();
        ls2.setPercentage(60);
        document.add(new Chunk(ls));

        document.add(escribir("Vacaciones pendientes:", 10, Font.NORMAL, Element.ALIGN_LEFT));

        Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(vacacionSolicitud.getSaldoVacacionesEfectiva(), vacacionSolicitud.getDistributivoDetalle().getServidor().getJornada());
        Integer saldo2[] = UtilFechas.convertirMinutosA_ddHHmm(vacacionSolicitud.getSaldoVacacionesEfectiva() - UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vacacionSolicitud.getTipoPeriodo().charAt(0), vacacionSolicitud.getCantidadSolicitada(),
                vacacionSolicitud.getDistributivoDetalle().getServidor().getJornada()) - vacacionSolicitud.getCantidadSolicitadaMinutos(), vacacionSolicitud.getDistributivoDetalle().getServidor().getJornada());

        PdfPTable table3 = new PdfPTable(2);
        table3.getDefaultCell().setBorder(0);
        table3.setWidthPercentage(60);
        table3.addCell(escribirCelda("TIPO", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table3.addCell(escribirCelda("DÍAS/HORAS/MINUTOS", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table3.addCell(escribirCelda("EFECTIVAS", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table3.addCell(escribirCelda(saldo[0] + " Días " + saldo[1] + " Horas " + saldo[2] + " Minutos ", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table3.addCell(escribirCelda("TOTAL", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table3.addCell(escribirCelda(saldo[0] + " Días " + saldo[1] + " Horas " + saldo[2] + " Minutos ", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        document.add(table3);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable table4 = new PdfPTable(2);
        table4.getDefaultCell().setBorder(0);
        table4.setHorizontalAlignment(Element.ALIGN_CENTER);
        table4.setWidthPercentage(80);
        table4.setWidths(new float[]{50, 50});
        table4.addCell(new Phrase(new Chunk(ls2)));
        table4.addCell(new Phrase(new Chunk(ls2)));
        table4.addCell(escribirCelda("Director de Recursos Humanos", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table4.addCell(escribirCelda("Analista", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        document.add(table4);

        document.add(Chunk.NEWLINE);
        document.add(new Chunk(ls));
        document.add(Chunk.NEWLINE);

        if (vacacionSolicitud.getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
            document.add(escribir("De conformidad con la certificación de vacaciones conferida por la "
                    + "Dirección de Recursos Humanos, solicito "
                    + vacacionSolicitud.getCantidadSolicitada() + " días de adelanto de vacaciones, a partir del "
                    + UtilFechas.obtenerDiaDelMes(fechaInicial) + " de "
                    + UtilFechas.obtenerNombreMes(UtilFechas.obtenerMes(fechaInicial) + 1) + " del "
                    + UtilFechas.obtenerAnio(fechaInicial) /*+ " al "
                     + UtilFechas.obtenerDiaDelMes(vacacionSolicitud.getFechaFin()) + " de "
                     + UtilFechas.obtenerNombreMes(UtilFechas.obtenerMes(vacacionSolicitud.getFechaFin()) + 1) + " del "
                     + UtilFechas.obtenerAnio(vacacionSolicitud.getFechaFin())*/, 10, Font.NORMAL, Element.ALIGN_LEFT));
        } else {
            document.add(escribir("De conformidad con la certificación de vacaciones conferida por la "
                    + "Dirección de Recursos Humanos, solicito "
                    + vacacionSolicitud.getCantidadSolicitada() + " horas " + vacacionSolicitud.getCantidadSolicitadaMinutos() + " minutos, de permiso personal con cargo a vacaciones, el día "
                    + UtilFechas.obtenerDiaDelMes(fechaInicial) + " de "
                    + UtilFechas.obtenerNombreMes(UtilFechas.obtenerMes(fechaInicial) + 1) + " del "
                    + UtilFechas.obtenerAnio(fechaInicial) /*+ ", desde "
                     + new SimpleDateFormat("HH:mm").format(vacacionSolicitud.getHoraInicio()) + "  hasta "
                     + new SimpleDateFormat("HH:mm").format(vacacionSolicitud.getHoraFin())*/, 10, Font.NORMAL, Element.ALIGN_LEFT));
        }

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable tablaCalendario = Util.dibujarCalendarios(vacacionSolicitud);
        document.add(tablaCalendario);

        document.add(Chunk.NEWLINE);

        PdfPTable table5 = new PdfPTable(1);
        table5.getDefaultCell().setBorder(0);
        table5.setHorizontalAlignment(Element.ALIGN_CENTER);
        table5.setWidthPercentage(60);
        table5.addCell(new Phrase(new Chunk(ls2)));
        table5.addCell(escribirCelda(vacacionSolicitud.getDistributivoDetalle().getServidor().getApellidosNombres(), 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        document.add(table5);

        document.add(Chunk.NEWLINE);

        document.add(escribir("Esta información esta sujeta a verificación. En caso de comprobar cualquier "
                + "alteración o irregularidad será motivo "
                + "suficiente para iniciar las acciones legales administrativas pertinentes",
                10, Font.NORMAL, Element.ALIGN_LEFT));

        document.add(Chunk.NEWLINE);
        document.add(new Chunk(ls));

        PdfPTable table6 = new PdfPTable(4);
        table6.getDefaultCell().setBorder(0);
        table6.setWidths(new float[]{45, 5, 45, 5});
        table6.setHorizontalAlignment(Element.ALIGN_CENTER);
        table6.setWidthPercentage(40);
        table6.addCell(escribirCelda("Autorizado:", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table6.addCell(escribirCelda("", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.BOX));
        table6.addCell(escribirCelda("No autorizado:", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table6.addCell(escribirCelda("", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.BOX));
        document.add(table6);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        PdfPTable table7 = new PdfPTable(2);
        table7.getDefaultCell().setBorder(0);
        table7.setHorizontalAlignment(Element.ALIGN_CENTER);
        table7.setWidthPercentage(80);
        table7.setWidths(new float[]{50, 50});
        table7.addCell(new Phrase(new Chunk(ls2)));
        table7.addCell(new Phrase(new Chunk(ls2)));
        table7.addCell(escribirCelda("Jefe inmediato", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table7.addCell(escribirCelda("Director", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        document.add(table7);

        document.add(escribir("OBSERVACIONES:", 10, Font.NORMAL, Element.ALIGN_LEFT));

        DottedLineSeparator ls3 = new DottedLineSeparator();
        ls3.setOffset(1);
        document.add(Chunk.NEWLINE);
        document.add(ls3);
        document.add(Chunk.NEWLINE);
        document.add(ls3);

        if (vacacionSolicitud.getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
            document.add(escribir("Se le concede " + vacacionSolicitud.getCantidadSolicitada() + " días, le quedan "
                    + saldo2[0] + " días, " + saldo2[1] + " horas," + saldo2[2] + " minutos  pendientes.", 10, Font.NORMAL, Element.ALIGN_LEFT));
        } else {
            document.add(escribir("Se le concede " + vacacionSolicitud.getCantidadSolicitada() + " horas " + vacacionSolicitud.getCantidadSolicitadaMinutos() + " minutos, le quedan "
                    + saldo2[0] + " días, " + saldo2[1] + " horas," + saldo2[2] + " minutos  pendientes.", 10, Font.NORMAL, Element.ALIGN_LEFT));

        }

        document.add(Chunk.NEWLINE);
        PdfPTable table9 = new PdfPTable(4);
        table9.getDefaultCell().setBorder(0);
        table9.setHorizontalAlignment(Element.ALIGN_CENTER);
        table9.setWidths(new float[]{20, 40, 20, 20});
        table9.setWidthPercentage(100);
        table9.addCell(escribirCelda("Elaborado por:", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table9.addCell(escribirCelda(vacacionSolicitud.getNombreUsuarioCreacion(), 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table9.addCell(escribirCelda("Fecha de elaboración:", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));
        table9.addCell(escribirCelda(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(vacacionSolicitud.getFechaCreacion()), 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER));

        document.add(table9);

        document.close();
        return pdf;
    }

    /**
     *
     * @param document
     * @param texto
     * @param bold
     * @param tamanio
     * @param propiedadElemento
     */
    private Paragraph escribir(String texto, int size, int propiedadFont, int propiedadElement) throws DocumentException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, propiedadFont);
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(propiedadElement);
        return p;
    }

    private PdfPCell escribirCelda(String texto, int size, int propiedadFont, int propiedadElement, int propiedadCelda) throws DocumentException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, propiedadFont);
        Paragraph p = new Paragraph(texto, font);
        PdfPCell cell = new PdfPCell(p);
        p.setAlignment(propiedadElement);
        cell.setBorder(propiedadCelda);
        cell.setHorizontalAlignment(propiedadElement);
        return cell;
    }

}
