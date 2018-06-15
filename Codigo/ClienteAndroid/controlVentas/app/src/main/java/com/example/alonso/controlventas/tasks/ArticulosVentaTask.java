package com.example.alonso.controlventas.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import com.example.alonso.controlventas.R;
import com.example.alonso.controlventas.adapters.CrearVentaAdapter;
import com.example.alonso.controlventas.modelo.Articulo;
import com.example.alonso.controlventas.modelo.ArticulosVenta;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ArticulosVentaTask extends AsyncTask<Void,Void,Boolean> {
    private List<ArticulosVenta> articulosVentas;
    private ListView listaVentas;
    private Context context;
    private int idVenta;

    public ArticulosVentaTask(List<ArticulosVenta> articulosVentas,ListView listaVentas,Context context,int idVenta){
        this.articulosVentas = articulosVentas;
        this.listaVentas = listaVentas;
        this.context = context;
        this.idVenta = idVenta;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.100.12:8080/servicios/webresources/modelo.ventahasarticulo/"+idVenta);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            InputStream input;
            if(conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cad = bufferedReader.readLine();
            try{
                JSONArray jsonRespuesta = new JSONArray(cad);
                for (int i = 0; i < jsonRespuesta.length();i++){
                    try{
                        JSONObject jsonObject = jsonRespuesta.getJSONObject(i);
                        ArticulosVenta articulosVenta = new ArticulosVenta();
                        JSONObject articulojson = new JSONObject(jsonObject.getString("articuloidArticulo"));
                        Articulo articulo = new Articulo(articulojson);
                        articulosVenta.setArticulo(articulo);
                        articulosVenta.setCantidadArticulos(jsonObject.getInt("cantidad"));
                        articulosVenta.setPrecioTotal(Double.parseDouble(jsonObject.getString("subtotal")));
                        articulosVentas.add(articulosVenta);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
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
            CrearVentaAdapter ventaAdapter = new CrearVentaAdapter(context,
                    R.layout.crear_venta_layout,articulosVentas);;
            listaVentas.setAdapter(ventaAdapter);
            listaVentas.invalidateViews();
        }else {
        }

    }
}
