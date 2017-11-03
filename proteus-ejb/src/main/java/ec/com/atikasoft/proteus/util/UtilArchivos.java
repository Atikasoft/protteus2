/*
 *  UtilArchivos.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Oct 10, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

import com.Ostermiller.util.CSVParser;
import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;
import javolution.util.FastTable;
import org.apache.commons.collections.FastArrayList;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import au.com.bytecode.opencsv.CSVReader;
import com.Ostermiller.util.BadDelimiterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidades para gestionar o usar archivos.
 *
 * @author Henry Molina  <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public final class UtilArchivos {

    /**
     * Constructor sin argumentos.
     */
    private UtilArchivos() {
        super();
    }

    /**
     * Se encarga de transformar un archivo fisco en formato CSV en un arreglo de objetos.
     *
     * @param archivo Archivo con los datos.
     * @param delimitador delimitador de campos
     * @param longitud Longitud que se inicializara.
     * @throws IOException Erro al abrir el archivo.
     * @return Lista de objetos cargados.
     */
    public static Map<Long, List<String>> parsearArchivoCSV(final byte[] archivo, final char delimitador,
            final int longitud) throws IOException {
        Map<Long, List<String>> registros = new FastMap<Long, List<String>>();
        CSVParser parser = new CSVParser(new ByteArrayInputStream(archivo), delimitador);
        String[][] valores = parser.getAllValues();
        // Display the parsed data
        if (valores == null) {
            throw new IOException("El archivo cargado no tiene registros para procesar");
        } else {
//            System.out.println("Total registros leidos:"+valores.length);
            for (int i = 0; i < valores.length; i++) {
                List<String> registro = new FastTable<String>();
                registro.addAll(0, Arrays.asList(valores[i]));
                if (registro.size() < longitud) {
                    for (int j = registro.size(); j < longitud; j++) {
                        registro.add("");
                    }
                }
                registros.put(Long.valueOf(i + 1), registro);
            }
        }
        return registros;

    }

    /**
     * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the
     * BufferedReader return null which means there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     *
     * @param is archivo entrada.
     * @return Cadena de salida.
     */
    public static String convertStreamToString(final InputStream is) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Este metodo crear un archivo temporal.
     *
     * @param inputStream InputStream
     * @param nombreArchivo String
     * @param extencion String
     * @return File
     * @throws IOException Captura de excepciones.
     */
    public static File getFileFromBytes(final InputStream inputStream, final String nombreArchivo, final String extencion) throws IOException {
        InputStream in = inputStream;
        File tempFile = File.createTempFile(nombreArchivo, extencion);
        tempFile.deleteOnExit();
        FileOutputStream fout = null;

        try {

            fout = new FileOutputStream(tempFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                fout.write(buf, 0, len);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        return tempFile;
    }

    /**
     * Se encarga de copiar el contenido de un archivo como array de bytes hacia un archivo.
     *
     * @param fuente Archivo fuente.
     * @param destino Archivo destino.
     * @throws IOException Error en operacion.
     */
    public static void copy(final byte[] fuente, final File destino) throws IOException {
        final InputStream in = new ByteArrayInputStream(fuente);
        final OutputStream out = new FileOutputStream(destino);

        // Transfer bytes from in to out
        final byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Se encarga de copiar un archivo hacia otro orachivo.
     *
     * @param fuente Archivo fuente.
     * @param destino Archivo destino.
     * @throws IOException Error en la operacion.
     */
    public static void copy(final File fuente, final File destino) throws IOException {
        final InputStream in = new FileInputStream(fuente);
        final OutputStream out = new FileOutputStream(destino);

        // Transfer bytes from in to out
        final byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    /**
     * Se encarga de determinar si tamanioArchivo es menor o igual a tamaniMaximoPermitidoMegabytes.
     *
     * @param tamanioArchivoEnBytes tamaño en bytes
     * @param tamanioMaximoPermitidoEnMegabytes maximo permitido
     * @return true si tamanioArchivoEnBytes es menor o igual a tamaniMaximoPermitidoEnMegabytes.
     */
    public static boolean validarTamanio(final long tamanioArchivoEnBytes,
            final long tamanioMaximoPermitidoEnMegabytes) {
        float tamanioEnMegabytes = tamanioArchivoEnBytes / 1000000f;
        return tamanioEnMegabytes <= tamanioMaximoPermitidoEnMegabytes;
    }

    /**
     * TRansforma un BufferedReader a string.
     *
     * @param bRead
     * @return
     * @throws IOException
     */
    public static StringBuilder getStringFromBuffer(final BufferedReader bRead) throws IOException {
        StringBuilder theText = new StringBuilder();
        String line = bRead.readLine();
        while (line != null) {
            theText.append(line);
            line = bRead.readLine();
        }
        return theText;
    }

    /**
     * Permite recuperar un archivo desde la web.
     *
     * @param url Url de la localizacion del archivo.
     * @return Archivo.
     * @throws IOException Error de ejecucion.
     */
    public static byte[] obtenerArchivoWeb(final String url) throws IOException {
        // post a pagina web
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        try {
            Integer timeoutMs = Integer.valueOf(10 * 1000);
            client.getParams().setSoTimeout(timeoutMs);
            int returnCode = client.executeMethod(method);
            if (returnCode == HttpStatus.SC_OK) {
                return method.getResponseBody();
            } else {
                throw new IOException("Error Http:" + returnCode);
            }
        } finally {
            method.releaseConnection();
        }
    }

    /**
     *
     */
    private static CellStyle styleNumber;
    private static HSSFWorkbook styleNumberFWorkbook;
    private static CellStyle styleDate;
    private static HSSFWorkbook styleDateFWorkbook;
    private static HSSFCellStyle styleRow;
    private static HSSFWorkbook styleRowFWorkbook;

    private static final void construirStyleNumber(final HSSFWorkbook workbook) {
        if (styleNumber != null) {
            if (!workbook.equals(styleNumberFWorkbook)) {
                styleNumber = null;
                styleNumberFWorkbook = null;
            }
        }
        if (styleNumber == null) {
            styleNumber = workbook.createCellStyle();
            styleNumber.setDataFormat(workbook.createDataFormat().getFormat("###,###,##0.00"));
            styleNumberFWorkbook = workbook;
        }
    }

    private static final void construirStyleDate(final HSSFWorkbook workbook) {
        if (styleDate != null) {
            if (!styleDateFWorkbook.equals(workbook)) {
                styleDate = null;
                styleDateFWorkbook = null;
            }
        }
        if (styleDate == null) {
            styleDate = workbook.createCellStyle();
            styleDate.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
            styleDateFWorkbook = workbook;
        }
    }

    private static final void construirStyleRow(final HSSFWorkbook workbook) {
        if (styleRow != null) {
            if (!styleRowFWorkbook.equals(workbook)) {
                styleRow = null;
                styleRowFWorkbook = null;
            }
        }
        if (styleRow == null) {
            styleRow = workbook.createCellStyle();
            styleRow.setWrapText(true);
            styleRowFWorkbook = workbook;
        }
    }

    /**
     *
     * @param workbook
     * @param sheet
     * @param fila
     * @param style
     * @param valores
     */
    public static void crearFilaExcel(final HSSFWorkbook workbook, final HSSFSheet sheet, int fila,
            final CellStyle style, Object... valores) {
        if (valores.length > 0) {
            Row row = sheet.createRow(fila);
            construirStyleNumber(workbook);
            construirStyleDate(workbook);
            construirStyleRow(workbook);

            row.setRowStyle(styleRow);
            int cellnum = 0;
            for (Object valor : valores) {
                Cell cell = row.createCell(cellnum++);
                if (valor instanceof String) {
                    if (((String) valor).startsWith("+")) {
                        if (((String) valor).length() > 1) {
                            cell.setCellType(CellType.FORMULA);
                            valor = ((String) valor).substring(1);
                            cell.setCellFormula((String) valor);
                        }
                        cell.setCellStyle(styleNumber);
                    } else {
                        cell.setCellValue((String) valor);
                    }
                } else if (valor instanceof Date) {
                    cell.setCellValue((Date) valor);
                    cell.setCellStyle(styleDate);
                } else if (valor instanceof Long) {
                    cell.setCellValue(((Long) valor).toString());
                } else if (valor instanceof Integer) {
                    cell.setCellValue(((Integer) valor).toString());
                } else if (valor instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) valor).doubleValue());
                    cell.setCellStyle(styleNumber);
                } else if (valor instanceof Double) {
                    cell.setCellValue((Double) valor);
                    cell.setCellStyle(styleNumber);
                }
                if (style != null) {
                    cell.setCellStyle(style);
                }
            }
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static List<String[]> leerArchivoCSVSubido(File f) {
        try {
            CSVReader reader;

            List<String[]> lineas = new FastArrayList();
            reader = new CSVReader(new FileReader(f), ';');
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                lineas.add(nextLine);
            }

            return lineas;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cargar data como array de array para un csv
     *
     * @param csv
     * @param delimitador
     * @return
     * @throws Exception
     */
    public static String[][] obtenerContenidoCSV(final File csv, final char delimitador) throws Exception {
        FileReader fr = null;
        String[][] csvContenido = null;
        try {
            fr = new FileReader(csv);
            csvContenido = CSVParser.parse(fr, delimitador);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilArchivos.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        } catch (IOException | BadDelimiterException ex) {
            Logger.getLogger(UtilArchivos.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilArchivos.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
        }
        return csvContenido;
    }

    /**
     *
     * @param listOfPdfFiles
     * @param outputFile
     * @throws DocumentException
     * @throws IOException
     */
    public static void concatenarPdfs(List<File> listOfPdfFiles, File outputFile)
            throws DocumentException, IOException {
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        PdfCopy copy = new PdfSmartCopy(document, outputStream);
        document.open();
        for (File inFile : listOfPdfFiles) {
            PdfReader reader = new PdfReader(inFile.getAbsolutePath());
            copy.addDocument(reader);
            reader.close();
        }
        document.close();
    }
}
