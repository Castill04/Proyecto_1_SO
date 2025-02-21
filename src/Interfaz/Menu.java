package Interfaz;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Clases.Cola;
import Clases.Configuracion;
import Clases.Proceso;
import Clases.SistemaOperativo;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 *
 * @author Ignacio
 */
public class Menu extends javax.swing.JFrame {
    public Configuracion configuracion;
    /**
     * Creates new form Menu
     */
    public Menu() {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(configuracion);
        initComponents();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textField2 = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textField3 = new java.awt.TextField();
        textField4 = new java.awt.TextField();
        jLabel9 = new javax.swing.JLabel();
        textField5 = new java.awt.TextField();
        button1 = new java.awt.Button();
        jComboBox1 = new javax.swing.JComboBox<>();
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
        jLabel10.setText("Sistema Operativo");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(10, 10, 160, 30);

        jScrollPane1.setViewportView(jTextPane1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(450, 270, 220, 140);

        jScrollPane2.setViewportView(jTextPane2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(450, 60, 220, 140);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Cola de listos");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(510, 20, 110, 30);

        jScrollPane3.setViewportView(jTextPane3);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(10, 340, 390, 120);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Cola de bloqueados");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(480, 230, 160, 30);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Procesos activos");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 300, 160, 30);

        jTabbedPane2.addTab("Dashboard", jPanel1);

        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Crear Proceso");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 160, 25);

        jLabel2.setText("Número de ciclos para satisfacer operaciones mediante interrupts:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(30, 300, 360, 16);

        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });
        jPanel2.add(textField1);
        textField1.setBounds(240, 120, 40, 20);

        jLabel3.setText("Nombre:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(30, 70, 50, 16);

        jLabel4.setText("Nombre:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(30, 70, 50, 16);

        textField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField2ActionPerformed(evt);
            }
        });
        jPanel2.add(textField2);
        textField2.setBounds(90, 70, 190, 20);

        jLabel5.setText("Longitud (Cantidad de Instrucciones):");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(30, 120, 210, 16);

        jLabel6.setText("Dependencia:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(30, 180, 80, 16);

        jLabel8.setText("Prioridad:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(30, 350, 60, 16);
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
        jLabel9.setBounds(30, 250, 250, 16);
        jPanel2.add(textField5);
        textField5.setBounds(90, 350, 50, 20);

        button1.setLabel("Crear");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel2.add(button1);
        button1.setBounds(520, 390, 100, 40);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dependiente de CPU", "Dependiente de E/S" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(130, 180, 320, 22);

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
        txtCiclosCompletarExcepcion.setBounds(29, 127, 64, 22);

        jLabel21.setText("Ciclos para completar excepción");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(98, 130, 300, 16);

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
        txtDuracion.setBounds(29, 207, 64, 22);

        jLabel22.setText("Duracion del ciclo (ms)");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(98, 210, 300, 16);

        txtProcesadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProcesadoresActionPerformed(evt);
            }
        });
        jPanel3.add(txtProcesadores);
        txtProcesadores.setBounds(29, 167, 64, 22);

        jLabel23.setText("Numero de procesadores");
        jPanel3.add(jLabel23);
        jLabel23.setBounds(98, 170, 240, 16);

        txtCantInstrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantInstruccionesActionPerformed(evt);
            }
        });
        jPanel3.add(txtCantInstrucciones);
        txtCantInstrucciones.setBounds(29, 47, 64, 22);

        jLabel24.setText("Cantidad Instrucciones");
        jPanel3.add(jLabel24);
        jLabel24.setBounds(100, 50, 160, 16);

        txtTipoProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoProcesoActionPerformed(evt);
            }
        });
        jPanel3.add(txtTipoProceso);
        txtTipoProceso.setBounds(29, 87, 64, 22);

        jLabel25.setText("Tipo de proceso (1=CPU Bound, 0=I/O Bound)");
        jPanel3.add(jLabel25);
        jLabel25.setBounds(99, 90, 320, 16);

        txtCiclosExcepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiclosExcepcionActionPerformed(evt);
            }
        });
        jPanel3.add(txtCiclosExcepcion);
        txtCiclosExcepcion.setBounds(29, 247, 64, 22);

        jLabel26.setText("Ciclos para excepción");
        jPanel3.add(jLabel26);
        jLabel26.setBounds(98, 250, 280, 16);

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

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        /**ong id = 5;
        boolean dependencia;
        String interrupt = "";
        String solicitud = "";
        if (jComboBox1.getSelectedItem().toString() == "Dependiente de CPU"){
            dependencia = true;
        } else{
            dependencia = false;
        }
        
        procesosListos.enqueue(new Proceso(id, textField2.getText(), Integer.parseInt(textField1.getText()), dependencia, Integer.parseInt(interrupt), Integer.parseInt(solicitud), 3, 3));
        id++;**/
        
    }//GEN-LAST:event_button1ActionPerformed

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Cola<Proceso> procesosListos = new Cola<>();

                procesosListos.enqueue(new Proceso(1, "P1", 10, true, 0, 0, 3, 4));
                procesosListos.enqueue(new Proceso(2, "P2", 15, false, 3, 2, 6, 6));
                procesosListos.enqueue(new Proceso(3, "P3", 8, true, 0, 0, 5, 5));
                procesosListos.enqueue(new Proceso(4, "P4", 12, false, 4, 3, 2,7));
                
                

                SistemaOperativo so = new SistemaOperativo(2, procesosListos);
                so.iniciar();
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfigurar;
    private javax.swing.JButton btnConfigurar1;
    private java.awt.Button button1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
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
