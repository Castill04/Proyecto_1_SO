/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Listas de procesadores
        Lista<Procesador> processorList = new Lista<>();
        Simulador simulator = new Simulador(null);
        processorList.add(new Procesador(simulator)); // Add processors as needed
        processorList.add(new Procesador(simulator)); // Add processors as needed

        // Configuracion
        Configuracion config = new Configuracion(1000, processorList);
        simulator = new Simulador(config);

        // Interfaz (Aun no esta implementada)
        //EventQueue.invokeLater(() -> {
        //    SimulatorGUI ex = new SimulatorGUI(simulator);
        //    ex.setVisible(true);
        //});

        // Inicializar el procesador y los hilos
        for (int i = 0; i < processorList.size(); i++) {
            Procesador processor = processorList.get(i);
            processor.start();
        }
    }
    
}
