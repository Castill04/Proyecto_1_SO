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
    }

    public void verificarYCrearArchivo() throws IOException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("1000\n2\n10\n1\n5\n3\n");
            }
        }
    }

    public int[] leerParametros() throws IOException {
        int[] parametros = new int[6];
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            for (int i = 0; i < 6; i++) {
                parametros[i] = Integer.parseInt(reader.readLine().trim());
            }
        }
        return parametros;
    }

    public void guardarParametros(int duracion, int procesadores, int instrucciones, int tipoProceso, int ciclosExcepcion, int ciclosCompletarExcepcion) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(duracion + "\n");
            writer.write(procesadores + "\n");
            writer.write(instrucciones + "\n");
            writer.write(tipoProceso + "\n");
            writer.write(ciclosExcepcion + "\n");
            writer.write(ciclosCompletarExcepcion + "\n");
        }
    }
}

