package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author casti
 */

public class PoliticaFCFSS extends Scheduler{
    public PoliticaFCFSS(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
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
