package com.example.shopapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.CartManager;
import com.example.shopapp.MainActivity;
import com.example.shopapp.Model.itemsModel;
import com.example.shopapp.ProductDetailFragment;
import com.example.shopapp.R;
import com.example.shopapp.ViewSubscriptionFragment;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<itemsModel> itemsModelsList;
    private Context context;
    private static final String PREFS_NAME = "ProductDetails";

    public CustomAdapter(List<itemsModel> itemsModelsList, Context context) {
        this.itemsModelsList = itemsModelsList;
        this.context = context;
    }

    public void setFilteredList(List<itemsModel> filteredList) {
        this.itemsModelsList = filteredList; // Update the data in the adapter
        notifyDataSetChanged(); // Notify the adapter to refresh the view
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data for each item
        itemsModel item = itemsModelsList.get(position);

        holder.imageView.setImageResource(item.getImage());
        holder.tvName.setText(item.getName());
        holder.tvType.setText(item.getType());
        holder.tvPrice.setText(item.getPrice());

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.addToCart(item);
                Toast.makeText(context, item.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        holder.productLayout.setOnClickListener(view -> {
            // Store selected subscription details in SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", item.getName());
            editor.putString("description", item.getType());
            editor.putString("price", item.getPrice());
            editor.putInt("productImage", item.getImage());
            editor.apply();

            // Navigate to ViewSubscriptionFragment
            ProductDetailFragment productDetailFragment = new ProductDetailFragment();
            ((MainActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.linearLayout, productDetailFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return itemsModelsList.size(); // Return the size of the list
    }

    // ViewHolder class for RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvName, tvType, tvPrice;
        public Button addToCartButton;
        public LinearLayout productLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            tvName = itemView.findViewById(R.id.tvname);
            tvType = itemView.findViewById(R.id.tvtype);
            tvPrice = itemView.findViewById(R.id.tvprice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            productLayout = itemView.findViewById(R.id.productdetaillayout);
        }
    }
}
