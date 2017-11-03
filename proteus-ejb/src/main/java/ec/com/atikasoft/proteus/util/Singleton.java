/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.util;

/**
 *
 * @author henry
 */
public class Singleton {

    private static Singleton instance = null;

    private Singleton() {
        // Exists only to defeat instantiation.
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
