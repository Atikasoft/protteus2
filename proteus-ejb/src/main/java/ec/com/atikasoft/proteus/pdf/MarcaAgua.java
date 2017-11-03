/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author hmolina
 */
public class MarcaAgua {

    /**
     *
     * @param src
     * @return
     * @throws IOException
     */
    public static File procesar(File src, File marcaAgua) throws IOException, DocumentException {
        File pdf = File.createTempFile(src.getName() + "_SIGEN", ".pdf");
        PdfReader reader = new PdfReader(src.getAbsolutePath());
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdf));
        Font f = new Font(FontFamily.HELVETICA, 30);
//        Phrase p = new Phrase("My watermark (text)", f);
        Image img = Image.getInstance(marcaAgua.getAbsolutePath());
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);
        gs1.setStrokeOpacity(0.3f);
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
            over.restoreState();
        }
        stamper.close();
        reader.close();
        return pdf;

    }

}
