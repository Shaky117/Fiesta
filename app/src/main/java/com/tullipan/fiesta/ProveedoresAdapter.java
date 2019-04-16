package com.tullipan.fiesta;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProveedoresAdapter extends RecyclerView.Adapter<ProveedoresAdapter.ProveedoresViewHolder> implements Filterable {

    public interface OnItemClickListener {
        void onItemClick(ProveedoresItem proveedoresItem);
    }

    private List<ProveedoresItem> proveedoresList;
    private List<ProveedoresItem> proveedoresListFull;
    private OnItemClickListener listener;

    class ProveedoresViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        ProveedoresViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivItemProveedores);
            textView1 = itemView.findViewById(R.id.txtItemProveedores);
        }
        public void bind(final ProveedoresItem proveedoresItem, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(proveedoresItem);
                }
            });
        }
    }

    ProveedoresAdapter(List<ProveedoresItem> proveedoresList, OnItemClickListener listener) {
        this.proveedoresList = proveedoresList;
        proveedoresListFull = new ArrayList<>(proveedoresList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProveedoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedores,
                parent, false);
        return new ProveedoresViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedoresViewHolder holder, int position) {
        ProveedoresItem currentItem = proveedoresList.get(position);
        String foto_url = "http://fiesta.mawetecnologias.com/img/uploads/" + currentItem.getFoto();
        Picasso.get().load(foto_url).into(holder.imageView);
        holder.textView1.setText(currentItem.getName());
        holder.bind(proveedoresList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return proveedoresList.size();
    }

    @Override
    public Filter getFilter() {
        return proveedoresFilter;
    }

    private Filter proveedoresFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProveedoresItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(proveedoresListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProveedoresItem item : proveedoresListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getFoto().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            proveedoresList.clear();
            proveedoresList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
