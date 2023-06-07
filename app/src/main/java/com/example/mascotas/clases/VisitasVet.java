package com.example.mascotas.clases;

public class VisitasVet {

    private String fecha;
    private String descripcion;
    private String nombreVet;
    private String tipoAtencion;
    private String id;

    public String getNombreConsulta() {
        return nombreConsulta;
    }

    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
    }

    private String nombreConsulta;

    public VisitasVet(String fecha, String descripcion, String nombreVet, String tipoAtencion, String id, String nombreConsulta) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombreVet = nombreVet;
        this.tipoAtencion = tipoAtencion;
        this.id = id;
        this.nombreConsulta= nombreConsulta;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
