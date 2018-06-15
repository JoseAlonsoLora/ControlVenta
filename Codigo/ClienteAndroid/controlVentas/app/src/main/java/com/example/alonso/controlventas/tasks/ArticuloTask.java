package com.example.alonso.controlventas.tasks;

import android.os.AsyncTask;

import com.example.alonso.controlventas.modelo.Articulo;
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

public class ArticuloTask extends AsyncTask<Void,Void,Boolean> {
    private List<Articulo> articulos;

    public ArticuloTask(List<Articulo> articulos){
        this.articulos = articulos;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.100.12:8080/servicios/webresources/modelo.articulo");
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
                        articulos.add(new Articulo(jsonObject));
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
}
