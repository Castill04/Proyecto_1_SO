/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaRR extends Scheduler{
    private int quantum = 5;

    public PoliticaRR(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        super(numCPUs, procesosL, procesosB);
    }

    @Override
    public void asignarProceso() {
        while (!procesosListos.isEmpty()) {
            CPU cpuLibre = obtenerCPUDisponible();

            if (cpuLibre != null) {
                Proceso proceso = procesosListos.dequeue();
                proceso.asignarCPU(cpuLibre);
            } else {
                break;
            }
        }
    }
}
