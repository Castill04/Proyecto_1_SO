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
public class PCB {
    private long id; 
    private String estado; 
    private int programCounter; 
    private int mar;  
    private int totalInstrucciones;  
    private String nombre;

    public PCB(long id, int totalInstrucciones, String nombre) {
        this.id = id;
        this.estado = "Listo"; 
        this.programCounter = 0;
        this.mar = 0;
        this.totalInstrucciones = totalInstrucciones;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int getTotalInstrucciones() {
        return totalInstrucciones;
    }

    public void setTotalInstrucciones(int totalInstrucciones) {
        this.totalInstrucciones = totalInstrucciones;
    }
    
    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void incrementarPC() {
        if (programCounter < totalInstrucciones) {
            programCounter++;
            mar++;
        }
    }
}
