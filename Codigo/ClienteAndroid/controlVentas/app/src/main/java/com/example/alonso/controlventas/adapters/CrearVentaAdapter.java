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
import com.example.alonso.controlventas.modelo.ArticulosVenta;

import java.util.List;

public class CrearVentaAdapter extends ArrayAdapter {
    private List<ArticulosVenta> articulos;
    private int layout;
    private Context context;


    public CrearVentaAdapter(@NonNull Context context, int resource, List<ArticulosVenta> articulos) {
        super(context, resource,articulos);
        this.layout = resource;
        this.context = context;
        this.articulos = articulos;
    }

    public void refreshEvents() {
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.crear_venta_layout, parent, false);
        }

        ArticulosVenta venta = articulos.get(position);
        TextView cantidad = (TextView) listItem.findViewById(R.id.lblCantidadProducto);
        TextView nombreArticulo = (TextView) listItem.findViewById(R.id.lblNombreProducto);
        TextView precio = (TextView) listItem.findViewById(R.id.lblPrecio);

        cantidad.setText(String.valueOf(venta.getCantidadArticulos()));
        nombreArticulo.setText(venta.getArticulo().getNombre());
        precio.setText(String.valueOf(venta.getPrecioTotal()));
        return listItem;
    }

}
