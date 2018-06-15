package com.example.alonso.controlventas.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.alonso.controlventas.PantallaRegistrarVenta;
import com.example.alonso.controlventas.modelo.ArticulosVenta;
import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class VentaGuardarTask extends AsyncTask<Void, Void, Boolean> {
    private List<ArticulosVenta> articulosVentas;
    private Cliente cliente;
    private double montoTotal;
    private PantallaRegistrarVenta context;

    public VentaGuardarTask(List<ArticulosVenta> articulosVentas, Cliente cliente, double montoTotal,PantallaRegistrarVenta context) {
        this.articulosVentas = articulosVentas;
        this.cliente = cliente;
        this.montoTotal = montoTotal;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        SharedPreferences prefs = context.getSharedPreferences("empleado",
                MODE_PRIVATE);
        String string = prefs.getString("empleado",
                "default_value_here_if_string_is_missing");
        try {
            JSONObject empleado = new JSONObject(string);
            JSONObject venta = new JSONObject();
            venta.put("idEmpleado", empleado.getString("idempleado"));
            venta.put("idCliente",cliente.getIdCliente());
            venta.put("montoventa", montoTotal);
            venta.put("estado","En proceso");
            venta.put("fechaventa",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            JSONArray articulosVentaJson = new JSONArray();
            for(ArticulosVenta articulo: articulosVentas){
                JSONObject articuloJSON = new JSONObject();
                articuloJSON.put("cantidad",articulo.getCantidadArticulos());
                articuloJSON.put("subtotal",articulo.getPrecioTotal());
                articuloJSON.put("idArticulo",articulo.getArticulo().getIdArticulo());
                articulosVentaJson.put(articuloJSON);
            }
            venta.put("listaArticulos",articulosVentaJson);


                String ruta = "http://192.168.100.12:9000/crearVenta/";
                String metodoEnvio ="POST";
                URL url = new URL(ruta);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod(metodoEnvio);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();
                OutputStream output = conn.getOutputStream();
                BufferedWriter bufferW = new BufferedWriter(new OutputStreamWriter(output));
                InputStream input;
                bufferW.write(venta.toString());
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
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success){
        if(success){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Guardado exitoso");
            builder.setMessage("La venta ha sido guardada con exito").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.finish();
                }
            });
            builder.create().show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
