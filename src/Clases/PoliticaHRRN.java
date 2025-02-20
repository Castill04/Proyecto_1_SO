/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PoliticaHRRN extends Scheduler{
    
    public PoliticaHRRN(Cola<Proceso> readyQueue) {
        super(readyQueue);
    }

    @Override
    public Proceso getNextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        // Find the process with the highest response ratio
        Nodo<Proceso> currentNode = readyQueue.getFront();
        Nodo<Proceso> highestNode = currentNode;
        double highestResponseRatio = calculateResponseRatio(currentNode.getData());

        while (currentNode != null) {
            double currentResponseRatio = calculateResponseRatio(currentNode.getData());
            if (currentResponseRatio > highestResponseRatio) {
                highestNode = currentNode;
                highestResponseRatio = currentResponseRatio;
            }
            currentNode = currentNode.getNext();
        }

        // Remove the highest response ratio job from the queue
        if (highestNode == readyQueue.getFront()) {
            return readyQueue.dequeue();
        } else {
            return removeNode(highestNode);
        }
    }

    private double calculateResponseRatio(Proceso process) {
        return (double) (process.getTiempoespera()+ process.getInstruccionesRestantes()) / process.getInstruccionesRestantes();
    }

    private Proceso removeNode(Nodo<Proceso> node) {
        Nodo<Proceso> currentNode = readyQueue.getFront();
        Nodo<Proceso> previousNode = null;

        while (currentNode != null && currentNode != node) {
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if (currentNode == null) {
            return null;
        }

        if (previousNode != null) {
            previousNode.setNext(currentNode.getNext());
        }

        if (currentNode == readyQueue.getRear()) {
            readyQueue.setRear(previousNode);
        }

        readyQueue.decrementSize();
        return currentNode.getData();
    }
    
}
