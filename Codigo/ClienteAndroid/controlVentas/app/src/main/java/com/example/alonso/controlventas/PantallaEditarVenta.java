package com.example.alonso.controlventas;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alonso.controlventas.adapters.CrearVentaAdapter;
import com.example.alonso.controlventas.modelo.ArticulosVenta;
import com.example.alonso.controlventas.modelo.Venta;
import com.example.alonso.controlventas.tasks.ArticulosVentaTask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class PantallaEditarVenta extends AppCompatActivity {
    private ListView listaVenta;
    private CrearVentaAdapter ventaAdapter;
    private List<ArticulosVenta> articulosVentas;
    private Venta venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        venta = PantallaVentas.venta;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_venta);
        TextView nombreCliente = (TextView)  findViewById(R.id.lblClienteEditar);
        nombreCliente.setText(venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos());
        TextView montoTotal = (TextView)  findViewById(R.id.lblmonto);
        montoTotal.setText(String.valueOf(venta.getMonto()));
        listaVenta = (ListView) findViewById(R.id.listaEditarVenta);
        cargarVentas();
        ventaAdapter = new CrearVentaAdapter(PantallaEditarVenta.this,
                R.layout.crear_venta_layout,articulosVentas);
        listaVenta.setAdapter(ventaAdapter);
    }

    public void cargarVentas(){
        articulosVentas = new ArrayList<>();
        new ArticulosVentaTask(articulosVentas,listaVenta,this,venta.getIdVenta()).execute();
    }
}
