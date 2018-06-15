package com.example.alonso.controlventas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.tasks.ClienteGuardarTask;

public class PantallaRegistrarCliente extends AppCompatActivity {
    private Cliente cliente;
    private EditText nombre;
    private EditText apellidos;
    private EditText rfc;
    private EditText correo;
    private EditText direccion;
    private EditText codigoPostal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        cliente = PantallaPrincipal.cliente;
        nombre = (EditText) findViewById(R.id.txtNombre);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        rfc = (EditText) findViewById(R.id.txtRFC);
        correo = (EditText) findViewById(R.id.txtCorreo);
        direccion = (EditText) findViewById(R.id.txtDireccion);
        codigoPostal = (EditText) findViewById(R.id.txtCodigoPostal);

        if(cliente != null){
            nombre.setText(cliente.getNombres());
            apellidos .setText(cliente.getApellidos());
            rfc.setText(cliente.getRFC());
            correo.setText(cliente.getCorreoElectronico());
            direccion.setText(cliente.getDireccion());
            codigoPostal.setText(cliente.getCodigoPostal());
        }else{
            cliente = new Cliente();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registrarcliente_menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btnGuardarCliente:
                cliente.setNombres(nombre.getText().toString());
                cliente.setApellidos(apellidos.getText().toString());
                cliente.setRFC(rfc.getText().toString());
                cliente.setCorreoElectronico(correo.getText().toString());
                cliente.setDireccion(direccion.getText().toString());
                cliente.setCodigoPostal(codigoPostal.getText().toString());
                new ClienteGuardarTask(cliente,this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
