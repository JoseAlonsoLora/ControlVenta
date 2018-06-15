package com.example.alonso.controlventas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alonso.controlventas.R;
import com.example.alonso.controlventas.modelo.Articulo;
import com.example.alonso.controlventas.modelo.Cliente;
import com.example.alonso.controlventas.modelo.Venta;

import java.util.List;

public class VentaAdapter extends ArrayAdapter {
    private List<Venta> ventas;
    private int layout;
    private Context context;


    public VentaAdapter(@NonNull Context context, int resource, @NonNull List<Venta> ventas) {
        super(context, resource, ventas);
        this.layout = resource;
        this.context = context;
        this.ventas = ventas;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.venta_layout, parent, false);
        }
        Venta venta = ventas.get(position);
        TextView fecha = listItem.findViewById(R.id.lblFechaVenta);
        TextView cliente = listItem.findViewById(R.id.lblNombreClienteVenta);
        TextView totalVenta = listItem.findViewById(R.id.lblTotalVenta);
        TextView estado = listItem.findViewById(R.id.lblEstadoVenta);
        String fechaVenta = venta.getFechaVenta();
        fechaVenta = fechaVenta.split("T")[0];
        fechaVenta = fechaVenta.split("-")[2] + "-" + fechaVenta.split("-")[1] + "-" +
                fechaVenta.split("-")[0];
        fecha.setText("Fecha venta: " + fechaVenta);
        estado.setText(venta.getEstado());
        cliente.setText("Cliente: "+venta.getCliente().getNombres()+ " " + venta.getCliente().getApellidos());
        totalVenta.setText("Total: $ " + String.valueOf(venta.getMonto()));
        return listItem;
    }
}
