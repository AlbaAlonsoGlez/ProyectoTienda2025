/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.educastur.alba.proyectotienda2025;

/**
 *
 * @author alu01d
 */
public class StockInsuficiente extends Exception{
    public StockInsuficiente(String cadena) {
        super (cadena);
    }
}
