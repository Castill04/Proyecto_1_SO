/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaSRT extends Scheduler{
    
    public PoliticaSRT(Cola<Proceso> procesosL, Cola<Proceso> procesosB, Cola<Proceso> procesosT) {
        super(procesosL, procesosB, procesosT);
    }

    @Override
    public void asignarProceso() {
        if (procesosListos.isEmpty()) {
            return;
        }

        reorganizarColaPorSRT();

        for (CPU cpu : cpus) {
            if (procesosListos.isEmpty()) {
                break; 
            }

            Proceso procesoConMenorTiempoRestante = procesosListos.peek();
            Proceso procesoActual = cpu.getProcesoActual(); 

            if (procesoActual == null) {
                procesosListos.dequeue();
                cpu.ejecutarProceso(procesoConMenorTiempoRestante);
            } else if (procesoConMenorTiempoRestante.getInstruccionesRestantes() < procesoActual.getInstruccionesRestantes()) {
                procesoActual.detener();
                procesosListos.enqueue(procesoActual);
                procesosListos.dequeue();
                cpu.ejecutarProceso(procesoConMenorTiempoRestante);
            }
        }
    }

    private void reorganizarColaPorSRT() {
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
    
