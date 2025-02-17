package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author casti
 */
public class PlanificadorFCFSS extends Scheduler{
    
    public PlanificadorFCFSS(Cola<Proceso> readyQueue) {
        super(readyQueue);
    }

    @Override
    public Proceso getNextProcess() {
        return readyQueue.dequeue();
    }
}
