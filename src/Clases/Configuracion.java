/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


/**
 *
 * @author casti
 */
import java.io.*;

public class Configuracion implements Serializable {
    private int cycleDuration;
    private Lista<Procesador> processors;

    public Configuracion(int cycleDuration, Lista<Procesador> processors) {
        this.cycleDuration = cycleDuration;
        this.processors = processors;
    }

    public int getCycleDuration() {
        return cycleDuration;
    }

    public void setCycleDuration(int cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    public Lista<Procesador> getProcessors() {
        return processors;
    }

    public void setProcessors(Lista<Procesador> processors) {
        this.processors = processors;
    }

    public static Configuracion loadFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Configuracion) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
