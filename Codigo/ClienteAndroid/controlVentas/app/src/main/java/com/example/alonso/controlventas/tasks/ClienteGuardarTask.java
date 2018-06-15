package com.example.alonso.controlventas.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.alonso.controlventas.PantallaRegistrarCliente;
import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.modelo.Empleado;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class ClienteGuardarTask extends AsyncTask<Void,Void,Boolean>{
    private Cliente cliente;
    private PantallaRegistrarCliente pantalla;

    public ClienteGuardarTask(Cliente cliente, PantallaRegistrarCliente pantalla){
        this.cliente = cliente;
        this.pantalla = pantalla;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            String ruta = "http://192.168.100.12:8080/servicios/webresources/modelo.cliente";
            String metodoEnvio ="POST";
            if(cliente.getIdCliente() != null) {
                ruta += "/"+cliente.getIdCliente();
                metodoEnvio = "PUT";
            }
            URL url = new URL(ruta);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(metodoEnvio);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            JSONObject jsonObject = new JSONObject();
            SharedPreferences prefs = pantalla.getSharedPreferences("empleado",
                    MODE_PRIVATE);
            String string = prefs.getString("empleado",
                    "default_value_here_if_string_is_missing");
            try {
                jsonObject.put("idCliente",cliente.getIdCliente());
                jsonObject.put("nombres",cliente.getNombres());
                jsonObject.put("apellidos",cliente.getApellidos());
                jsonObject.put("correoElectronico",cliente.getCorreoElectronico());
                jsonObject.put("direccion",cliente.getDireccion());
                jsonObject.put("codigoPostal",cliente.getCodigoPostal());
                jsonObject.put("rfc",cliente.getRFC());
                Empleado empleado = new Empleado();
                jsonObject.put("empleadoidEmpleado",empleado.obtenerJSON(new JSONObject(string)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream output = conn.getOutputStream();
            BufferedWriter bufferW = new BufferedWriter(new OutputStreamWriter(output));
            InputStream input;
            bufferW.write(jsonObject.toString());

            System.out.println(jsonObject.toString());
            bufferW.flush();
            if(conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
                return false;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    protected void onPostExecute(final Boolean success){
        if(success){
            AlertDialog.Builder builder = new AlertDialog.Builder(pantalla);
            builder.setTitle("Guardado exitoso");
            builder.setMessage("El cliente ha sido guardado con exito").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pantalla.finish();
                }
            });
            builder.create().show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(pantalla);
            builder.setTitle("Error");
            builder.setMessage("Error fatal").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }
}
