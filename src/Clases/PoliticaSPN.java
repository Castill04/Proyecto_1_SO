/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaSPN extends Scheduler{
    
    public PoliticaSPN(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        super(numCPUs, procesosL, procesosB);
    }

    @Override
    public void asignarProceso() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
