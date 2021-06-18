/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lathy
 */
public class Coding {
    private int id;
    private int id_subtitle;
    private String nombre;
    private String color;
    private String pathDocumento;
    private String palabra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getId_subtitle() {
        return id_subtitle;
    }

    public void setId_subtitle(int id_subtitle) {
        this.id_subtitle = id_subtitle;
    }

    public Coding(int id, int id_subtitle, String nombre, String color, String pathDocumento, String palabra) {
        this.id = id;
        this.id_subtitle = id_subtitle;
        this.nombre = nombre;
        this.color = color;
        this.pathDocumento = pathDocumento;
        this.palabra = palabra;
    }

    public Coding() {
    }
    
    
    
}
