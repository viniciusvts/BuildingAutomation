/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vtsantana.buildingautomation.testes;

import br.com.vtsantana.buildingautomation.control.ControlConnArduino;
import jssc.SerialPortException;

/**
 *
 * @author vinicius
 */
public class Main {
    public static void main(String[] args) throws SerialPortException {
        testaConexao();
    }
    
    public static void testaConexao() throws SerialPortException{
        System.out.println("iniciando");
        ControlConnArduino con = new ControlConnArduino();
        int count = 0;
        String retornoSerial;
        retornoSerial = "nenhum retorno "+ count;
        System.out.println("iniciado");
        
        while(true){
            con.digitalWrite("dadosenviados = " + count);
            
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            
            retornoSerial = con.digitalRead();
            System.out.println(retornoSerial);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            count++;
        }
     }
    
}
