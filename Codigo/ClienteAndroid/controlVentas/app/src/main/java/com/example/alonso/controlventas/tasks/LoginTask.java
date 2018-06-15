package com.example.alonso.controlventas.tasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.alonso.controlventas.PantallaLogin;
import com.example.alonso.controlventas.PantallaPrincipal;
import com.example.alonso.controlventas.modelo.Usuario;

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

public class LoginTask extends AsyncTask<Void,Void,Boolean> {

    private Usuario usuario;
    private PantallaLogin pantallaLogin;


    public LoginTask(Usuario usuario,PantallaLogin pantallaLogin){
        this.usuario = usuario;
        this.pantallaLogin = pantallaLogin;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.100.12:9000/loginMovil/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nombreusuario",usuario.getNombreUsuario());
                jsonObject.put("contraseña",usuario.getContraseña());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream output = conn.getOutputStream();
            BufferedWriter bufferW = new BufferedWriter(new OutputStreamWriter(output));
            InputStream input;
            bufferW.write(jsonObject.toString());
            bufferW.flush();

            if(conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
                return false;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cad = bufferedReader.readLine();
            JSONObject empleado = new JSONObject(cad);
            SharedPreferences prefs = pantallaLogin.getSharedPreferences("empleado", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("empleado", String.valueOf(empleado));
            editor.commit();
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
            Intent intento = new Intent(pantallaLogin, PantallaPrincipal.class);
            pantallaLogin.startActivity(intento);
            pantallaLogin.finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(pantallaLogin);
            builder.setMessage("Usuario inválido").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
    }

}
