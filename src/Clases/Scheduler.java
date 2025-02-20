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
    private String policy;
    private int timeQuantum;

    public Scheduler(Cola<Proceso> readyQueue) {
        this.readyQueue = readyQueue;
        
    }

    public void addProcess(Proceso process) {
        readyQueue.enqueue(process);
    }
    
    public Proceso getNextProcess() {
        switch (policy) {
            case "FCFS":
                return getFCFSProcess();
            case "SJF":
                return getSJFProcess();
            case "RR":
                return getRRProcess();
            case "HRRN":
                return getHRRNProcess();
            case "SPN":
                return getSPNProcess();
            default:
                throw new IllegalArgumentException("Unknown scheduling policy: " + policy);
        }
    }
    
    

    private Proceso getFCFSProcess() {
        return readyQueue.dequeue();
    }

    private Proceso getSJFProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        // Reordenar por la cantidad de jobs restantes
        reorderQueueByInstructions();

        return readyQueue.dequeue();
    }

    private Proceso getRRProcess() {
        Proceso process = readyQueue.dequeue();
        if (process != null) {
            readyQueue.enqueue(process);
        }
        return process;
    }

    private Proceso getHRRNProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        // Reordena la cola en funcion de su tiempo de respuesta
        reorderQueueByResponseRatio();

        return readyQueue.dequeue();
    }

    private double calculateResponseRatio(Proceso process) {
        return (double) (process.getTiempoespera()+ process.getInstruccionesRestantes()) / process.getInstruccionesRestantes();
    }

    private Proceso getSPNProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        // Reordena la cola en funcion de su cantidad de instrucciones
        reorderQueueByInstructions();

        return readyQueue.dequeue();
    }

    private void reorderQueueByInstructions() {
        if (readyQueue.isEmpty()) {
            return;
        }

        // Convertir la cola en un array para reordenarlo
        Proceso[] processes = new Proceso[readyQueue.getSize()];
        int index = 0;
        while (!readyQueue.isEmpty()) {
            processes[index++] = readyQueue.dequeue();
        }

        // Se reordena 
        for (int i = 1; i < processes.length; i++) {
            Proceso key = processes[i];
            int j = i - 1;
            while (j >= 0 && processes[j].getInstruccionesRestantes() > key.getInstruccionesRestantes()) {
                processes[j + 1] = processes[j];
                j = j - 1;
            }
            processes[j + 1] = key;
        }

        // Se vuelve a convertir en una cola
        for (Proceso process : processes) {
            readyQueue.enqueue(process);
        }
    }

    private void reorderQueueByResponseRatio() {
        if (readyQueue.isEmpty()) {
            return;
        }

        // Convertir la cola en un array para reordenarlo
        Proceso[] processes = new Proceso[readyQueue.getSize()];
        int index = 0;
        while (!readyQueue.isEmpty()) {
            processes[index++] = readyQueue.dequeue();
        }

        // Se reordena 
        for (int i = 1; i < processes.length; i++) {
            Proceso key = processes[i];
            int j = i - 1;
            while (j >= 0 && calculateResponseRatio(processes[j]) < calculateResponseRatio(key)) {
                processes[j + 1] = processes[j];
                j = j - 1;
            }
            processes[j + 1] = key;
        }

        // Se vuelve a convertir en una cola
        for (Proceso process : processes) {
            readyQueue.enqueue(process);
        }
    }

}
