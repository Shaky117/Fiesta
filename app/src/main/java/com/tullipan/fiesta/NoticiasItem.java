package com.tullipan.fiesta;

public class NoticiasItem {

    private int id;
    private int idProveedor;
    private String imagen;
    private String noticia;
    private String descripcion;

    public NoticiasItem(int id, String imagen, String noticia, String descripcion, int proveedor){
        this.id = id;
        this.imagen = imagen;
        this.noticia = noticia;
        this.idProveedor = proveedor;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNoticia() {
        return noticia;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
