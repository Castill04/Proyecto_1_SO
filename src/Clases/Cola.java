/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class Cola<T> {
    private Nodo<T> front;
    private Nodo<T> rear;
    private int size;

    public Cola() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T data) {
        Nodo<T> newNode = new Nodo<>(data);
        if (rear != null) {
            rear.setNext(newNode);
        }
        rear = newNode;
        if (front == null) {
            front = rear;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = front.getData();
        front = front.getNext();
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    public T peek() {
        return isEmpty() ? null : front.getData();
    }

    public int getSize() {
        return size;
    }

    public Nodo<T> getFront() {
        return front;
    }

    public void setFront(Nodo<T> front) {
        this.front = front;
    }

    public Nodo<T> getRear() {
        return rear;
    }

    public void setRear(Nodo<T> rear) {
        this.rear = rear;
    }
    
    public void decrementSize() {
        if (size > 0) {
            size--;
        }
    }
    
    public Lista<T> toLista() {
        Lista<T> lista = new Lista<>();
        Nodo<T> current = front;
        while (current != null) {
            lista.add(current.getData());
            current = current.getNext();
        }
        return lista;
    }
}
