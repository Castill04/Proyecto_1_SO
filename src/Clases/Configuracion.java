/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.*;

/**
 *
 * @author casti
 */

public class Configuracion {
    private String rutaArchivo;

    public Configuracion(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        verificarYCrearArchivo();
    }

    public void verificarYCrearArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarParametros(1000, 2, 10, 1, 5, 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] leerParametros() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
        int[] parametros = new int[6]; 

        for (int i = 0; i < parametros.length; i++) {
            String linea = reader.readLine();
            if (linea != null) {
                parametros[i] = Integer.parseInt(reader.readLine());
            }
        }
        reader.close();
        return parametros;
    }

    public void guardarParametros(int duracionCiclo, int numProcesadores, int instrucciones, int tipoProceso, int ciclosExcepcion, int ciclosEspera) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(duracionCiclo + "\n");
            writer.write(numProcesadores + "\n");
            writer.write(instrucciones + "\n");
            writer.write(tipoProceso + "\n");
            writer.write(ciclosExcepcion + "\n");
            writer.write(ciclosEspera + "\n");
        }
    }
}

