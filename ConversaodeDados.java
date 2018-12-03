package br.com.vtsantana.buildingautomation.control;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author viniciusbarbosa
 */
public class ConversaodeDados {
      ControlConnArduino controle ;
      ControlDadosArduino dados = new ControlDadosArduino() ;

    public ConversaodeDados() throws SerialPortException {
        this.controle = new ControlConnArduino();
        controle.digitalRead(dados.setPresenca(true)| dados.setTemperatura(0));
        
        
    }
     
     
      
    
}    
