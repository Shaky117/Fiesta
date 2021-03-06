package com.tullipan.fiesta;

public class Categorias {
    private int id;
    private String categoria;
    private String imagen;

    public Categorias(int id, String categoria, String imagen) {
        this.id = id;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}