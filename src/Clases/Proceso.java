/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author casti
 */
public class Proceso {
    private int id;
    private String name;
    private int pc; // Program Counter
    private int mar; // Memory Address Register
    private int instructions;
    private boolean isCpuBound;
    private int ioExceptionCycle;
    private int ioCompletionCycle;
    private String status;

    public Proceso(int id, String name, int instructions, boolean isCpuBound, int ioExceptionCycle, int ioCompletionCycle) {
        this.id = id;
        this.name = name;
        this.pc = 0;
        this.mar = 0;
        this.instructions = instructions;
        this.isCpuBound = isCpuBound;
        this.ioExceptionCycle = ioExceptionCycle;
        this.ioCompletionCycle = ioCompletionCycle;
        this.status = "LISTO";
    }

    public void executeInstruction(long globalClock) {
        // Logica de ejecucion de una instruccion
        // Actualizar pc y mar
        pc++;
        mar++;

        // Verificar si esta limitado por E/S o por el CPU
        if (isCpuBound) {
            // Logica CPU
        } else {
            // Logica E/S
            if (globalClock % ioExceptionCycle == 0) {
                // Excepcion E/S
                status = "BLOQUEADO";
            }
        }

        // Verificar si el proceso ha terminado
        if (pc >= instructions) {
            status = "COMPLETADO";
        }
    }

    public boolean isCompleted() {
        return "COMPLETADO".equals(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int getInstructions() {
        return instructions;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public boolean isIsCpuBound() {
        return isCpuBound;
    }

    public void setIsCpuBound(boolean isCpuBound) {
        this.isCpuBound = isCpuBound;
    }

    public int getIoExceptionCycle() {
        return ioExceptionCycle;
    }

    public void setIoExceptionCycle(int ioExceptionCycle) {
        this.ioExceptionCycle = ioExceptionCycle;
    }

    public int getIoCompletionCycle() {
        return ioCompletionCycle;
    }

    public void setIoCompletionCycle(int ioCompletionCycle) {
        this.ioCompletionCycle = ioCompletionCycle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
