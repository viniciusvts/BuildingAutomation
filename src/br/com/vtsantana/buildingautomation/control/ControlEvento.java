/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vtsantana.buildingautomation.control;

import java.util.Date;

/**
 *
 * @author vinicius
 */
public class ControlEvento {
    private int id;
    private String numeroSala;
    private Date inicio;
    private Date fim;

    //contrutores
    public ControlEvento(){}
    public ControlEvento(String numeroSala, Date inicio, Date fim) {
        this.numeroSala = numeroSala;
        this.inicio = inicio;
        this.fim = fim;
    }
    
    //gets e sets

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }
    
}
