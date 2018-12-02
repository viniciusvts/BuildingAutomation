/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vtsantana.buildingautomation.model;

/**
 *
 * @author vinicius
 */
public class ModelDadosArduino {
    private int temperatura;
    private boolean presenca;
    
    public ModelDadosArduino(){}
    public ModelDadosArduino(int temperatura, boolean presenca) {
        this.temperatura = temperatura;
        this.presenca = presenca;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public boolean isPresenca() {
        return presenca;
    }

    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }
    
    
}
