package com.example.mascotas.clases;

public class VisitasVet {

    private String fecha, descripcion, nombreVet, ubicacion, tipoAtencion;
    public VisitasVet(String fecha, String descripcion, String nombreVet, String ubicacion, String tipoAtencion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombreVet = nombreVet;
        this.ubicacion = ubicacion;
        this.tipoAtencion = tipoAtencion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreVet() {
        return nombreVet;
    }

    public void setNombreVet(String nombreVet) {
        this.nombreVet = nombreVet;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }
}
