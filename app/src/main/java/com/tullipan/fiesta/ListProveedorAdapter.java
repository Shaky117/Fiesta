package com.tullipan.fiesta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListProveedorAdapter extends RecyclerView.Adapter<ListProveedorAdapter.ListProveedorViewHolder> {

    Context context;

    String url = "http://admin.easyparty.mx";

    public interface OnItemClickListener {
        void onItemClick(ProveedoresItem eventoProveedor);
    }

    private ArrayList<ProveedoresItem> data;
    private ListProveedorAdapter.OnItemClickListener listener;
    private int id;

    public ListProveedorAdapter(ArrayList<ProveedoresItem> data, int id,ListProveedorAdapter.OnItemClickListener listener) {
        this.data = data;
        this.id = id;
        this.listener = listener;
    }

    @Override
    public ListProveedorAdapter.ListProveedorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ListProveedorAdapter.ListProveedorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_proveedores, parent, false));
    }

    @Override
    public void onBindViewHolder(final ListProveedorAdapter.ListProveedorViewHolder holder, int position) {
        final ProveedoresItem proveedoresItem = data.get(position);

        holder.tvNombre.setText(proveedoresItem.getName());

        holder.ivAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker add = new BackgroundWorker(context);
                String type = "addProveedor" ;
                add.execute(type, String.valueOf(id), String.valueOf(proveedoresItem.getId()));
            }
        });

        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListProveedorViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        ImageView ivAgregar;

        public ListProveedorViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            ivAgregar = itemView.findViewById(R.id.btnAgregar);
        }

        public void bind(final ProveedoresItem proveedoresItem, final ListProveedorAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(proveedoresItem);
                }
            });
        }
    }
}
