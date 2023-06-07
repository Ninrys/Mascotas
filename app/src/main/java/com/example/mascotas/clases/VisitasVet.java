package com.example.mascotas.clases;

public class VisitasVet {

    private String fecha, descripcion, nombreVet, tipoAtencion;
    public VisitasVet(String fecha, String descripcion, String nombreVet, String tipoAtencion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombreVet = nombreVet;

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

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }
}
