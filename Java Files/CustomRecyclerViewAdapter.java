package com.example.projectmaddoulingoclone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<FruitItem> items;
    private final OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // To track selected item

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CustomRecyclerViewAdapter(Context context, List<FruitItem> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageIcon);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FruitItem item = items.get(position);

        // Set item text and image
        holder.textView.setText(item.getName());
        holder.imageView.setImageResource(item.getImageResId());

        // Highlight the selected item
        if (holder.getAdapterPosition() == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.combined_input_border); // Highlight background
        }else{
            holder.itemView.setBackgroundResource(R.drawable.border); // Default background
        }

        // Set item click listener
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition(); // Get updated position dynamically

            // Notify adapter to refresh the previously selected and currently selected items
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            listener.onItemClick(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
