/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.pdf;

import ec.com.atikasoft.proteus.excepciones.PdfException;
import ec.com.atikasoft.proteus.vo.Formulario107VO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author hmolina
 */
public class GenerarFormulario107 {

    /**
     * Datos del formulario 107
     */
    private Formulario107VO vo;

    /**
     * Repositorio de archivos.
     */
    private String repos;

    /**
     *
     */
    private final String NOMBRE_ARCHIVO = "FORMULARIO_107";
    /**
     *
     */
    private final String EXTENSION_ARCHIVO = ".pdf";

    /**
     *
     */
    private final String FORMATO_FORMULARIO_107 = "FORMATO_FORMULARIO_107.docx";

    /**
     *
     * @param vo
     */
    public GenerarFormulario107(final Formulario107VO vo, final String repos) {
        this.vo = vo;
        this.repos = repos;
    }

    /**
     * Generar pdf del formulario 107.
     *
     * @throws PdfException
     */
    public File generar() throws PdfException {
        try {

//            StringBuilder nombreArchivo = new StringBuilder("");
//            nombreArchivo.append(NOMBRE_ARCHIVO).append("_").append(vo.getEjercicioFiscal()).append("_").
//                    append(vo.getIdentificacion());
//            File pdf = File.createTempFile(nombreArchivo.toString(), EXTENSION_ARCHIVO);
            File pdf = new File("/home/hmolina/EJEMPLO1.pdf");

            // recuperar doc word de plantilla.
            File plantilla = new File(repos + File.separator + "formatos" + File.separator + FORMATO_FORMULARIO_107);
            System.out.println("plantilla:" + plantilla.getAbsolutePath());
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(plantilla));
//                saveDocument(doc, pdf);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(pdf);
            PdfConverter.getInstance().convert(doc, out, options);
            return pdf;
        } catch (Exception e) {
            throw new PdfException(e);
        }
    }

}
