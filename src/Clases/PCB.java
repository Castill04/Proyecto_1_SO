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
    private int id; 
    private String estado; 
    private int programCounter; 
    private int mar;  
    private int totalInstrucciones; 
    private Proceso proceso;  

    public PCB(int id, Proceso proceso) {
        this.id = id;
        this.proceso = proceso;
        this.estado = "Ready"; 
        this.programCounter = 0;
        this.mar = 0;
        this.totalInstrucciones = proceso.getInstrucciones();
    }

    public int getId() {
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

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
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
