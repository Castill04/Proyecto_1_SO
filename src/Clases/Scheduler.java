/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public abstract class Scheduler {

    protected Cola<Proceso> readyQueue;

    public Scheduler(Cola<Proceso> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public abstract Proceso getNextProcess();

    public void addProcess(Proceso process) {
        readyQueue.enqueue(process);
    }

}
