package com.example.alonso.controlventas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alonso.controlventas.modelo.Cliente;

import java.util.List;

public class SpinAdapter extends ArrayAdapter {
    private Context context;
    private List<Cliente> clientes;

    public SpinAdapter(Context context, int textViewResourceId,
                       List<Cliente> clientes) {
        super(context, textViewResourceId, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Cliente getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(clientes.get(position).getNombres() + " " + clientes.get(position).getApellidos());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(clientes.get(position).getNombres() + " " + clientes.get(position).getApellidos());

        return label;
    }
}
