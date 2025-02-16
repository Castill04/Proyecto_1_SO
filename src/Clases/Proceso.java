/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Ignacio
 */
public class Proceso extends Thread {
    private String nombre;
    private int instrucciones;
    private Boolean esCpuBound;
    private int ciclosExcepcion;
    private int ciclosEspera;
    private int programCounter = 0;

    public Proceso(String nombre, int instrucciones, Boolean esCpuBound, int ciclosExcepcion, int ciclosEspera) {
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.esCpuBound = esCpuBound;
        this.ciclosExcepcion = ciclosExcepcion;
        this.ciclosEspera = ciclosEspera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(int instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Boolean getEsCpuBound() {
        return esCpuBound;
    }

    public void setEsCpuBound(Boolean esCpuBound) {
        this.esCpuBound = esCpuBound;
    }

    public int getCiclosExcepcion() {
        return ciclosExcepcion;
    }

    public void setCiclosExcepcion(int ciclosExcepcion) {
        this.ciclosExcepcion = ciclosExcepcion;
    }

    public int getCiclosEspera() {
        return ciclosEspera;
    }

    public void setCiclosEspera(int ciclosEspera) {
        this.ciclosEspera = ciclosEspera;
    }
    
    
    
}
