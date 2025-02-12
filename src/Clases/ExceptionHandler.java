/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class ExceptionHandler {

    public class ExcepcionHandler implements Runnable {
    private Proceso proceso;
    private CPU cpu;

    public ExcepcionHandler(Proceso proceso, CPU cpu) {
        this.proceso = proceso;
        this.cpu = cpu;
    }

    @Override
    public void run() {
        // Manejar la excepción y regresar al CPU correspondiente
    }
}
    
}
