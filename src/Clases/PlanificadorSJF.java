/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class PlanificadorSJF extends Scheduler{
    
    public PlanificadorSJF(Cola<Proceso> readyQueue) {
        super(readyQueue);
    }

    @Override
    public Proceso getNextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        
        Nodo<Proceso> currentNode = readyQueue.getFront();
        Nodo<Proceso> shortestNode = currentNode;
        while (currentNode != null) {
            if (currentNode.getData().getInstruccionesRestantes() < shortestNode.getData().getInstruccionesRestantes()) {
                shortestNode = currentNode;
            }
            currentNode = currentNode.getNext();
        }

        
        if (shortestNode == readyQueue.getFront()) {
            return readyQueue.dequeue();
        } else {
            return removeNode(shortestNode);
        }
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
