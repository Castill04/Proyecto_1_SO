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
    public PoliticaHRRN(Cola<Proceso> procesosL, Cola<Proceso> procesosB, Cola<Proceso> procesosT) {
        super(procesosL, procesosB, procesosT);
    }

    private double calcularRespuesta(Proceso proceso) {
        return (double) (proceso.getTiempoespera() + proceso.getInstruccionesRestantes()) / proceso.getInstruccionesRestantes();
    }

    @Override
    public void asignarProceso() {
        if (procesosListos.isEmpty()) {
            return;
        }

        reorganizarColaPorHRRN();

        CPU cpuDisponible = obtenerCPUDisponible();
        if (cpuDisponible != null) {
            Proceso mejorProceso = procesosListos.dequeue();
            cpuDisponible.ejecutarProceso(mejorProceso);
        }
    }

    private void reorganizarColaPorHRRN() {
        Lista<Proceso> listaProcesos = procesosListos.toLista();
        listaProcesos.sort(new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                return Double.compare(calcularRespuesta(p2), calcularRespuesta(p1));
            }
        });
        procesosListos.clear();
        for (int i = 0; i < listaProcesos.size(); i++) {
            procesosListos.enqueue(listaProcesos.get(i));
        }
    }
}
