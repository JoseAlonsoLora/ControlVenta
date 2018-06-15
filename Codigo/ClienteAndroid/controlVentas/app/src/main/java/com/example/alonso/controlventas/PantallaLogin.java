package com.example.alonso.controlventas;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.modelo.Usuario;
import com.example.alonso.controlventas.tasks.LoginTask;
import com.example.alonso.controlventas.tasks.VentaTask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PantallaLogin extends AppCompatActivity {
    private Button btnInciarSesion;
    private EditText nombreUsuario;
    private EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);
        nombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        contraseña = (EditText) findViewById(R.id.txtContraseña);
        btnInciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
    }

    public void iniciarSesion(View view){
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario.getText().toString());
        usuario.setContraseña(makeHash(contraseña.getText().toString()));
        new LoginTask(usuario,this).execute();

    }

    public static String makeHash(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(string.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
