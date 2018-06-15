package com.example.alonso.controlventas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alonso.controlventas.modelo.Articulo;

import java.util.List;

public class ArticuloAdapter extends ArrayAdapter {
    private List<Articulo> articulos;
    private Context context;
    private int resource;


    public ArticuloAdapter(@NonNull Context context, int resource, @NonNull List<Articulo> articulos) {
        super(context, resource, articulos);
        this.context = context;
        this.articulos = articulos;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(articulos.get(position).getNombre());
        return label;
    }

    @Override
    public Articulo getItem(int position){
        return articulos.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(articulos.get(position).getNombre());

        return label;
    }
}
