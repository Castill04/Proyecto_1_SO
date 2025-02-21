/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class Lista<T> {
    private Nodo<T> head;
    private int size;

    public Lista() {
        this.head = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T data) {
        Nodo<T> newNode = new Nodo<>(data);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    public T get(int index) {
        Nodo<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current.getData();
            }
            count++;
            current = current.getNext();
        }
        return null;
    }

    public int size() {
        return size;
    }
    
    public void set(int index, T data) {
        Nodo<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                current.setData(data);
                return;
            }
            count++;
            current = current.getNext();
        }
    }
    
    public void sort(Comparator<? super T> comparator) {
        if (size > 1) {
            boolean wasChanged;
            do {
                Nodo<T> current = head;
                Nodo<T> previous = null;
                Nodo<T> next = head.getNext();
                wasChanged = false;
                while (next != null) {
                    if (comparator.compare(current.getData(), next.getData()) > 0) {
                        wasChanged = true;
                        if (previous != null) {
                            Nodo<T> sig = next.getNext();
                            previous.setNext(next);
                            next.setNext(current);
                            current.setNext(sig);
                        } else {
                            Nodo<T> sig = next.getNext();
                            head = next;
                            next.setNext(current);
                            current.setNext(sig);
                        }
                        previous = next;
                        next = current.getNext();
                    } else {
                        previous = current;
                        current = next;
                        next = next.getNext();
                    }
                }
            } while (wasChanged);
        }
    }
}
