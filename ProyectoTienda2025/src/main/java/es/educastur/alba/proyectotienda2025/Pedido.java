/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.educastur.alba.proyectotienda2025;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author alu01d
 */
public class Pedido implements Serializable, Comparable <Pedido>{
    private String idPedido;
    private Cliente clientePedido;
    private LocalDate fechaPedido;
    ArrayList<LineaPedido> CestaCompra;
    
    public Pedido(String idPedido, Cliente clientePedido, LocalDate fechaPedido, ArrayList<LineaPedido> CestaCompra) {
        this.idPedido = idPedido;
        this.clientePedido = clientePedido;
        this.fechaPedido = fechaPedido;
        this.CestaCompra = CestaCompra;
}

    public String getIdPedido() {
        return idPedido;
    }

    public Cliente getClientePedido() {
        return clientePedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public ArrayList<LineaPedido> getCestaCompra() {
        return CestaCompra;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public void setClientePedido(Cliente clientePedido) {
        this.clientePedido = clientePedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setCestaCompra(ArrayList<LineaPedido> CestaCompra) {
        this.CestaCompra = CestaCompra;
    }
        
    @Override
    public String toString() {
        return idPedido + "-" + clientePedido + "-" + fechaPedido + "-" + CestaCompra;
    }
    
    @Override
    public int compareTo(Pedido p) {
         return this.fechaPedido.compareTo(p.getFechaPedido());
    }
    
    /** Para ordenar por ID pedido
    @Override
    public int compareTo(Pedido p) {
         return this.idPedido.compareTo(p.getIdPedido());
    }
    */
}
