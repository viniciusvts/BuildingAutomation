package br.com.vtsantana.buildingautomation.control;

import gnu.io.SerialPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JOptionPane;
/**
 *
 * @author vinicius
 */
public class ControlArduino {
    private String PORTA = "/dev/ttyACM0"; //porta onde o arduino está
    private int TAXA = 9600; //taxa de comunicação do arduino. padrao:9600
    private InputStream serialIn;
    private OutputStream serialOut;
    
    public ControlArduino(){
        this.initialize();
    }
    
    /**
     * Método que inicializa a comunicação serial com o arduino
     */
    public void initialize(){
        try {
        //Define uma variável portId para realizar a comunicação serial
        CommPortIdentifier portId = null;
        
        try {
            //verifica se a PORTA existe
            portId = CommPortIdentifier.getPortIdentifier(this.PORTA);
        }catch (NoSuchPortException e) {
            //se nao existe, exibe erro
            JOptionPane.showMessageDialog(null, "Porta não existe.",
                    "Porta COM", JOptionPane.PLAIN_MESSAGE);
        }
        //Abre a porta COM 
        SerialPort port = (SerialPort) portId.open("Comunicação serial", this.TAXA);
        serialIn = port.getInputStream();
        serialOut = port.getOutputStream();
        port.setSerialPortParams(this.TAXA, //taxa de transferência da porta serial 
                SerialPort.DATABITS_8, //taxa de 10 bits 8 (envio)
                SerialPort.STOPBITS_1, //taxa de 10 bits 1 (recebimento)
                SerialPort.PARITY_NONE); //receber e enviar dados
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * método que irá fechar a conexão
     */
    public void close(){
        try {
            serialOut.close();
            serialIn.close();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    /**
     * método que irá mandar dados na porta serial
     * @param dado - String que será enviada
     */
    public void enviaDados(String dado){
        byte[] dados = dado.getBytes();
        try {
            serialOut.write(dados);//escreve o valor na porta serial para ser enviado
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o dado. ",
                    "Enviar dados", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    /**
     * método que irá pegar dados na porta serial
     * @return - a String com a informação resultante
     */
    /*
    public String recebeDados(){
        byte[] dado;
        try {
            serialIn.read();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o dado. ",
                    "Enviar dados", JOptionPane.PLAIN_MESSAGE);
        }
        return 
    }
    */
    
}
