package com.example.alonso.controlventas;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alonso.controlventas.adapters.VentaAdapter;
import com.example.alonso.controlventas.modelo.Venta;
import com.example.alonso.controlventas.tasks.VentaTask;

import java.util.ArrayList;
import java.util.List;

public class PantallaVentas extends AppCompatActivity  {
    private List<Venta> ventas;
    private ListView listaVenta;
    public static Venta venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ventas);
        listaVenta = (ListView) findViewById(R.id.listaVentas);
        cargarVentas();
        VentaAdapter adapter = new VentaAdapter(this, R.layout.venta_layout,ventas);
        listaVenta.setAdapter(adapter);
        listaVenta.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(PantallaVentas.this, PantallaEditarVenta.class);
                PantallaVentas.venta = ventas.get(position);
                PantallaVentas.this.startActivityForResult(intento, 100);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_ventas, menu);
        return true;
    }

    public void cargarVentas(){
        ventas = new ArrayList<>();
        new VentaTask(PantallaVentas.this,listaVenta,ventas).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btnAgregarVenta:
               venta = null;
                Intent intento = new Intent(this, PantallaRegistrarVenta.class);
                this.startActivityForResult(intento,100);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == 100){
            cargarVentas();
        }
    }


}
