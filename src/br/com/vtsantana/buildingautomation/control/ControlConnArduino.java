package br.com.vtsantana.buildingautomation.control;

import br.com.vtsantana.buildingautomation.model.ModelDadosArduino;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author vinicius
 */
public class ControlConnArduino {
    
    private final SerialPort serialPort;
    private final String port = "/dev/ttyACM0";
    
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
    
    /**
     * Método recebe dados pela porta serial
     * @return String
     * @throws SerialPortException 
     */
    public String digitalRead() throws SerialPortException{
        String string = serialPort.readString();
        return string;
    }
    
    /**
     * Método recebe dados pela porta serial e retorna o ModelDadosArduino
     * @return ModelDadosArduino
     * @throws SerialPortException 
     */
    public ModelDadosArduino getDadosArduino() throws SerialPortException{
        String str; //onde colocarei o dados vindo do s
        String strPresenca; // onde colocarei a informação 1
        String strTemp; // onde colocarei a informação 2
        
        // as informações estão em String, transformarei em respectivos bool e inteiro
        boolean boolPresenca;
        int intTemp;
        
        str = digitalRead(); //leio a informação
        /* A informação vem da seguinte forma:
        1|30
        onde 1 ou 0 é presença e 30 é a temperatura que virá*/
        String[] dados = str.split("|"); // quebro a string que virá
        if (dados.length == 2){ // se qubrou em duas está correto, se não retorna null
            strPresenca = dados[0];
            strTemp = dados[1];
        } else {
            return null;
        }
        
        //verifica a presença
        switch (strPresenca) {
            case "1":
                boolPresenca = true;
                break;
            case "0":
                boolPresenca = false;
                break;
            default:
                return null; // se nao vier nem 0 ou 1 esntao algo errado aconteceu
            }
            
            // pega a temeratura e tranforma em inteiro
            intTemp = Integer.parseInt(strTemp);
            
            //instancia a classe e retorna
            ModelDadosArduino model = new ModelDadosArduino(intTemp, boolPresenca);
            return model;
    }
    
    public void enviarLigarParaArduino() throws SerialPortException{
        this.digitalWrite("l");
    }
    
    public void enviarDesligarParaArduino() throws SerialPortException{
        this.digitalWrite("d");
    }
}
