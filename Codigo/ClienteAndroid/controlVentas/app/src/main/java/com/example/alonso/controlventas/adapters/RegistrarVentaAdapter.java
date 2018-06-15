package com.example.alonso.controlventas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alonso.controlventas.R;
import com.example.alonso.controlventas.modelo.Articulo;
import com.example.alonso.controlventas.modelo.ArticulosVenta;
import com.example.alonso.controlventas.modelo.Venta;

import java.util.List;

public class RegistrarVentaAdapter extends ArrayAdapter {
    private List<ArticulosVenta> articulosVentas;
    private Context context;
    private int layout;

    public RegistrarVentaAdapter(@NonNull Context context, int resource, @NonNull List<ArticulosVenta> articulosVentas) {
        super(context, resource, articulosVentas);
        this.layout = resource;
        this.context = context;
        this.articulosVentas = articulosVentas;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.venta_layout, parent, false);
        }
        ArticulosVenta venta = articulosVentas.get(position);
        TextView cantidad = listItem.findViewById(R.id.lblCantidadProducto);
        TextView nombreProducto = listItem.findViewById(R.id.lblNombreProducto);
        TextView ventaTotal = listItem.findViewById(R.id.lblPrecio);
        cantidad.setText(venta.getCantidadArticulos());
        nombreProducto.setText(venta.getArticulo().getNombre());
        ventaTotal.setText(String.valueOf(venta.getPrecioTotal()));
        return listItem;
    }



}
