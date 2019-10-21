package com.tullipan.fiesta;

public class Imagenes {
    private int id;
    private String imagen;

    public Imagenes(int id, String imagen) {
        this.id = id;
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
}
