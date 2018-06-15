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
import com.example.alonso.controlventas.modelo.Cliente;

import java.util.List;

public class ClienteAdapter extends ArrayAdapter{
    private List<Cliente> clientes;
    private int layout;
    private Context context;


    public ClienteAdapter(@NonNull Context context, int resource, @NonNull List<Cliente> clientes) {
        super(context, resource, clientes);
        this.layout = resource;
        this.context = context;
        this.clientes = clientes;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.cliente_layout, parent, false);
        }
        Cliente cliente = clientes.get(position);
        TextView nombre = listItem.findViewById(R.id.lblNombreCliente);
        EditText direccion = listItem.findViewById(R.id.txtDireccionCliente);
        TextView correoElectronico = listItem.findViewById(R.id.lblCorreoCliente);
        direccion.setText(cliente.getDireccion());
        nombre.setText("Cliente: "+cliente.getNombres() + " " + cliente.getApellidos());
        correoElectronico.setText("Correo: "+cliente.getCorreoElectronico());
        return listItem;
    }
}
