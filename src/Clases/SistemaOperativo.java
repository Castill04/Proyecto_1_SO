/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Ignacio
 */
public class SistemaOperativo {
    private Scheduler scheduler;
    private Cola<Proceso> procesosListos;
    private Cola<Proceso> procesosBloqueados;
    private boolean ejecutando;
    private int cicloRelojGlobal = 0;
    private int duraciónCiclo = 0;

    public SistemaOperativo(Scheduler scheduler, Cola<Proceso> procesosL, Cola<Proceso> procesosB) {
        this.scheduler = scheduler;
        this.procesosListos = procesosL;
        this.procesosBloqueados = procesosB;
        this.ejecutando = true;
    }

    public void setScheduler(Scheduler nuevoScheduler) {
        System.out.println("🔄 Cambiando política de planificación...");
        this.scheduler = nuevoScheduler;
    }
    
    public void reanudarProceso(Proceso proceso) {
        proceso.detener();
        procesosListos.enqueue(proceso);
        System.out.println("🔄 Proceso " + proceso.getNombre() + " reinsertado en la cola de listos.");
    }

    private void iniciarCicloReloj() {
        new Thread(() -> {
            while (ejecutando) {
                try {
                    Thread.sleep(1000); 
                    cicloRelojGlobal++; 
                    System.out.println("⏳ Ciclo de Reloj Global: " + cicloRelojGlobal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }    
    
    public void manejarProcesosBloqueados(Proceso proceso) {
        procesosBloqueados.enqueue(proceso);
        new Thread(() -> {
            while (true) {
                if (!procesosBloqueados.isEmpty()) {
                    try {
                        Thread.sleep(proceso.getIoCompletionCycle() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    proceso.desbloquear();
                    procesosBloqueados.dequeue();
                    reanudarProceso(proceso);
                    System.out.println("🔄 Proceso " + proceso.getNombre() + " desbloqueado y movido a listos.");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void iniciar() {
        iniciarCicloReloj();
        new Thread(() -> {
            while (ejecutando) {
                scheduler.asignarProceso();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public void actualizarCPUs(int nuevoNumCPUs) {
        System.out.println("🔄 Actualizando número de CPUs a: " + nuevoNumCPUs);
        this.scheduler.actualizarCPUs(nuevoNumCPUs);
    }

    public void detener() {
        this.ejecutando = false;
    }

    public Cola<Proceso> getProcesosListos() {
        return procesosListos;
    }

    public Cola<Proceso> getProcesosBloqueados() {
        return procesosBloqueados;
    }

    public Lista<Proceso> getProcesosListosL() {
        return procesosListos.toLista();
    }

    public Lista<Proceso> getProcesosBloqueadosL() {
        return procesosBloqueados.toLista();
    }    

    public int getCicloRelojGlobal() {
        return cicloRelojGlobal;
    }

    public void setCicloRelojGlobal(int cicloRelojGlobal) {
        this.cicloRelojGlobal = cicloRelojGlobal;
    }

    public int getDuraciónCiclo() {
        return duraciónCiclo;
    }

    public void setDuraciónCiclo(int duraciónCiclo) {
        this.duraciónCiclo = duraciónCiclo;
    }

    public void setProcesosListos(Cola<Proceso> procesosListos) {
        this.procesosListos = procesosListos;
    }
}
