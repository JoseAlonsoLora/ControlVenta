package com.example.alonso.controlventas.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Cliente implements Serializable{
    private String idCliente;
    private String nombres;
    private String apellidos;
    private String RFC;
    private String correoElectronico;
    private String direccion;
    private String codigoPostal;
    private String empleado;

    public Cliente(){

    }

    public Cliente(JSONObject jsonObject) throws JSONException {
        idCliente = jsonObject.getString("idCliente");
        nombres = jsonObject.getString("nombres");
        apellidos = jsonObject.getString("apellidos");
        correoElectronico = jsonObject.getString("correoElectronico");
        direccion = jsonObject.getString("direccion");
        codigoPostal = jsonObject.getString("codigoPostal");
        RFC = jsonObject.getString("rfc");
       // empleado = jsonObject.getString("empleadoidEmpleado");
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRFC() {
        return RFC;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


}
