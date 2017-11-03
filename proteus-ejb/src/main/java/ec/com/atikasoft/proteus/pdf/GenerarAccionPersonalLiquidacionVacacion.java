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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public class GenerarAccionPersonalLiquidacionVacacion {

    /**
     *
     */
    private final Vacacion vacacion;

    /**
     * DISTRIBUTIVO DETALLE
     */
    private final DistributivoDetalle dd;

    /**
     *
     */
    private final String nroAccionPersonal;

    /**
     *
     */
    private final String explicacion;

    /**
     *
     */
    private final String NOMBRE_ARCHIVO = "ACCION_PERSONAL_LIQUIDACION_VACACION";

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
    private final String codigoDocPrevio;

    /**
     *
     */
    private final String valorDocPrevio;

    /**
     *
     */
    private final Date fechaDocPrevio;

    /**
     *
     */
    private final String nombreUsuarioConectado;

    /**
     *
     */
    private final String representanteRRHH;

    /**
     *
     */
    private final String nombreRepresentanteRRHH;
    /**
     *
     */

    private final String autoridadNominadora;

    /**
     *
     */
    private final String nombreAutoridadNominadora;

    /**
     *
     * @param vacacion vacacion
     * @param dd distributivo detalle
     * @param nombreUsuarioConectado nombre del usuario conectado
     * @param nroAccionpersonal contador de la accion de personal
     * @param explicacion
     * @param codigoDocPrevio codigo del tipo de documento legal
     * @param valorDocPrevio
     * @param fechaDocPrevio
     * @param logo archivo del logo de la institucion
     */
    public GenerarAccionPersonalLiquidacionVacacion(
            final Vacacion vacacion,
            final DistributivoDetalle dd,
            final String nombreUsuarioConectado,
            final String nroAccionpersonal,
            final String explicacion,
            final String codigoDocPrevio,
            final String valorDocPrevio,
            final Date fechaDocPrevio,
            final String representanteRRHH,
            final String nombreRepresentanteRRHH,
            final String autoridadNominadora,
            final String nombreAutoridadNominadora,
            final byte[] logo) {
        this.vacacion = vacacion;
        this.dd = dd;
        this.nombreUsuarioConectado = nombreUsuarioConectado;
        this.nroAccionPersonal = nroAccionpersonal;
        this.explicacion = explicacion;
        this.logo = logo;
        this.codigoDocPrevio = codigoDocPrevio;
        this.valorDocPrevio = valorDocPrevio;
        this.fechaDocPrevio = fechaDocPrevio;
        this.representanteRRHH = representanteRRHH;
        this.nombreRepresentanteRRHH = nombreRepresentanteRRHH;
        this.autoridadNominadora = autoridadNominadora;
        this.nombreAutoridadNominadora = nombreAutoridadNominadora;
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
        String nroFormateado = String.format("%05d", new Long(nroAccionPersonal));
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
//        tablaInfoAccion.getDefaultCell().setBorder(1);

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

        SimpleDateFormat sdf = new SimpleDateFormat(UtilFechas.FORMATO_FECHA);
        Long saldoTotalInicial = this.vacacion.getSaldo() + this.vacacion.getSaldoProporcional();

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);

        PdfPCell c1 = escribirCelda(
                UtilCadena.concatenar(
                        docSeleccionado(
                                DocumentoPrevioEnum.DECRETO.getCodigo()) ? "DECRETO (X)      " : "DECRETO ( )    ",
                        docSeleccionado(
                                DocumentoPrevioEnum.ACUERDO.getCodigo()) ? "ACUERDO (X)      " : "ACUERDO ( )    ",
                        docSeleccionado(
                                DocumentoPrevioEnum.RESOLUCION.getCodigo()) ? "RESOLUCIÓN (X)      " : "RESOLUCIÓN ( )     ",
                        docSeleccionado(
                                DocumentoPrevioEnum.OFICIO.getCodigo()) ? "OFICIO (X)      " : "OFICIO ( )   ",
                        docSeleccionado(
                                DocumentoPrevioEnum.SOLICITUD.getCodigo()) ? "SOLICITUD (X)      " : "SOLICITUD ( )    ",
                        docSeleccionado(
                                DocumentoPrevioEnum.CIRCULAR.getCodigo()) ? "CIRCULAR (X)      " : "CIRCULAR ( )     ",
                        docSeleccionado(
                                DocumentoPrevioEnum.COOTAD.getCodigo()) ? "COOTAD (X)      " : "COOTAD ( )     "),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0
        );
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT | PdfPCell.BOTTOM);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------  
        String tabFormat = "%-40s%s%n";
        c1 = escribirCelda(String.format(tabFormat,
                UtilCadena.concatenar("No.: ", this.valorDocPrevio),
                UtilCadena.concatenar("Fecha: ", UtilFechas.formatear(this.fechaDocPrevio))),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.RECTANGLE, 1, 1);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------  
        c1 = escribirCelda(this.vacacion.getServidorInstitucion().getServidor().getApellidosNombres(),
                10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        c1 = escribirCelda("APELLIDOS Y NOMBRES" + "\n", 10, Font.BOLD,
                Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------   

        PdfPTable t2 = new PdfPTable(1);

        PdfPCell c2 = escribirCelda("No. DE IDENTIFICACIÓN",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t2.addCell(c2);

        c2 = escribirCelda(this.vacacion.getServidorInstitucion().getServidor().getNumeroIdentificacion(),
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

        Date fechaIni = this.dd.getServidor().getFechaIngresoInstitucion();
        if (fechaIni != null) {
            c2 = escribirCelda(UtilFechas.formatear(fechaIni), 10,
                    Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        } else {
            c2 = escribirCelda("-", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        }

        c2.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t2.addCell(c2);
        c1 = new PdfPCell(t2);
        tabla.addCell(c1);

        t2 = new PdfPTable(1);

        c2 = escribirCelda("FECHA FIN",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t2.addCell(c2);

        Date fechaFin = this.dd.getServidor().getFechaSalida();
        if (fechaFin != null) {
            c2 = escribirCelda(UtilFechas.formatear(fechaFin), 10,
                    Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        } else {
            c2 = escribirCelda("-", 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        }

        c2.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t2.addCell(c2);
        c1 = new PdfPCell(t2);
        tabla.addCell(c1);
        //----------------------------------------------------------------------

        c1 = escribirCelda("EXPLICACIÓN:",
                10, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        String explicacion = this.explicacion.replaceAll(
                "#fecha_inicio", "" + sdf.format(this.dd.getServidor().getFechaIngresoInstitucion()));
        Date fechaSalida = this.dd.getServidor().getFechaSalida();
        if (fechaSalida != null) {
            explicacion = explicacion.replaceAll("#fecha_final", "" + sdf.format(fechaSalida));
        } else {
            explicacion = explicacion.replaceAll(" AL #fecha_final", "");
        }

        explicacion = explicacion.replaceAll("#saldo_vacacion", String.valueOf(UtilFechas.convertirMinutosA_ddHHmmPalabras(
                saldoTotalInicial, this.dd.getServidor().getJornada())));

        c1 = escribirCelda(explicacion, 10, Font.NORMAL, Element.ALIGN_JUSTIFIED,
                Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------
        t2 = new PdfPTable(new float[]{1, 3});
        c2 = escribirCelda("TIPO DE MOVIMIENTO: ",
                10, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
        c2.setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.BOTTOM);
        t2.addCell(c2);

        c2 = escribirCelda(
                "LIQUIDACIÓN DE VACACIONES",
                10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c2.setBorder(PdfPCell.BOTTOM | PdfPCell.TOP);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        c1.setColspan(3);
        tabla.addCell(c1);
        //---------------------------------------------------------------------- 

        t2 = new PdfPTable(2);

        PdfPTable t21 = new PdfPTable(2);
        PdfPCell c21 = escribirCelda("SITUACIÓN ACTUAL",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c21.setColspan(2);
        t21.addCell(c21);

        c21 = escribirCelda("REGIMEN LABORAL:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(this.dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre(),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("UBICACIÓN:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(this.dd.getDistributivo().getUnidadOrganizacional().getNombreCompleto(),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("PUESTO:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(this.dd.getCodigoPuesto().toString(),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("LUGAR DE TRABAJO:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(this.dd.getUbicacionGeografica().getNombreCompleto(),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("REMUNERACIÓN MENSUAL:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM);
        t21.addCell(c21);

        c21 = escribirCelda("$" + String.format("%.2f", this.dd.getRmu()),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        //comentar esta linea si se quita comentario del bloque que continúa
//        t2.addCell(new PdfPCell(new Phrase("")));
        //------------------------------------------------------------------
        t21 = new PdfPTable(2);

        c21 = escribirCelda("SALDO INICIAL VACACIONES EFECTIVAS:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(UtilFechas.convertirMinutosA_ddHHmmPalabras(
                this.vacacion.getSaldo(), this.dd.getServidor().getJornada()),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("SALDO INICIAL VACACIONES PROPORCIONALES:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(UtilFechas.convertirMinutosA_ddHHmmPalabras(
                this.vacacion.getSaldoProporcional(), this.dd.getServidor().getJornada()),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("SALDO TOTAL INICIAL:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        c21 = escribirCelda(String.valueOf(UtilFechas.convertirMinutosA_ddHHmmPalabras(
                saldoTotalInicial, this.dd.getServidor().getJornada())),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("DÍAS CONCEDIDOS:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT);
        t21.addCell(c21);

        Long tiempoConcedido = this.vacacion.getSaldo() + this.vacacion.getSaldoProporcional();
        c21 = escribirCelda(String.valueOf(UtilFechas.convertirMinutosA_ddHHmmPalabras(
                tiempoConcedido, this.dd.getServidor().getJornada())),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda(
                "SALDO TOTAL FINAL:",
                8, Font.BOLD, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP);
        t21.addCell(c21);

        c21 = escribirCelda(UtilFechas.convertirMinutosA_ddHHmmPalabras(0,
                this.vacacion.getServidorInstitucion().getServidor().getJornada()),
                8, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);

        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        c1.setColspan(3);
        tabla.addCell(c1);
        //---------------------------------------------------------------------- 

        t2 = new PdfPTable(2);

        t21 = new PdfPTable(1);
        c21 = escribirCelda("ACTA FINAL DEL CONCURSO",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("No.: ",
                10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        t21 = new PdfPTable(1);

        c21 = escribirCelda("PROCESO DE RECURSOS HUMANOS",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 1, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("f. ",
                10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda(nombreRepresentanteRRHH.toUpperCase(),
                8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda(representanteRRHH.toUpperCase(),
                8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        c1 = new PdfPCell(t2);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------    

        c1 = escribirCelda("f.", 10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 1, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        c1 = escribirCelda(nombreAutoridadNominadora.toUpperCase(), 10, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);

        c1 = escribirCelda(autoridadNominadora.toUpperCase(), 8, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
        c1.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        c1.setColspan(3);
        tabla.addCell(c1);
        //----------------------------------------------------------------------

        t2 = new PdfPTable(2);

        t21 = new PdfPTable(1);
        c21 = escribirCelda("RECURSOS HUMANOS",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("No.: ",
                10, Font.NORMAL, Element.ALIGN_LEFT, Rectangle.NO_BORDER, 0, 1);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM | PdfPCell.RIGHT);
        t21.addCell(c21);

        c2 = new PdfPCell(t21);
        t2.addCell(c2);

        t21 = new PdfPTable(1);
        c21 = escribirCelda("REGISTRO Y CONTROL",
                10, Font.BOLD, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda(
                nombreUsuarioConectado, 8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 0);
        c21.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        t21.addCell(c21);

        c21 = escribirCelda("RESPONSABLE DE REGISTRO",
                8, Font.NORMAL, Element.ALIGN_CENTER, Rectangle.NO_BORDER, 0, 1);
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
//        PdfPCell cell = new PdfPCell(p);
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

    /**
     *
     * @param codDocPrev codigo del documento previo
     * @return indicar si es seleccionado
     */
    private boolean docSeleccionado(String codDocPrev) {
        return codDocPrev.equals(this.codigoDocPrevio);
    }

}
