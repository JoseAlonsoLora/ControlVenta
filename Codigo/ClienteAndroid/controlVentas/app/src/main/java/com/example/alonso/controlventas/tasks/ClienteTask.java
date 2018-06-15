package com.example.alonso.controlventas.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.alonso.controlventas.PantallaPrincipal;
import com.example.alonso.controlventas.R;
import com.example.alonso.controlventas.adapters.ClienteAdapter;
import com.example.alonso.controlventas.modelo.Cliente;

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

import static android.content.Context.MODE_PRIVATE;

public class ClienteTask extends AsyncTask<Void,Void,Boolean>{
    private List<Cliente> clientes;
    private ListView listaClientes;
    private Context context;

    public ClienteTask(List<Cliente> clientes, ListView listaClientes, Context context){
        this.listaClientes = listaClientes;
        this.clientes = clientes;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String cad = "";
        try {
            String ruta = "http://192.168.100.12:8080/servicios/webresources/modelo.cliente";
            SharedPreferences prefs = context.getSharedPreferences("empleado",
                    MODE_PRIVATE);
            String string = prefs.getString("empleado",
                    "default_value_here_if_string_is_missing");

            try {
                JSONObject empleado = new JSONObject(string);
                ruta += "/" + empleado.getString("idempleado");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            URL url = new URL(ruta);

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
            cad = bufferedReader.readLine();
            try{
                JSONArray jsonRespuesta = new JSONArray(cad);
                for (int i = 0; i < jsonRespuesta.length();i++){
                    try{
                        JSONObject jsonObject = jsonRespuesta.getJSONObject(i);
                        clientes.add(new Cliente(jsonObject));
                    }catch (Exception e){

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
            ClienteAdapter adaptador = new ClienteAdapter(context, R.layout.cliente_layout, clientes);
            listaClientes.setAdapter(adaptador);
            listaClientes.invalidateViews();
        }else{

        }
    }
}
