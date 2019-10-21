package com.tullipan.fiesta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventoProveedoresAdapter extends RecyclerView.Adapter<EventoProveedoresAdapter.EventoProveedoresViewHolder> {

    Context context;

    String url = "http://admin.easyparty.mx";

    public interface OnItemClickListener {
        void onItemClick(EventoProveedor eventoProveedor);
    }

    private ArrayList<EventoProveedor> data;
    private EventoProveedoresAdapter.OnItemClickListener listener;
    private int id;

    public EventoProveedoresAdapter(ArrayList<EventoProveedor> data, int id,EventoProveedoresAdapter.OnItemClickListener listener) {
        this.data = data;
        this.id = id;
        this.listener = listener;
    }

    public void mePuedesVer(){

    }

    @Override
    public EventoProveedoresAdapter.EventoProveedoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new EventoProveedoresAdapter.EventoProveedoresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proovedor_evento, parent, false));
    }

    @Override
    public void onBindViewHolder(EventoProveedoresAdapter.EventoProveedoresViewHolder holder, int position) {
        final EventoProveedor eventos = data.get(position);

        holder.tvNombre.setText(eventos.getNombre());
        holder.btnElimiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker eliminar = new BackgroundWorker(context);
                String type = "eliminarProveedor";
                eliminar.execute(type, String.valueOf(id), String.valueOf(eventos.getId()));
            }
        });

        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EventoProveedoresViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        ImageView btnElimiar;

        public EventoProveedoresViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProveedor);
            btnElimiar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final EventoProveedor eventos, final EventoProveedoresAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(eventos);
                }
            });
        }
    }
}
