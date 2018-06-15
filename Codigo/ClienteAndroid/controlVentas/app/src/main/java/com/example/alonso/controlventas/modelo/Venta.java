package com.example.alonso.controlventas.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private int idEmpleado;
    private double monto;
    private String estado;
    private String fechaVenta;

    public Venta(JSONObject jsonObject){
        try {
            this.idVenta = jsonObject.getInt("idVenta");
            JSONObject cliente =  jsonObject.getJSONObject("clienteidCliente");
            this.cliente = new Cliente(cliente);
            this.monto = jsonObject.getDouble("montoVenta");
            this.estado = jsonObject.getString("estado");
            this.fechaVenta = jsonObject.getString("fechaVenta");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

}
