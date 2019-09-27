package com.example.transporteudb_v04;

public class Unidad {
    private String dueno;
    private String id;
    private Double latitud;
    private Double longitud;
    private String motorista;
    private String zona;

    public Unidad() {
    }

    public Unidad(String dueno, String id, Double latitud, Double longitud, String motorista, String zona) {
        this.dueno = dueno;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.motorista = motorista;
        this.zona = zona;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
