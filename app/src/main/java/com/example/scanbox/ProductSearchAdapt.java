package com.example.scanbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

public class ProductSearchAdapt extends ListAdapter<ProductSearchResult, ProductSearchAdapt.ProductViewHolder> {

    protected ProductSearchAdapt() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ProductSearchResult> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductSearchResult>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductSearchResult oldItem, @NonNull ProductSearchResult newItem) {
            return oldItem.getImageId().equals(newItem.getImageId()) && oldItem.getImageUri().equals(newItem.getImageUri());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductSearchResult oldItem, @NonNull ProductSearchResult newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductName;
        private final TextView tvProductScore;
        private final TextView tvProductLabel;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductScore = itemView.findViewById(R.id.tvProductScore);
            tvProductLabel = itemView.findViewById(R.id.tvProductLabel);
        }

        void bind(ProductSearchResult product) {
            tvProductName.setText("Name: " + product.getName());
            tvProductScore.setText("Similarity score: " + product.getScore());
            tvProductLabel.setText("Labels: " + product.getLabel());
            Glide.with(itemView).load(product.getImageUri()).into((android.widget.ImageView) itemView.findViewById(R.id.ivProduct));
        }
    }
}