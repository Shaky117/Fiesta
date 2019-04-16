package com.tullipan.fiesta;

public class ProveedoresItem {
    private int id;
    private String name;
    private String foto;

    public ProveedoresItem(int id, String name, String foto) {
        this.id = id;
        this.name = name;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
