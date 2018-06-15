package com.example.alonso.controlventas;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.alonso.controlventas.adapters.ArticuloAdapter;
import com.example.alonso.controlventas.adapters.CrearVentaAdapter;
import com.example.alonso.controlventas.adapters.SpinAdapter;
import com.example.alonso.controlventas.modelo.Articulo;
import com.example.alonso.controlventas.modelo.ArticulosVenta;
import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.tasks.ArticuloTask;
import com.example.alonso.controlventas.tasks.ClientesEmpleadoTask;
import com.example.alonso.controlventas.tasks.VentaGuardarTask;

import java.util.ArrayList;
import java.util.List;

public class PantallaRegistrarVenta extends AppCompatActivity {
    private List<Cliente> clientes;
    private List<Articulo> listaArtuculos;
    private List<ArticulosVenta> articulosVentas;
    private Spinner cmbClientes;
    private Cliente cliente;
    private ListView listaVentas;
    private CrearVentaAdapter ventaAdapter;
    private double ventaTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar_venta);
        articulosVentas = new ArrayList<>();
        listaVentas = (ListView) findViewById(R.id.listaRegistrarVenta);
        ventaAdapter = new CrearVentaAdapter(PantallaRegistrarVenta.this,
                R.layout.crear_venta_layout,articulosVentas);
        listaVentas.setAdapter(ventaAdapter);
        Button btnAgregarProducto = (Button) findViewById(R.id.btnAgregarProducto);
        final TextView total = (TextView)  findViewById(R.id.lblventatotal);
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogo = new AlertDialog.Builder(PantallaRegistrarVenta.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_registrar_articulo, null);
                final Spinner cmbArticulo = (Spinner) view.findViewById(R.id.cmbArticulo);
                final ArticuloAdapter adapter = new ArticuloAdapter(PantallaRegistrarVenta.this,
                        android.R.layout.simple_spinner_item,
                        listaArtuculos);
                final Spinner cmbStockArticulos = (Spinner) view.findViewById(R.id.cmbStockArticulo);
                Button btnAceptararticulo = (Button) view.findViewById(R.id.btnAceptarArticulo);
                cmbArticulo.setAdapter(adapter);
                dialogo.setView(view);
                final AlertDialog alertDialog = dialogo.create();
                cmbArticulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        Articulo articulo = adapter.getItem(position);
                        ArrayList<Integer> items = new ArrayList<>();
                        for (int i = 1; i <= articulo.getCantidad(); i++) {
                            items.add(i);
                        }
                        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(PantallaRegistrarVenta.this,
                                android.R.layout.simple_spinner_item, items);
                        cmbStockArticulos.setAdapter(adapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {
                    }
                });

                btnAceptararticulo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        ArticulosVenta pedido = new ArticulosVenta();
                        double precioTotal = Integer.parseInt(cmbStockArticulos.getSelectedItem().toString()) *
                                ((Articulo)cmbArticulo.getSelectedItem()).getPrecioUnitario();
                        ventaTotal += precioTotal;
                        total.setText(String.valueOf(ventaTotal));
                        pedido.setPrecioTotal(precioTotal);
                        Articulo articulo = (Articulo)cmbArticulo.getSelectedItem();
                        pedido.setArticulo(articulo);
                        int cantidadArticulos = Integer.parseInt(cmbStockArticulos.getSelectedItem().toString());
                        pedido.setCantidadArticulos(cantidadArticulos);
                        int indice  = listaArtuculos.lastIndexOf(articulo);
                        listaArtuculos.get(indice).setCantidad(listaArtuculos.get(indice).getCantidad() - cantidadArticulos);
                        if(listaArtuculos.get(indice).getCantidad() == 0){
                            listaArtuculos.remove(indice);
                        }
                        articulosVentas.add(pedido);
                        ventaAdapter.refreshEvents();
                        alertDialog.dismiss();

                    }
                });



                alertDialog.show();
            }
        });


        cmbClientes = (Spinner) findViewById(R.id.cmbClientes);
        cargarClientes();
        cargarListaArticulos();
        final SpinAdapter adapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,
                clientes);
        cmbClientes.setAdapter(adapter);
    }

    public void cargarClientes() {
        clientes = new ArrayList<>();
        new ClientesEmpleadoTask(clientes, this, cmbClientes).execute();
    }

    public void cargarListaArticulos() {
        listaArtuculos = new ArrayList<>();
        new ArticuloTask(listaArtuculos).execute();
    }

    public List<ArticulosVenta> getArticulosVentas() {
        return articulosVentas;
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
                    new VentaGuardarTask(articulosVentas,(Cliente)cmbClientes.getSelectedItem(),ventaTotal,this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
