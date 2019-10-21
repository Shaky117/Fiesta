package com.tullipan.fiesta;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> {

    Context context;

    String url= "http://admin.easyparty.mx";

    public interface OnItemClickListener {
        void onItemClick(Eventos eventos);
    }

    private ArrayList<Eventos> data;
    private EventosAdapter.OnItemClickListener listener;

    public EventosAdapter(ArrayList<Eventos> data, EventosAdapter.OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }
    @Override
    public EventosAdapter.EventosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new EventosAdapter.EventosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false));
    }

    @Override
    public void onBindViewHolder(EventosAdapter.EventosViewHolder holder, int position) {
        Eventos eventos = data.get(position);

        holder.tvNombre.setText(eventos.getNombre());
        holder.tvFecha.setText(eventos.getFecha());
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EventosViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombre;
        TextView tvFecha;

        public EventosViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }

        public void bind(final Eventos eventos, final EventosAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(eventos);
                }
            });
        }
    }
}
