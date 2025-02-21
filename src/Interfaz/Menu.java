package Interfaz;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Clases.CPU;
import Clases.Cola;
import Clases.Lista;
import Clases.Configuracion;
import Clases.Proceso;
import Clases.Scheduler;
import Clases.SistemaOperativo;
import Clases.PoliticaRR;
import Clases.PoliticaFCFSS;
import Clases.PoliticaSRT;
import Clases.PoliticaHRRN;
import Clases.PoliticaSPN;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
/**
 *
 * @author Ignacio
 */
public class Menu extends javax.swing.JFrame {
    private DefaultTableModel modeloListos;
    private DefaultTableModel modeloBloqueados;
    private DefaultTableModel modeloTerminados;
    private DefaultTableModel modeloEnEjecucion;
    public Configuracion configuracion;
    private Scheduler scheduler;
    private SistemaOperativo sistemaOperativo;
    private int duracionCiclo;

    /**
     * Creates new form Menu
     */
    public Menu(SistemaOperativo sistemaOperativo, Scheduler scheduler) {
        this.sistemaOperativo = sistemaOperativo;
        this.scheduler = scheduler;
        configuracion = new Configuracion("parametros.txt");
        try {
            configuracion.verificarYCrearArchivo();
            int[] parametros = configuracion.leerParametros();
            
            // Los parámetros del archivo:
            // [0] - Duración del ciclo (ms)
            // [1] - Número de procesadores
            // [2] - Número de instrucciones
            // [3] - Tipo de proceso (1=CPU Bound, 0=I/O Bound)
            // [4] - Ciclos para excepción
            // [5] - Ciclos para completar excepción
            duracionCiclo = parametros[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        cargarParametrosDesdeTXT();
        configurarTablas();
        configurarSpinners();
    }
    
    private void configurarTablas() {
        modeloListos = new DefaultTableModel(new String[]{"ID", "Nombre", "Instrucciones", "Tipo"}, 0);
        tablaListos.setModel(modeloListos);

        modeloBloqueados = new DefaultTableModel(new String[]{"ID", "Nombre", "I/O Completion"}, 0);
        tablaBloqueados.setModel(modeloBloqueados);
        
        modeloTerminados = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaTerminados = new JTable(modeloTerminados);

        modeloEnEjecucion = new DefaultTableModel(new String[]{"ID", "Estado", "CPU"}, 0);
        tablaEjecucion = new JTable(modeloEnEjecucion);

        new javax.swing.Timer(sistemaOperativo.getDuraciónCiclo(), e -> actualizarTablas()).start();
        new javax.swing.Timer(sistemaOperativo.getDuraciónCiclo(), e -> actualizarCicloReloj()).start();
    }
    
   private void cargarConfiguracion() {
        try {
            int[] parametros = configuracion.leerParametros();
            txtDuracion.setText(String.valueOf(parametros[0]));
            txtProcesadores.setText(String.valueOf(parametros[1]));
            txtCantInstrucciones.setText(String.valueOf(parametros[2]));
            txtTipoProceso.setText(String.valueOf(parametros[3]));
            txtCiclosExcepcion.setText(String.valueOf(parametros[4]));
            txtCiclosCompletarExcepcion.setText(String.valueOf(parametros[5]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean configurar() {
        System.out.println(this.configuracion);
        try {
            int duracion = validarNumero(txtDuracion, "Duración del día");
            int procedadores = validarNumeroDeProcesadoresYTipoProceso(txtProcesadores, "Días entre entregas", 2, 3);
            int CantInstrucciones = validarNumero(txtCantInstrucciones, "Productores Placa Base");
            int TipoProceso = validarNumeroDeProcesadoresYTipoProceso(txtTipoProceso, "Productores CPU", 0, 1);
            int CiclosExcepcion = validarNumero(txtCiclosExcepcion, "Productores RAM");
            int CiclosCompletarExcepcion = validarNumero(txtCiclosCompletarExcepcion, "Productores Fuente de Poder");
            System.out.println(configuracion);
            configuracion.guardarParametros(duracion, procedadores, CantInstrucciones, TipoProceso, CiclosExcepcion, CiclosCompletarExcepcion);
            JOptionPane.showMessageDialog(this, "Parámetros guardados correctamente.");
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar parámetros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private int validarNumero(JTextField campo, String nombreCampo) throws NumberFormatException {
        int valor = Integer.parseInt(campo.getText());
        if (valor <= 0) {
            throw new NumberFormatException(nombreCampo + " debe ser un número positivo.");
        }
        return valor;
    }
    
    private int validarNumeroDeProcesadoresYTipoProceso(JTextField campo, String nombreCampo, int min, int max) throws NumberFormatException {
        int valor = Integer.parseInt(campo.getText().trim());

        if (valor < min || valor > max) {
            throw new NumberFormatException(nombreCampo + " debe ser un número entre " + min + " y " + max + ".");
        }

        return valor;
    }
    
    private void actualizarTablas() {
        scheduler.setProcesosTerminados(sistemaOperativo.getProcesosTerminados());
        
        modeloListos.setRowCount(0); 
        Lista<Proceso> listos = scheduler.getProcesosListosL();
        for (int i = 0; i < listos.size(); i++) {
            Proceso p = listos.get(i);
            modeloListos.addRow(new Object[]{p.getId(), p.getNombre(), p.getInstruccionesRestantes(), (p.isCpuBound() ? "CPU-Bound" : "I/O-Bound")});
        }

        modeloBloqueados.setRowCount(0);
        Lista<Proceso> bloqueados = scheduler.getProcesosBloqueadosL();
        for (int i = 0; i < bloqueados.size(); i++) {
            Proceso p = bloqueados.get(i);
            modeloBloqueados.addRow(new Object[]{p.getId(), p.getNombre(), p.getIoCompletionCycle()});
        }
        
        modeloTerminados.setRowCount(0);
        Lista<Proceso> terminados = scheduler.getProcesosTerminadosL();
        for (int i = 0; i < terminados.size(); i++) {
            Proceso p = terminados.get(i);
            modeloTerminados.addRow(new Object[]{p.getId(), p.getNombre()});
        }

        modeloEnEjecucion.setRowCount(0);
        CPU[] cpus = scheduler.getCPUs();
        for (CPU cpu : cpus) {
            Proceso p = cpu.getProcesoActual();
            if (p != null) {
                modeloEnEjecucion.addRow(new Object[]{p.getId(), "Ejecutando", "CPU " + cpu.getId()});
            }
        }
    }

    
    private void configurarComponentes() {
                String seleccion = (String) SeleccionP.getSelectedItem();
                sistemaOperativo.cambiarPolitica(seleccion);
                scheduler = sistemaOperativo.getScheduler();
                sistemaOperativo.iniciar();
    }
    
    private void actualizarCicloReloj() {
        if (sistemaOperativo != null) {
            int cicloActual = sistemaOperativo.getCicloRelojGlobal();
            lblCicloReloj.setText("" + cicloActual);
        }
    }
    
    private void cargarParametrosDesdeTXT() {
        try {
            Configuracion configuracion = new Configuracion("parametros.txt");
            int[] parametros = configuracion.leerParametros();

            spinnerDuracionCiclo.setValue(parametros[0]); 
            spinnerNumProcesadores.setValue(parametros[1]); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void configurarSpinners() {
        SpinnerNumberModel modeloDuracion = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        spinnerDuracionCiclo.setModel(modeloDuracion);

        SpinnerNumberModel modeloProcesadores = new SpinnerNumberModel(2, 2, 3, 1);
        spinnerNumProcesadores.setModel(modeloProcesadores);

        spinnerNumProcesadores.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int numProcesadores = (int) spinnerNumProcesadores.getValue();
                sistemaOperativo.actualizarCPUs(numProcesadores);
            }
        });

        spinnerDuracionCiclo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int duracionCiclo = (int) spinnerDuracionCiclo.getValue();
                sistemaOperativo.setDuraciónCiclo(duracionCiclo);
            }
        });

        cargarParametrosDesdeTXT();
    }
    
    private void actualizarCPUs(int numProcesadores) {
        sistemaOperativo.actualizarCPUs(numProcesadores);
        System.out.println("🔄 CPUs actualizados a " + numProcesadores);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConfigurar = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblCicloReloj = new javax.swing.JLabel();
        SeleccionP = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaListos = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaBloqueados = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        spinnerNumProcesadores = new javax.swing.JSpinner();
        spinnerDuracionCiclo = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        botonCambiarPolitica = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTerminados = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEjecucion = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textField2 = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textField3 = new java.awt.TextField();
        textField4 = new java.awt.TextField();
        jLabel9 = new javax.swing.JLabel();
        textField5 = new java.awt.TextField();
        button1 = new java.awt.Button();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCiclosCompletarExcepcion = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnConfigurar1 = new javax.swing.JButton();
        txtDuracion = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtProcesadores = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCantInstrucciones = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTipoProceso = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCiclosExcepcion = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();

        btnConfigurar.setText("Actualizar");
        btnConfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Ciclo de Reloj Global:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(400, 10, 170, 30);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Cola de listos");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 70, 110, 30);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Número Procesadores:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 420, 250, 50);

        lblCicloReloj.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(lblCicloReloj);
        lblCicloReloj.setBounds(580, 10, 70, 30);

        SeleccionP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "Round Robin", "HRRN", "SRT", "SPN" }));
        SeleccionP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionPActionPerformed(evt);
            }
        });
        jPanel1.add(SeleccionP);
        SeleccionP.setBounds(180, 360, 130, 30);

        tablaListos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tablaListos);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(10, 100, 410, 90);

        tablaBloqueados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablaBloqueados);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(10, 230, 410, 90);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Cola de bloqueados");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(30, 200, 160, 30);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Sistema Operativo");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(10, 10, 160, 30);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Procesos Terminados");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(480, 270, 180, 30);
        jPanel1.add(spinnerNumProcesadores);
        spinnerNumProcesadores.setBounds(220, 430, 130, 30);
        jPanel1.add(spinnerDuracionCiclo);
        spinnerDuracionCiclo.setBounds(220, 400, 130, 30);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Política Planificación:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 360, 170, 25);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Duración Ciclo Instrucción:");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(10, 380, 250, 60);

        botonCambiarPolitica.setText("Cambiar Política");
        botonCambiarPolitica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCambiarPoliticaActionPerformed(evt);
            }
        });
        jPanel1.add(botonCambiarPolitica);
        botonCambiarPolitica.setBounds(320, 360, 120, 30);

        tablaTerminados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaTerminados);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(440, 300, 250, 160);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Procesos activos");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(490, 70, 170, 30);

        tablaEjecucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaEjecucion);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(440, 100, 250, 160);

        jTabbedPane2.addTab("Dashboard", jPanel1);

        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Crear Proceso");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 160, 25);

        jLabel2.setText("Número de ciclos para satisfacer operaciones mediante interrupts:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(30, 300, 360, 14);

        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });
        jPanel2.add(textField1);
        textField1.setBounds(240, 120, 40, 20);

        jLabel3.setText("Nombre:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(30, 70, 50, 14);

        jLabel4.setText("Nombre:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(30, 70, 50, 14);

        textField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField2ActionPerformed(evt);
            }
        });
        jPanel2.add(textField2);
        textField2.setBounds(90, 70, 190, 20);

        jLabel5.setText("Longitud (Cantidad de Instrucciones):");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(30, 120, 210, 14);

        jButton1.setText("Dependiente de E/S");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(300, 180, 150, 30);

        jButton2.setText("Dependiente de CPU");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(120, 180, 150, 30);

        jLabel6.setText("Dependencia:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(30, 180, 210, 14);

        jLabel8.setText("Prioridad:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(30, 350, 60, 14);
        jPanel2.add(textField3);
        textField3.setBounds(270, 250, 50, 20);

        textField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField4ActionPerformed(evt);
            }
        });
        jPanel2.add(textField4);
        textField4.setBounds(390, 300, 60, 20);

        jLabel9.setText("Número de ciclos para generar solicitud/es:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(30, 250, 250, 14);
        jPanel2.add(textField5);
        textField5.setBounds(90, 350, 50, 20);

        button1.setLabel("Crear");
        jPanel2.add(button1);
        button1.setBounds(520, 390, 100, 40);

        jTabbedPane2.addTab("Crear", jPanel2);

        jPanel3.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Modificar parametros iniciales:");
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 14));
        jPanel3.add(jLabel7);
        jLabel7.setBounds(29, 9, 251, 20);

        txtCiclosCompletarExcepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiclosCompletarExcepcionActionPerformed(evt);
            }
        });
        jPanel3.add(txtCiclosCompletarExcepcion);
        txtCiclosCompletarExcepcion.setBounds(29, 127, 60, 20);

        jLabel21.setText("Ciclos para completar excepción");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(98, 130, 300, 14);

        btnConfigurar1.setText("Actualizar");
        btnConfigurar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurar1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnConfigurar1);
        btnConfigurar1.setBounds(450, 150, 120, 30);

        txtDuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuracionActionPerformed(evt);
            }
        });
        jPanel3.add(txtDuracion);
        txtDuracion.setBounds(29, 207, 60, 20);

        jLabel22.setText("Duracion del ciclo (ms)");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(98, 210, 300, 14);

        txtProcesadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProcesadoresActionPerformed(evt);
            }
        });
        jPanel3.add(txtProcesadores);
        txtProcesadores.setBounds(29, 167, 60, 20);

        jLabel23.setText("Numero de procesadores");
        jPanel3.add(jLabel23);
        jLabel23.setBounds(98, 170, 240, 14);

        txtCantInstrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantInstruccionesActionPerformed(evt);
            }
        });
        jPanel3.add(txtCantInstrucciones);
        txtCantInstrucciones.setBounds(29, 47, 60, 20);

        jLabel24.setText("Cantidad Instrucciones");
        jPanel3.add(jLabel24);
        jLabel24.setBounds(100, 50, 160, 14);

        txtTipoProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoProcesoActionPerformed(evt);
            }
        });
        jPanel3.add(txtTipoProceso);
        txtTipoProceso.setBounds(29, 87, 60, 20);

        jLabel25.setText("Tipo de proceso (1=CPU Bound, 0=I/O Bound)");
        jPanel3.add(jLabel25);
        jLabel25.setBounds(99, 90, 320, 14);

        txtCiclosExcepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiclosExcepcionActionPerformed(evt);
            }
        });
        jPanel3.add(txtCiclosExcepcion);
        txtCiclosExcepcion.setBounds(29, 247, 60, 20);

        jLabel26.setText("Ciclos para excepción");
        jPanel3.add(jLabel26);
        jLabel26.setBounds(98, 250, 280, 14);

        jTabbedPane2.addTab("Configuración", jPanel3);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCiclosCompletarExcepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiclosCompletarExcepcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiclosCompletarExcepcionActionPerformed

    private void btnConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfigurarActionPerformed

    private void btnConfigurar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurar1ActionPerformed
        // TODO add your handling code here:
        configurar();
    }//GEN-LAST:event_btnConfigurar1ActionPerformed

    private void txtDuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuracionActionPerformed

    private void txtProcesadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProcesadoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProcesadoresActionPerformed

    private void txtCantInstruccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantInstruccionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantInstruccionesActionPerformed

    private void txtTipoProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoProcesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoProcesoActionPerformed

    private void txtCiclosExcepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiclosExcepcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiclosExcepcionActionPerformed

    private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField1ActionPerformed

    private void textField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    private void SeleccionPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionPActionPerformed

    }//GEN-LAST:event_SeleccionPActionPerformed

    private void botonCambiarPoliticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCambiarPoliticaActionPerformed
             configurarComponentes();
    }//GEN-LAST:event_botonCambiarPoliticaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> {
        try {
            Configuracion configuracion = new Configuracion("parametros.txt");
            int[] parametros = configuracion.leerParametros();

            int duracionCiclo = parametros[0];
            int numProcesadores = parametros[1];
            int numInstrucciones = parametros[2];
            int tipoProceso = parametros[3];
            int ciclosExcepcion = parametros[4];
            int ciclosCompletarExcepcion = parametros[5];

            Cola<Proceso> procesosListos = new Cola<>();
            Cola<Proceso> procesosBloqueados = new Cola<>();
            Cola<Proceso> procesosTerminados = new Cola<>();
            Cola<Proceso> procesosEjecucion = new Cola<>();

            Scheduler schedulerInicial = new PoliticaFCFSS(procesosListos, procesosBloqueados, procesosTerminados);
            SistemaOperativo sistemaOperativo = new SistemaOperativo(schedulerInicial, procesosListos, procesosBloqueados, procesosTerminados, procesosEjecucion, numProcesadores);
            sistemaOperativo.setDuraciónCiclo(duracionCiclo);

            for (int i = 1; i <= 15; i++) {
                procesosListos.enqueue(new Proceso(100 + i, "Proceso" + i, numInstrucciones, tipoProceso == 1, ciclosExcepcion, ciclosCompletarExcepcion, 1000, 5, sistemaOperativo));
            }

            for (int i = 16; i <= 30; i++) {
                int randomInstrucciones = (int) (Math.random() * 50) + 1;
                int randomTipoProceso = (int) (Math.random() * 2);
                int randomCiclosExcepcion = (int) (Math.random() * 10) + 1;
                int randomCiclosCompletarExcepcion = (int) (Math.random() * 10) + 1;
                procesosListos.enqueue(new Proceso(100 + i, "Proceso" + i, randomInstrucciones, randomTipoProceso == 1, randomCiclosExcepcion, randomCiclosCompletarExcepcion, 1000, 5, sistemaOperativo));
            }

            sistemaOperativo.iniciar();
            new Menu(sistemaOperativo, schedulerInicial).setVisible(true);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SeleccionP;
    private javax.swing.JButton botonCambiarPolitica;
    private javax.swing.JButton btnConfigurar;
    private javax.swing.JButton btnConfigurar1;
    private java.awt.Button button1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblCicloReloj;
    private javax.swing.JSpinner spinnerDuracionCiclo;
    private javax.swing.JSpinner spinnerNumProcesadores;
    private javax.swing.JTable tablaBloqueados;
    private javax.swing.JTable tablaEjecucion;
    private javax.swing.JTable tablaListos;
    private javax.swing.JTable tablaTerminados;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    private java.awt.TextField textField3;
    private java.awt.TextField textField4;
    private java.awt.TextField textField5;
    private javax.swing.JTextField txtCantInstrucciones;
    private javax.swing.JTextField txtCiclosCompletarExcepcion;
    private javax.swing.JTextField txtCiclosExcepcion;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtProcesadores;
    private javax.swing.JTextField txtTipoProceso;
    // End of variables declaration//GEN-END:variables
}
