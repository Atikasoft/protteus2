/**
 * PROGRAMADO POR : CUEVAS MEDINA JHONNY ALEXANDER 09/11/2006 Clase para describir un numero
 */
package ec.com.atikasoft.proteus.util;

/**
 *
 * @author victor
 */
public class CnvNumsLets {

    /**
     * NUMEROS EN PALABRAS PARA LAS UNIDADES.
     */
    private final String unidad[] = {"Cero", "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete",
        "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "DieciSeis",
        "DieciSiete", "DieciOcho", "DieciNueve", "Veinte"};

    /**
     * NUMEROS EN PALABRAS PARA LAS DECENAS.
     */
    private final String decena[] = {"Veinti", "Treinta", "Cuarenta", "Cincuenta", "Sesenta", "Setenta",
        "Ochenta", "Noventa"};

    /**
     * NUMEROS EN PALABRAS PARA LAS CENTENAS.
     */
    private final String centena[] = {"Cien", "DosCientos", "TresCientos", "CuatroCientos", "Quinientos",
        "SeisCientos", "SeteCientos", "OchoCientos", "NoveCientos", "Mil", "Un Millón", "Millones", "Un Billón",
        "Billones"};

    private long valororiginal = 0;

    public CnvNumsLets() {
        //constructoir vacio
    }

    private String getUnidad(final long Numero) {
        String aux = "";
        for (int p = 0; p <= 20; p++) {
            if (Numero == p) {
                aux = unidad[p] + " ";
                /*
                 * if( Numero ==1 & Valororiginal > 1000 ) return("Un ");
                 */
                return aux;
            }
        }
        return " ";
    }

    private String getDecena(long Numero) {
        String aux = "";
        long pf = Numero % 10;
        long pi = (Numero / 10);
        int p = 0;
        boolean sal = false;
        //pf: parte final del numero,pi: parte inicial del numero
        while (p <= 8 & sal == false) {
            if (pi == p + 2) {
                aux = decena[p];
                sal = true;
            }
            p++;
        }
        if (pf == 0) {
            return aux + " ";
        }
        if (Numero > 20 & Numero < 30) {
            return (aux + getUnidad(pf) + " ");
        }
        return aux + " y " + getUnidad(pf) + " ";
    }

    private String getCentena(long Numero) {
        String aux = "", aux2 = "";
        long pf = Numero % 100;
        long pi = (Numero / 100);
        int p = 0;
        boolean sal = false;
        while (p <= 10 & sal == false) {
            if (pi == p + 1) {
                aux = centena[p];
                sal = true;
            }
            p++;
        }
        if (pf == 0) {
            return aux;
        }
        if (pf < 21) {
            aux2 = getUnidad(pf);
        } else {
            aux2 = getDecena(pf);
        }
        if (Numero < 200) {
            return (aux + "to " + aux2 + " ");
        } else {
            return (aux + " " + aux2 + " ");
        }
    }

    private String getMil(long Numero) {
        String aux = "", aux2 = "";
        long pf = Numero % 1000;
        long pi = (Numero / 1000);
        long p = 0;
        if (Numero == 1000) {
            return "MIL";
        }
        if (Numero > 1000 & Numero < 1999) {
            aux = centena[9] + " ";
        } else {
            aux = resolverIntervalo(pi) + centena[9] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String getMillon(long Numero) {
        String aux = "", aux2 = "";
        long pf = Numero % 1000000;
        long pi = (Numero / 1000000);
        long p = 0;
        if (Numero > 1000000 & Numero < 1999999) {
            aux = centena[10] + " ";
        } else {
            aux = resolverIntervalo(pi) + centena[11] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String getBillon(long Numero) {
        String aux = "", aux2 = "";
        long pf = Numero % 1000000000;
        long pi = (Numero / 1000000000);
        long p = 0;
        if (Numero > 1000000000 & Numero < 1999999999) {
            aux = centena[12] + " ";
        } else {
            aux = resolverIntervalo(pi) + centena[13] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String resolverIntervalo(long Numero) {
        if (Numero >= 0 & Numero <= 20) {
            return getUnidad(Numero);
        }
        if (Numero >= 21 & Numero <= 99) {
            return getDecena(Numero);
        }
        if (Numero >= 100 & Numero <= 999) {
            return getCentena(Numero);
        }
        if (Numero >= 1000 & Numero <= 999999) {
            return getMil(Numero);
        }
        if (Numero >= 1000000 & Numero <= 999999999) {
            return getMillon(Numero);
        }
        if (Numero >= 1000000000 & Numero <= 2000000000) {
            return getBillon(Numero);
        }
        return "<<El número esta fuera del rango>>";
    }

    public String toLetras(long numero) {
        valororiginal = numero;
        if (numero >= 0) {
            return (resolverIntervalo(numero));
        } else {
            return (" Menos " + resolverIntervalo(numero * -1));
        }
    }
}
