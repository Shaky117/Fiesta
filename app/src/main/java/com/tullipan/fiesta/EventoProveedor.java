package com.tullipan.fiesta;

public class EventoProveedor {

    private int id;
    private String nombre;

    public EventoProveedor(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}
