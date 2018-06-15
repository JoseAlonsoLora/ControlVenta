package com.example.alonso.controlventas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alonso.controlventas.adapters.ClienteAdapter;
import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.tasks.ClienteGuardarTask;
import com.example.alonso.controlventas.tasks.ClienteTask;

import java.util.ArrayList;
import java.util.List;

public class PantallaPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EMPLEADO = "empleado";
    private ListView listaClientes;
    private List<Cliente> clientes;
    public static int CODIGO = 100;
    public static Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        listaClientes = (ListView) findViewById(R.id.tablaClientes);
        cargarClientes();
        ClienteAdapter adapter = new ClienteAdapter(this, R.layout.cliente_layout,clientes);
        listaClientes.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(PantallaPrincipal.this, PantallaRegistrarCliente.class);
                PantallaPrincipal.cliente = clientes.get(position);
                PantallaPrincipal.this.startActivityForResult(intento, CODIGO);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void cargarClientes(){
        clientes = new ArrayList();
        new ClienteTask(clientes,listaClientes,PantallaPrincipal.this).execute();
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == CODIGO){
            this.cliente = null;
            cargarClientes();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btnAgregarCliente:
                cliente = null;
                Intent intento = new Intent(PantallaPrincipal.this, PantallaRegistrarCliente.class);
                PantallaPrincipal.this.startActivityForResult(intento, CODIGO);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menucliente) {
            Intent intento = new Intent(this, PantallaPrincipal.class);
            this.startActivity(intento);
            this.finish();
        } else if (id == R.id.menuVentas) {
            Intent intento = new Intent(this, PantallaVentas.class);
            this.startActivity(intento);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
