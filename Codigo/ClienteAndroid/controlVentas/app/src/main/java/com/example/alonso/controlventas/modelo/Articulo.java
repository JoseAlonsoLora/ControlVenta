package com.example.alonso.controlventas.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Articulo {
    private String idArticulo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private int cantidad;

    public Articulo(JSONObject jsonObject){
        try {
            this.idArticulo = jsonObject.getString("idArticulo");
            this.nombre = jsonObject.getString("nombre");
            this.cantidad = jsonObject.getInt("cantidad");
            this.descripcion = jsonObject.getString("descripcion");
            this.precioUnitario = jsonObject.getDouble("precioUnitario");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
