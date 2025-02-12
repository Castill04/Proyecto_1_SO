/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;

/**
 *
 * @author casti
 */
public class Scheduler {

    private List<Proceso> colaListos;
    private String politica; // FCFS, SJF, Round Robin

    public void agregarProceso(Proceso proceso) {
        colaListos.add(proceso);
    }

    //public void Proceso siguienteProceso() {
        // Implementar lógica de planificación según la política
    //}

}
