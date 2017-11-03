/*
 *  UtilImagen.java
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
 *  Sep 16, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Juan Carlos Vaca <jvaca@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public final class UtilImagen {

    /**
     * Constructor sin argumentos.
     */
    private UtilImagen() {
        super();
    }

    /**
     * Se encarga de obtener el tamaño en escala en función del ancho que se desea para una imagen.
     *
     * @param bytesImagen Los bytes de la imagen.
     * @param ancho El ancho que debe tener la imagen
     * @return Un array con el ancho y el alto de la imagen para que no se pierda la proporcion
     * @throws IOException Encaso de no poder cargar los datos de la imagen.
     */
    public static int[] obtenerEscalaImagen(final byte[] bytesImagen, final int ancho) throws IOException {
        BufferedImage sourceImage = obtenerImagen(bytesImagen);
        return obtenerEscalaImagen(sourceImage, ancho);
    }

    /**
     * Se encarga de obtener el tamaño en escala en función del ancho que se desea para una imagen.
     *
     * @param sourceImage La imagen
     * @param ancho El ancho que debe tener la imagen
     * @return Un array con el ancho y el alto de la imagen para que no se pierda la proporcion
     * @throws IOException Encaso de no poder cargar los datos de la imagen.
     */
    public static int[] obtenerEscalaImagen(final BufferedImage sourceImage, final int ancho) throws IOException {
        int srcWidth = sourceImage.getWidth();
        int srcHeight = sourceImage.getHeight();
        double longSideForSource = srcWidth;
        double longSideForDest = ancho;
        double multiplier = longSideForDest / longSideForSource;
        int destWidth = (int) (srcWidth * multiplier);
        int destHeight = (int) (srcHeight * multiplier);
        int[] resultado = new int[2];
        resultado[0] = destWidth;
        resultado[1] = destHeight;
        return resultado;
    }

    /**
     * Se encarga de obtener una instancia de BufferedImage con los bytes recibidos como parametro.
     *
     * @param bytesImagen Array de bytes.
     * @throws IOException Cuando no se ha podido recuperar una imagen con los bytes recibidos.
     * @return BufferedImage
     */
    public static BufferedImage obtenerImagen(final byte[] bytesImagen) throws IOException {
        ByteArrayInputStream baio = new ByteArrayInputStream(bytesImagen);
        return ImageIO.read(baio);
    }

    /**
     * Se encarga de determinar las dimenciones de la imagen.
     *
     * @param bytesImagen Bytes de la imagen.
     * @return Array con el alto y el ancho de la imagen.
     * @throws IOException En caso de no poder leer la imagen
     */
    public static int[] obtenerDimensionesImagen(final byte[] bytesImagen) throws IOException {
        BufferedImage sourceImage = obtenerImagen(bytesImagen);
        return obtenerDimensionesImagen(sourceImage);
    }

    /**
     * Se encarga de determinar las dimenciones de la imagen.
     *
     * @param sourceImage La imagen.
     * @return Array con el alto y el ancho de la imagen.
     * @throws IOException En caso de no poder leer la imagen
     */
    public static int[] obtenerDimensionesImagen(final BufferedImage sourceImage) throws IOException {
        int[] resultado = new int[2];
        resultado[0] = sourceImage.getWidth();
        resultado[1] = sourceImage.getHeight();
        return resultado;
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada.
     *
     * @param bytesImagen Los bytes de la imagen
     * @return true si el alto y el ancho son iguales
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final byte[] bytesImagen) throws IOException {
        BufferedImage sourceImage = obtenerImagen(bytesImagen);
        return verificarEsImagenCuadrada(sourceImage);
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada.
     *
     * @param imagen La imagen actual
     * @return true si el alto y el ancho son iguales
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final BufferedImage imagen) throws IOException {
        int[] dimensiones = obtenerDimensionesImagen(imagen);
        return dimensiones[0] == dimensiones[1];
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada y que su tamaño se a igual al tamanio pasado como parametro.
     *
     * @param bytesImagen Los bytes de la imagen
     * @param tamanio El tamanio que debe tener la imagen.
     * @return true si el alto y el ancho son iguales y el tamaño de la imagen es igual al tamaño pasado como parametro
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final byte[] bytesImagen, final int tamanio) throws IOException {
        BufferedImage sourceImage = obtenerImagen(bytesImagen);
        return verificarEsImagenCuadrada(sourceImage, tamanio);
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada y que su tamaño se a igual al tamanio pasado como parametro.
     *
     * @param imagen Imagen
     * @param tamanio El tamanio que debe tener la imagen.
     * @return true si el alto y el ancho son iguales y el tamaño de la imagen es igual al tamaño pasado como parametro
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final BufferedImage imagen, final int tamanio) throws IOException {
        int[] dimensiones = obtenerDimensionesImagen(imagen);
        return dimensiones[0] == dimensiones[1] && dimensiones[0] == tamanio;
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada y que su tamaño se a igual al tamanio pasado como parametro.
     *
     * @param bytesImagen Los bytes de la imagen
     * @param tamanioMinimo El tamanio que puede tener la imagen.
     * @param tamanioMaximo Tamaño máximo que pude tener la imagen.
     * @return true si el alto y el ancho son iguales y el tamaño de la imagen esta entre el tamaño maximo y tamaño
     * minimo
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final byte[] bytesImagen, final int tamanioMinimo,
            final int tamanioMaximo)
            throws IOException {
        BufferedImage imagen = obtenerImagen(bytesImagen);
        return verificarEsImagenCuadrada(imagen, tamanioMinimo, tamanioMaximo);
    }

    /**
     * Se encarga de verificar si la imagen es cuadrada y que su tamaño sea igual al tamanio pasado como parametro.
     *
     * @param imagen La imagen
     * @param tamanioMinimo El tamanio que puede tener la imagen.
     * @param tamanioMaximo Tamaño máximo que pude tener la imagen.
     * @return true si el alto y el ancho son iguales y el tamaño de la imagen esta entre el tamaño maximo y tamaño
     * minimo
     * @throws IOException En caso de no poder leer correctamente los bytes de la imagen
     */
    public static boolean verificarEsImagenCuadrada(final BufferedImage imagen, final int tamanioMinimo,
            final int tamanioMaximo)
            throws IOException {
        int[] dimensiones = obtenerDimensionesImagen(imagen);
        return dimensiones[0] == dimensiones[1] && dimensiones[0] >= tamanioMinimo && dimensiones[0] <= tamanioMaximo;
    }

    /**
     * Verifica si el alto y el ancho de la imagen son iguales a sus correspondientes valores pasados como parametro.
     *
     * @param imagen La imagen
     * @param alto El alto que debe tener la imagen
     * @param ancho El ancho que debe tener la imagen.
     * @return True si el alto y el ancho de la imagen son iguales a los correspondientes valores pasados como
     * parametro.
     * @throws IOException En caso de no poder leer los bytes de la imagen
     */
    public static boolean verificarTamanioImagen(final BufferedImage imagen, final int ancho, final int alto)
            throws
            IOException {
        int[] dimensiones = obtenerDimensionesImagen(imagen);
        return dimensiones[0] == ancho && dimensiones[1] == alto;
    }

    /**
     * Verifica si el alto y el ancho de la imagen son iguales a sus correspondientes valores pasados como parametro.
     *
     * @param bytesImagen Bytes de la imagen
     * @param alto El alto que debe tener la imagen
     * @param ancho El ancho que debe tener la imagen.
     * @return True si el alto y el ancho de la imagen son iguales a los correspondientes valores pasados como
     * parametro.
     * @throws IOException En caso de no poder leer los bytes de la imagen
     */
    public static boolean verificarTamanioImagen(final byte[] bytesImagen, final int ancho, final int alto) throws
            IOException {
        BufferedImage imagen = obtenerImagen(bytesImagen);
        return verificarTamanioImagen(imagen, ancho, alto);
    }

    /**
     * Se encarga de determinar si el ancho y alto de la imagen se encuentra dentro de los rangos pasados como
     * parametro.
     *
     * @param bytesImagen Bytes de la imagen.
     * @param anchoMinimo Ancho minimo de la imagen
     * @param anchoMaximo Ancho maximo de la imagen
     * @param altoMinimo Alto minimo de la imagen
     * @param altoMaximo Alto maximo de la imagen
     * @return true si el tamaño de la imagen se encuentre en el rengo especificado.
     * @throws IOException En caso de no poder recuperar los bytes de la imagen
     */
    public static boolean verificarTamanioImagen(final BufferedImage bytesImagen, final int anchoMinimo,
            final int anchoMaximo,
            final int altoMinimo, final int altoMaximo) throws IOException {
        int[] dimensiones = obtenerDimensionesImagen(bytesImagen);
        return dimensiones[0] >= anchoMinimo && dimensiones[0] <= anchoMaximo && dimensiones[1] >= altoMinimo
                && dimensiones[1] <= altoMaximo;


    }

    /**
     * Se encarga de determinar si el ancho y alto de la imagen se encuentra dentro de los rangos pasados como
     * parametro.
     *
     * @param bytesImagen Bytes de la imagen.
     * @param anchoMinimo Ancho minimo de la imagen
     * @param anchoMaximo Ancho maximo de la imagen
     * @param altoMinimo Alto minimo de la imagen
     * @param altoMaximo Alto maximo de la imagen
     * @return true si el tamaño de la imagen se encuentre en el rengo especificado.
     * @throws IOException En caso de no poder recuperar los bytes de la imagen
     */
    public static boolean verificarTamanioImagen(final byte[] bytesImagen, final int anchoMinimo,
            final int anchoMaximo,
            final int altoMinimo, final int altoMaximo) throws IOException {
        BufferedImage imagen = obtenerImagen(bytesImagen);
        return verificarTamanioImagen(imagen, anchoMinimo, anchoMaximo, altoMinimo, altoMaximo);


    }
}
