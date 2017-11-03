/*
 *  UtilZip.java
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
 *  Jun 27, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

import java.io.*;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class UtilZip {

    public static byte[] MAGIC = {'P', 'K', 0x3, 0x4};

    /**
     * Constructor sin argumentos.
     */
    public UtilZip() {
        super();

    }

    /**
     * The method to test if a input stream is a zip archive.
     *
     * @param in the input stream to test.
     * @return
     */
    public static boolean isZipStream(InputStream in) {
        if (!in.markSupported()) {
            in = new BufferedInputStream(in);
        }
        boolean isZip = true;
        try {
            in.mark(MAGIC.length);
            for (int i = 0; i < MAGIC.length; i++) {
                if (MAGIC[i] != (byte) in.read()) {
                    isZip = false;
                    break;
                }
            }
            in.reset();
        } catch (IOException e) {
            isZip = false;
        }
        return isZip;
    }

    /**
     * Test if a file is a zip file.
     *
     * @param f the file to test.
     * @return
     */
    public static boolean isZipFile(File f) {

        boolean isZip = true;
        byte[] buffer = new byte[MAGIC.length];
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            raf.readFully(buffer);
            for (int i = 0; i < MAGIC.length; i++) {
                if (buffer[i] != MAGIC[i]) {
                    isZip = false;
                    break;
                }
            }
            raf.close();
        } catch (Throwable e) {
            isZip = false;
        }
        return isZip;
    }
}
