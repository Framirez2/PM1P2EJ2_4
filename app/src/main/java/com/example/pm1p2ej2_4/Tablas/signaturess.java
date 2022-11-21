package com.example.pm1p2ej2_4.Tablas;

public class signaturess {
    public Integer id;
    public String descripcion;
    public String signatures;

    public signaturess(){
        //Todo
    }

    public signaturess(Integer id, String descripcion, String image) {
        this.id = id;
        this.descripcion = descripcion;
        this.signatures = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSignatures() {
        return signatures;
    }

    public void setSignatures(String image) {
        this.signatures = image;
    }
}
