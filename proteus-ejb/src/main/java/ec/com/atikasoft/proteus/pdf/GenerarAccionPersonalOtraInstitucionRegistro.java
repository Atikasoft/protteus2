/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.PersonalOtrasInstituciones;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerarAccionPersonalOtraInstitucionRegistro {

    /**
     *
     */
    private final PersonalOtrasInstituciones personalOtrasInstituciones;

    /**
     *
     */
    private final String contadorAccionPersonal;

    /**
     *
     */
    private final String NOMBRE_ARCHIVO = "ACCION_PERSONAL_OTRAS_INSTITUCIONES_REGISTRO";

    /**
     *
     */
    private final String EXTENSION_ARCHIVO = ".pdf";

    /**
     *
     */
    private final byte[] logo;

    /**
     *
     */
    //private final String codigoDocPrevio;
    /**
     *
     */
    private final String nombreUsuarioConectado;

    /**
     *
     */
    private final String textoResolucion;

    /**
     *
     * @param personal
     * @param nombreUsuarioConectado nombre del usuario conectado
     * @param contadorAccionPersonalOtrasInstitucionesRegistro contador de la accion de personal
     * @param logo archivo del logo de la institucion
     * @param textoResolucion
     */
    public GenerarAccionPersonalOtraInstitucionRegistro(
            PersonalOtrasInstituciones personal,
            final String nombreUsuarioConectado,
            final String contadorAccionPersonalOtrasInstitucionesRegistro,
            final byte[] logo,
            final String textoResolucion) {
        this.nombreUsuarioConectado = nombreUsuarioConectado;
        this.contadorAccionPersonal = contadorAccionPersonalOtrasInstitucionesRegistro;
        this.logo = logo;
        this.personalOtrasInstituciones = personal;
        this.textoResolucion = textoResolucion;
    }

    /**
     * Generar el archivo pdf de la accion de personal
     *
     * @return archivo pdf
     * @throws IOException error de acceso al archivo
     * @throws DocumentException error al crear al pdf
     * @throws DaoException error de acceso a datos
     * @throws ParseException
     */
    public File generar() throws IOException, DocumentException, DaoException, ParseException {
        StringBuilder nombreArchivo = new StringBuilder("");
        String nroFormateado = String.format("%05d", new Long(contadorAccionPersonal));
        nombreArchivo.append(NOMBRE_ARCHIVO).append(nroFormateado);
        File pdf = File.createTempFile(nombreArchivo.toString(), EXTENSION_ARCHIVO);

        Document document = new Document(PageSize.A4, 30, 30, 10, 10);
        PdfWriter.getInstance(document, new FileOutputStream(pdf));
        document.open();

        PdfPTable tablaCabecera = new PdfPTable(2);
        tablaCabecera.setWidthPercentage(100);
        tablaCabecera.getDefaultCell().setBorder(0);
        tablaCabecera.setWidths(new float[]{75, 25});

        Image imagen = Image.getInstance(logo);
        imagen.setAlignment(Element.ALIGN_RIGHT);
        imagen.scaleAbsoluteHeight(80);
        imagen.scaleAbsoluteWidth(130);
        PdfPCell celda = new PdfPCell(imagen);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBorder(PdfPCell.NO_BORDER);
        tablaCabecera.addCell(celda);

        PdfPTable tablaInfoAccion = new PdfPTable(1);
        tablaInfoAccion.setWidthPercentage(100);

        celda = escribirCelda(
                "ACCIÓN DE PERSONAL", 10, Font.BOLD,
                Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        celda.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        tablaInfoAccion.addCell(celda);

        celda = escribirCelda(
                UtilCadena.concatenar("No.: ", nroFormateado),
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        celda.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        tablaInfoAccion.addCell(celda);

        tablaCabecera.addCell(tablaInfoAccion);
        tablaCabecera.setSpacingAfter(10);
        document.add(tablaCabecera);

        agregarCuerpo(document);
        document.close();
        return pdf;
    }

    /**
     * Agrega el contenido de la accion de personal
     *
     * @param document referencia del documento pdf
     * @throws BadElementException error de construccion pdf
     * @throws DocumentException error de construccion odf
     * @throws DaoException error de acceso a datos
     */
    private void agregarCuerpo(Document document)
            throws BadElementException, DocumentException, DaoException, ParseException {

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);

        //----------------------------------------------------------------------  
        PdfPCell c1 = escribirCelda(textoResolucion, 8, Font.NORMAL, Element.ALIGN_JUSTIFIED, Rectangle.NO_BORDER, 0, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        c1 = escribirCelda("APELLIDOS Y NOMBRES" + "\n", 10, Font.BOLD,
                Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        c1 = escribirCelda(
                this.personalOtrasInstituciones.getApellidosNombres(),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        //----------------------------------------------------------------------   
        PdfPTable t2 = new PdfPTable(1);

        PdfPCell c2 = escribirCelda("No. DE IDENTIFICACIÓN",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t2.addCell(c2);

        c2 = escribirCelda(
                this.personalOtrasInstituciones.getNumeroIdentificacion(),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        tabla.addCell(c1);

        t2 = new PdfPTable(1);

        c2 = escribirCelda("FECHA INICIO",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t2.addCell(c2);

        c2 = escribirCelda(
                UtilFechas.formatear(this.personalOtrasInstituciones.getFechaInicio()),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        tabla.addCell(c1);

        t2 = new PdfPTable(1);

        c2 = escribirCelda("FECHA FIN",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t2.addCell(c2);

        c2 = escribirCelda(
                UtilFechas.formatear(this.personalOtrasInstituciones.getFechaFin()),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        tabla.addCell(c1);
        //----------------------------------------------------------------------

        c1 = escribirCelda("EXPLICACIÓN",
                10, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        String explicacion = this.personalOtrasInstituciones.getJustificacionIngreso();
        c1 = escribirCelda(explicacion, 10, Font.NORMAL, Element.ALIGN_LEFT,
                Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------

        //t2 = new PdfPTable(new float[]{1, 3});
        t2 = new PdfPTable(1);
        c2 = escribirCelda("ACCIÓN ",
                10, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.BOTTOM);
        t2.addCell(c2);

        c2 = escribirCelda(
                "REGISTRO DE COMISIÓN DE SERVICIOS CON REMUNERACIÓN DE OTRA INSTITUCIÓN",
                10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.BOTTOM | PdfPCell.TOP);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        c1.setColspan(3);
        tabla.addCell(c1);
        //---------------------------------------------------------------------- 

        /*        t2 = new PdfPTable(2);

         PdfPTable t21 = new PdfPTable(2);
         PdfPCell c21 = escribirCelda("SITUACIÓN ACTUAL",
         10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
         c21.setColspan(2);
         t21.addCell(c21);
         */
//        c21 = escribirCelda("REGIMEN LABORAL:",
//                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
//        c21.setBorder(PdfPCell.LEFT);
//        t21.addCell(c21);
//
//        c21 = escribirCelda(
//                this.vacacionSolicitud.getDistributivoDetalle().
//                getEscalaOcupacional().getNivelOcupacional()
//                .getRegimenLaboral().getNombre(),
//                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
//        c21.setBorder(PdfPCell.RIGHT);
//        t21.addCell(c21);
//
//        c21 = escribirCelda("UBICACIÓN:",
//                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
//        c21.setBorder(PdfPCell.LEFT);
//        t21.addCell(c21);
//
//        c21 = escribirCelda(
//                this.vacacionSolicitud.getDistributivoDetalle().
//                getDistributivo().getUnidadOrganizacional().getNombreCompleto(),
//                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
//        c21.setBorder(PdfPCell.RIGHT);
//        t21.addCell(c21);
/*        c21 = escribirCelda("INSTITUCIÓN ORIGEN:",
         8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.LEFT);
         t21.addCell(c21);

         c21 = escribirCelda(
         this.personalOtrasInstituciones.getInstitucionOrigen(),
         8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.RIGHT);
         t21.addCell(c21);

         c21 = escribirCelda("PUESTO EN INSTITUCIÓN ORIGEN:",
         8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.LEFT);
         t21.addCell(c21);

         c21 = escribirCelda(
         this.personalOtrasInstituciones.getPuestoInstitucionOrigen(),
         8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.RIGHT);
         t21.addCell(c21);
        

         c21 = escribirCelda("REMUNERACIÓN EN INSTITUCIÓN ORIGEN:",
         8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
         c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM);
         t21.addCell(c21);

         c21 = escribirCelda(
         "$" + String.format("%.2f", this.personalOtrasInstituciones.getRmuInstitucionOrigen()),
         8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
         c21.setBorder(PdfPCell.BOTTOM | PdfPCell.RIGHT);
         t21.addCell(c21);

         c2 = new PdfPCell(t21);
         t2.addCell(c2);*/
        //----------------------------------------------------------------------    

        /*c1 = escribirCelda(
         "f.", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
         c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
         c1.setColspan(3);
         tabla.addCell(c1);*/
        c1 = escribirCelda("DIRECTORA DE RECURSOS HUMANOS",
                //        c1 = escribirCelda("\t",
                8, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------

        t2 = new PdfPTable(2);

        PdfPTable t21 = new PdfPTable(1);
        PdfPCell c21 = escribirCelda("RECURSOS HUMANOS",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        PdfPCell c211 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c211.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c211);

        PdfPCell c212 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c212.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c212);

        PdfPCell c213 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c213.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c213);

        c21 = escribirCelda("No.: ",
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        t21 = new PdfPTable(1);
        c21 = escribirCelda("REGISTRO Y CONTROL",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c211 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c211.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c211);

        c212 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c212.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c212);

        c213 = escribirCelda("    ", 10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c213.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c213);

        c21 = escribirCelda(
                nombreUsuarioConectado, 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("RESPONSABLE DE REGISTRO",
                8, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------  
        document.add(tabla);

    }

    /**
     *
     * @param texto texto a escribir
     * @param size tamnanio de la letra
     * @param propiedadFont propiedades de la letra
     * @param propiedadElement propiedades del elemento
     * @param propiedadCelda propiedades de la celda
     * @return celda pdf
     * @throws DocumentException error en la creacion del pdf
     */
    private PdfPCell escribirCelda(
            String texto, int size, int propiedadFont, int propiedadElement,
            int propiedadCelda, int nroLienasAntes, int nroLineasDespues) throws DocumentException {

        PdfPCell cell = new PdfPCell();
        cell.addElement(addLineasEnBlanco(nroLienasAntes));
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, size, propiedadFont);
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(propiedadElement);
        cell.addElement(p);
        cell.addElement(addLineasEnBlanco(nroLineasDespues));
        cell.setBorder(propiedadCelda);
        cell.setHorizontalAlignment(propiedadElement);
        return cell;
    }

    /**
     * AGREGA UNA LINEA EN BLANCO
     *
     * @param numeroLineas
     */
    private static Paragraph addLineasEnBlanco(int numeroLineas) {
        Paragraph p = new Paragraph();
        p.setSpacingAfter(0);
        p.setSpacingBefore(0);
        for (int i = 0; i < numeroLineas; i++) {
            p.add(new Paragraph(" "));
        }
        return p;
    }

}
