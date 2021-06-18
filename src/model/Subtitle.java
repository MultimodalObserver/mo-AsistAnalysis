/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

/**
 *
 * @author Lathy
 */
public class Subtitle {
    private int id;
    private String inicio;
    private String termino;
    private String texto;
    private WebView textfield;

    public Subtitle() {
    
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Subtitle(int id, String inicio, String termino, String texto) {//For transcription
        this.id = id;
        this.inicio = inicio;
        this.termino = termino;
        this.texto = texto;
    }

    public WebView getTextfield() {
        return textfield;
    }

    public void setTextfield(WebView textfield) {
        this.textfield = textfield;
        this.textfield.getEngine().loadContent(this.texto);
    }
    
    public Subtitle(String texto, String inicio, String termino, int id) {//For label
        this.id = id;
        this.texto = texto;
        this.textfield = new WebView();
        this.textfield.getEngine().loadContent(texto);
        this.inicio = inicio;
        this.termino = termino;
    }
    
    
    
}
