package com.example.alonso.controlventas.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Empleado {
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private String idEmpleado;

    public Empleado(JSONObject jsonObject){
        try {
            this.nombres = jsonObject.getString("nombres");
            this.apellidos = jsonObject.getString("apellidos");
            this.correoElectronico = jsonObject.getString("correoElectronico");
            this.idEmpleado = jsonObject.getString("idEmpleado");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Empleado(){

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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public JSONObject obtenerJSON(JSONObject jsonObject){
        JSONObject empleado = new JSONObject();
        try {
            empleado.put("nombres",jsonObject.getString("nombres"));
            empleado.put("apellidos",jsonObject.getString("apellidos"));
            empleado.put("correoElectronico",jsonObject.getString("correoelectronico"));
            empleado.put("idEmpleado", jsonObject.getString("idempleado"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return empleado;
    }
}
