/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.IOException;
import Interfaz.Menu;
/**
 *
 * @author Ignacio
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
    }
}
