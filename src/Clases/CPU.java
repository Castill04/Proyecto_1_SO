/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class CPU {
    
    private int id;
    private Proceso procesoActual;
    private boolean ejecutandoSO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proceso getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(Proceso procesoActual) {
        this.procesoActual = procesoActual;
    }

    public boolean isEjecutandoSO() {
        return ejecutandoSO;
    }

    public void setEjecutandoSO(boolean ejecutandoSO) {
        this.ejecutandoSO = ejecutandoSO;
    }
    
    

    
}
