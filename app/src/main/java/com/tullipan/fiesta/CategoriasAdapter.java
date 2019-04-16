package com.tullipan.fiesta;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriasViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Categorias categorias);
    }

    private ArrayList<Categorias> data;
    private OnItemClickListener listener;

    public CategoriasAdapter(ArrayList<Categorias> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }
    @Override
    public CategoriasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoriasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoriasViewHolder holder, int position) {
        Categorias categorias = data.get(position);
        /*String path = ""+categorias.getImagen();
        Picasso.get().load(path).into(holder.imgLogo);*/
       // holder.imgLogo.setImageResource(categorias.getImagen());
        holder.tvText.setText(categorias.getCategoria());
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CategoriasViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLogo;
        TextView tvText;


        public CategoriasViewHolder(View itemView) {
            super(itemView);
            imgLogo = (ImageView) itemView.findViewById(R.id.ivItemCategoria);
            tvText = itemView.findViewById(R.id.txtItemCategoria);
        }

        public void bind(final Categorias categorias, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(categorias);
                }
            });
        }
    }
}
