package com.tullipan.fiesta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.squareup.picasso.Picasso;

import java.nio.file.Path;
import java.util.ArrayList;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriasViewHolder> {

    Context context;

    String url= "http://admin.easyparty.mx";

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
        context = parent.getContext();
        return new CategoriasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoriasViewHolder holder, int position) {
        Categorias categorias = data.get(position);
        String path = url + "/img/iconos/" +categorias.getImagen();
        Picasso.with(context).load(path).fit().into(holder.imgLogo);

        holder.tvText.setText(categorias.getCategoria());
        holder.tvText.setVisibility(View.INVISIBLE);
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CategoriasViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLogo;
        TextView tvText;
        CardView cvCategorias;

        public CategoriasViewHolder(View itemView) {
            super(itemView);
            imgLogo = (ImageView) itemView.findViewById(R.id.ivItemCategoria);
            tvText = itemView.findViewById(R.id.txtItemCategoria);
            cvCategorias = itemView.findViewById(R.id.cvCategoria);
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
