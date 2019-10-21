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

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    Context context;

    String url= "http://admin.easyparty.mx";

    public interface OnItemClickListener {
        void onItemClick(NoticiasItem noticiasItem);
    }

    private ArrayList<NoticiasItem> data;
    private NoticiasAdapter.OnItemClickListener listener;

    public NoticiasAdapter(ArrayList<NoticiasItem> data, NoticiasAdapter.OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }
    @Override
    public NoticiasAdapter.NoticiasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NoticiasAdapter.NoticiasViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticiasAdapter.NoticiasViewHolder holder, int position) {
        NoticiasItem noticiasItem = data.get(position);
        String path = url + "/img/uploads/" + noticiasItem.getImagen();
        Picasso.with(context).load(path).fit().into(holder.imgLogo);
        holder.tvText.setText(noticiasItem.getNoticia());
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoticiasViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLogo;
        TextView tvText;
        CardView cvCategorias;

        public NoticiasViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.ivImagenNoticia);
            tvText = itemView.findViewById(R.id.txtNoticias);
            cvCategorias = itemView.findViewById(R.id.cvCategoria);
        }

        public void bind(final NoticiasItem noticiasItem, final NoticiasAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(noticiasItem);
                }
            });
        }
    }
}
