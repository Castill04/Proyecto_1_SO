/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PlanificadorRR extends Scheduler{
    
    private int timeQuantum;
    private Cola<Proceso> rrQueue;

    public PlanificadorRR(Cola<Proceso> readyQueue, int timeQuantum) {
        super(readyQueue);
        this.timeQuantum = timeQuantum;
        this.rrQueue = new Cola<>();
        while (!readyQueue.isEmpty()) {
            rrQueue.enqueue(readyQueue.dequeue());
        }
    }

    @Override
    public Proceso getNextProcess() {
        Proceso process = rrQueue.dequeue();
        if (process != null) {
            rrQueue.enqueue(process);
        }
        return process;
    }
    
}
