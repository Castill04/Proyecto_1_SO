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
    
    public PoliticaSPN(Cola<Proceso> procesosL, Cola<Proceso> procesosB, Cola<Proceso> procesosT) {
        super(procesosL, procesosB, procesosT);
    }

    @Override
    public void asignarProceso() {
        if (procesosListos.isEmpty()) {
            return;
        }

        reorganizarColaPorSPN();

        for (CPU cpu : cpus) {
            if (procesosListos.isEmpty()) {
                break;
            }

            if (cpu.estaLibre()) { 
                Proceso procesoCorto = procesosListos.dequeue();
                cpu.ejecutarProceso(procesoCorto);
            }
        }
    }

    private void reorganizarColaPorSPN() {
        Lista<Proceso> listaProcesos = procesosListos.toLista();

        for (int i = 0; i < listaProcesos.size() - 1; i++) {
            for (int j = 0; j < listaProcesos.size() - i - 1; j++) {
                if (listaProcesos.get(j).getInstruccionesRestantes() > listaProcesos.get(j + 1).getInstruccionesRestantes()) {
                    Proceso temp = listaProcesos.get(j);
                    listaProcesos.set(j, listaProcesos.get(j + 1));
                    listaProcesos.set(j + 1, temp);
                }
            }
        }

        procesosListos.clear();
        for (int i = 0; i < listaProcesos.size(); i++) {
            procesosListos.enqueue(listaProcesos.get(i));
        }
    }
}
