/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaHRRN extends Scheduler {

    public PoliticaHRRN(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        super(numCPUs, procesosL, procesosB);
    }

    @Override
    public void asignarProceso() {
        if (!procesosListos.isEmpty()) {
            
            reordenarColaPorHRRN();
            Proceso procesoSeleccionado = procesosListos.dequeue();

            CPU cpuLibre = obtenerCPUDisponible();
            if (cpuLibre != null) {
                procesoSeleccionado.asignarCPU(cpuLibre);
            } else {
                procesosListos.enqueue(procesoSeleccionado);
            }
        }
    }

    public void reordenarColaPorHRRN() {
        Lista<Proceso> listaDeProcesos = procesosListos.toLista();

        for (int i = 0; i < listaDeProcesos.size(); i++) {
            for (int j = i + 1; j < listaDeProcesos.size(); j++) {
                Proceso p1 = listaDeProcesos.get(i);
                Proceso p2 = listaDeProcesos.get(j);

                int tiempoEspera1 = p1.getTiempoespera();
                int tiempoEjecucion1 = p1.getInstruccionesRestantes();
                double responseRatio1 = (double)(tiempoEspera1 + tiempoEjecucion1) / tiempoEjecucion1;

                int tiempoEspera2 = p2.getTiempoespera();
                int tiempoEjecucion2 = p2.getInstruccionesRestantes();
                double responseRatio2 = (double)(tiempoEspera2 + tiempoEjecucion2) / tiempoEjecucion2;

                if (responseRatio1 < responseRatio2) {
                    listaDeProcesos.set(i, p2);
                    listaDeProcesos.set(j, p1);
                }
            }
        }

        procesosListos = new Cola<>(); 
        for (int i = 0; i < listaDeProcesos.size(); i++) {
            procesosListos.enqueue(listaDeProcesos.get(i)); 
        }
    }
}
