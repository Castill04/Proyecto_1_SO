/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaSJF extends Scheduler{
    
    public PoliticaSJF(int numCPUs, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        super(numCPUs, procesosL, procesosB);
    }

    @Override
    public void asignarProceso() {
        reordenarColaPorInstruccionesRestantes();

        if (!procesosListos.isEmpty()) {
            Proceso procesoSeleccionado = procesosListos.dequeue();

            CPU cpuLibre = obtenerCPUDisponible();

            if (cpuLibre != null) {
                procesoSeleccionado.asignarCPU(cpuLibre);
            } else {
                procesosListos.enqueue(procesoSeleccionado);
            }
        }
    }

    private void reordenarColaPorInstruccionesRestantes() {
        Lista<Proceso> listaDeProcesos = procesosListos.toLista();


        for (int i = 0; i < listaDeProcesos.size(); i++) {
            for (int j = i + 1; j < listaDeProcesos.size(); j++) {
                if (listaDeProcesos.get(i).getInstruccionesRestantes() > listaDeProcesos.get(j).getInstruccionesRestantes()) {
                    Proceso temp = listaDeProcesos.get(i);
                    listaDeProcesos.set(i, listaDeProcesos.get(j));
                    listaDeProcesos.set(j, temp);
                }
            }
        }

        procesosListos = new Cola<>();
        for (int i = 0; i < listaDeProcesos.size(); i++) {
            procesosListos.enqueue(listaDeProcesos.get(i));
        }
    }
}
    
