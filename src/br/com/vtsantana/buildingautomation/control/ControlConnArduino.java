package br.com.vtsantana.buildingautomation.control;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author vinicius
 */
public class ControlConnArduino {
    
    private SerialPort serialPort;
    private String port = "/dev/ttyACM0";
    
    public ControlConnArduino () throws SerialPortException{
                
        boolean success = false;
        serialPort = new SerialPort(port);
        serialPort.openPort();
        serialPort.setParams(
                SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

    }
    
    /**
     * Método manda dados pela porta serial
     * @param dados - com string a ser enviada
     * @throws SerialPortException 
     */
    public void digitalWrite( String dados ) throws SerialPortException{
        serialPort.writeString(dados);
    }
    
    public String digitalRead() throws SerialPortException{
        String string = serialPort.readString();
        return string;
    }
}
