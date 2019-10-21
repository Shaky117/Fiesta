package com.tullipan.fiesta;

public class Eventos {

    private int id;
    private String nombre;
    private String fecha;
    private int tipoEvento;


    public Eventos(int id, String nombre, String fecha){
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Eventos(int id, String nombre, String fecha, int tipoEvento){
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoEvento = tipoEvento;
    }

    public int getTipoEvento() {
        return tipoEvento;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }
}
