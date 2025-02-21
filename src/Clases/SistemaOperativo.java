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
    private Cola<Proceso> procesosTerminados;
    private Cola<Proceso> procesosEjecucion;
    private boolean ejecutando;
    private int cicloRelojGlobal = 0;
    private int duraciónCiclo = 0;
    private CPU[] cpus;
    private Thread relojThread;

    public SistemaOperativo(Scheduler scheduler, Cola<Proceso> procesosL, Cola<Proceso> procesosB, Cola<Proceso> procesosT, Cola<Proceso> procesosE, int numCPUs) {
        this.scheduler = scheduler;
        this.procesosListos = procesosL;
        this.procesosBloqueados = procesosB;
        this.procesosTerminados = procesosT;
        this.procesosEjecucion = procesosE;
        this.ejecutando = true;
        this.cpus = new CPU[numCPUs];
        for (int i = 0; i < numCPUs; i++) {
            cpus[i] = new CPU(i);
        }
        this.scheduler.setCPUs(cpus);
        iniciarCicloReloj();
    }

    public void setScheduler(Scheduler nuevoScheduler) {
        System.out.println("🔄 Cambiando política de planificación...");
        this.scheduler = nuevoScheduler;
        this.scheduler.setCPUs(cpus);
    }

    public void reanudarProceso(Proceso proceso) {
        proceso.detener();
        procesosListos.enqueue(proceso);
        System.out.println("🔄 Proceso " + proceso.getNombre() + " reinsertado en la cola de listos.");
    }

    private void iniciarCicloReloj() {
        if (relojThread == null || !relojThread.isAlive()) {
            relojThread = new Thread(() -> {
                while (ejecutando) {
                    try {
                        Thread.sleep(duraciónCiclo);
                        cicloRelojGlobal++;
                        System.out.println("⏳ Ciclo de Reloj Global: " + cicloRelojGlobal);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            relojThread.start();
        }
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
        ejecutando = true;
        iniciarCicloReloj();
        new Thread(() -> {
            while (ejecutando) {
                scheduler.asignarProceso();
                try {
                    Thread.sleep(duraciónCiclo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void actualizarCPUs(int nuevoNumCPUs) {
        if (nuevoNumCPUs > cpus.length) {
            CPU[] nuevasCPUs = new CPU[nuevoNumCPUs];
            System.arraycopy(cpus, 0, nuevasCPUs, 0, cpus.length);
            for (int i = cpus.length; i < nuevoNumCPUs; i++) {
                nuevasCPUs[i] = new CPU(i);
            }
            cpus = nuevasCPUs;
        } else if (nuevoNumCPUs < cpus.length) {
            for (int i = nuevoNumCPUs; i < cpus.length; i++) {
                CPU cpu = cpus[i];
                if (cpu.getProcesoActual() != null) {
                    procesosListos.enqueue(cpu.getProcesoActual());
                }
            }
            CPU[] nuevasCPUs = new CPU[nuevoNumCPUs];
            System.arraycopy(cpus, 0, nuevasCPUs, 0, nuevoNumCPUs);
            cpus = nuevasCPUs;
        }
        scheduler.setCPUs(cpus);
    }

    public void detener() {
        this.ejecutando = false;
        for (CPU cpu : cpus) {
            if (cpu.getProcesoActual() != null) {
                cpu.getProcesoActual().detener();
            }
        }
    }
    
    public void cambiarPolitica(String seleccion) {
        detener();

        switch (seleccion) {
            case "FCFS":
                setScheduler(new PoliticaFCFSS(procesosListos, procesosBloqueados, procesosTerminados));
                break;
            case "Round Robin":
                setScheduler(new PoliticaRR(procesosListos, procesosBloqueados, procesosTerminados)); 
                break;
            case "SRT":
                setScheduler(new PoliticaSRT(procesosListos, procesosBloqueados, procesosTerminados));
                break;
            case "HRRN":
                setScheduler(new PoliticaHRRN(procesosListos, procesosBloqueados, procesosTerminados));
                break;
            case "SPN":
                setScheduler(new PoliticaSPN(procesosListos, procesosBloqueados, procesosTerminados));
                break;
            default:
                throw new IllegalArgumentException("Política de planificación no reconocida: " + seleccion);
        }

        System.out.println("🔄 Cambiada política de planificación a: " + seleccion);
        iniciar();
    }
    
   public void procesosTerminados(Proceso proceso){
       procesosTerminados.enqueue(proceso);
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

    public Scheduler getScheduler() {
        return scheduler;
    }
    
    public CPU[] getCPUs() {
        return cpus;
    }
    
    public Lista<Proceso> getProcesosTerminadosL() {
        return procesosTerminados.toLista();
    }

    public Cola<Proceso> getProcesosTerminados() {
        return procesosTerminados;
    }
    
}
