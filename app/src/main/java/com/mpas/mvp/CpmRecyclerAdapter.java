package com.mpas.mvp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.ui.main.CpmFragment;

import java.util.List;

public class CpmRecyclerAdapter extends RecyclerView.Adapter<CpmRecyclerAdapter.ViewHolder> {
    Context context;
    List<CpmItem> items;
    int item_layout;

    public CpmRecyclerAdapter(Context context, List<CpmItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cpmcardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CpmItem item = items.get(position);

        Drawable drawable = ContextCompat.getDrawable(context, item.getImage());
        holder.image.setImageDrawable(drawable);
        holder.cardview.setOnClickListener(v -> {
            Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            CpmFragment.setBarcode(item.getTitle());
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            cardview = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}

