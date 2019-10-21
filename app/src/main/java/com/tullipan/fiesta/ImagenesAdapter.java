package com.tullipan.fiesta;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opensooq.pluto.base.PlutoAdapter;
import com.opensooq.pluto.base.PlutoViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagenesAdapter extends PlutoAdapter<Imagenes, ImagenesAdapter.ImagenesViewHolder> {

    String url= "http://admin.easyparty.mx";

    public ImagenesAdapter(ArrayList<Imagenes> items) {
        super(items);
    }

    @Override
    public ImagenesViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ImagenesViewHolder(parent, R.layout.item_imagenes);
    }

    public static class ImagenesViewHolder extends PlutoViewHolder<Imagenes> {
        ImageView ivPoster;
        Context context;

        public ImagenesViewHolder(ViewGroup parent, int itemLayoutId) {
            super(parent, itemLayoutId);
            ivPoster = getView(R.id.imgCarousel);
            context = parent.getContext();
        }

        @Override
        public void set(Imagenes item, int pos) {
            String url = "http://admin.easyparty.mx/img/uploads/"+ item.getImagen();
            Picasso.with(context).load(url).into(ivPoster);
        }
    }
}