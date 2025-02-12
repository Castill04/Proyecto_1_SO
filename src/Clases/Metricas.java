/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;

/**
 *
 * @author casti
 */
public class Metricas {
    
    private List<Double> rendimientoCPU;
    private List<Double> rendimientoSistema;

    public void registrarRendimientoCPU(double rendimiento) {
        rendimientoCPU.add(rendimiento);
    }

    public void registrarRendimientoSistema(double rendimiento) {
        rendimientoSistema.add(rendimiento);
    }
    
}
