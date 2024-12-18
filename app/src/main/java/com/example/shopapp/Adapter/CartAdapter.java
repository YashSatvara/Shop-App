package com.example.shopapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Model.itemsModel;
import com.example.shopapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<itemsModel> cartItems;
    private Context context;
    private TextView totalProductPrice, shippingCharges, orderDiscount, finalPrice;

    private static final int SHIPPING_CHARGE = 50; // Example flat rate
    private static final int DISCOUNT = 10; // Example flat discount

    public CartAdapter(List<itemsModel> cartItems, Context context,
                       TextView totalProductPrice, TextView shippingCharges, TextView orderDiscount, TextView finalPrice) {
        this.cartItems = cartItems;
        this.context = context;
        this.totalProductPrice = totalProductPrice;
        this.shippingCharges = shippingCharges;
        this.orderDiscount = orderDiscount;
        this.finalPrice = finalPrice;
        updateTotals();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_list, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        itemsModel item = cartItems.get(position);

        // Set dynamic data to the views
        holder.imageView.setImageResource(item.getImage());
        holder.tvName.setText(item.getName());
        holder.tvType.setText(item.getType());
        holder.tvPrice.setText("Rs " + item.getPrice());

        // Handle remove item
        holder.removeItem.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, item.getName() + " removed from cart", Toast.LENGTH_SHORT).show();
            updateTotals();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Update the totals dynamically
    private void updateTotals() {
        int totalPrice = 0;

        // Check if cartItems is empty or null
        if (cartItems == null || cartItems.isEmpty()) {
            Log.d("CartAdapter", "Cart is empty or null");
        } else {
            for (itemsModel item : cartItems) {
                String priceStr = item.getPrice().replace("Rs", "").trim(); // Remove "Rs" and spaces
                Log.d("CartAdapter", "Price before parsing: " + priceStr);

                try {
                    int price = Integer.parseInt(priceStr); // Parse the price
                    totalPrice += price; // Add to total
                    Log.d("CartAdapter", "Price added: " + price);
                } catch (NumberFormatException e) {
                    Log.e("CartAdapter", "Error parsing price: " + priceStr, e);
                }
            }
        }

        // Calculate other totals
        int shippingCharge = SHIPPING_CHARGE;
        int discount = DISCOUNT;
        int finalAmount = totalPrice + shippingCharge - discount;

        // Log the final totals
        Log.d("CartAdapter", "Total Price: " + totalPrice);
        Log.d("CartAdapter", "Shipping Charge: " + shippingCharge);
        Log.d("CartAdapter", "Discount: " + discount);
        Log.d("CartAdapter", "Final Price: " + finalAmount);

        // Update the UI
        if (totalProductPrice != null) {
            totalProductPrice.setText("Rs " + totalPrice);
        }
        if (shippingCharges != null) {
            shippingCharges.setText("Rs " + shippingCharge);
        }
        if (orderDiscount != null) {
            orderDiscount.setText("Rs " + discount);
        }
        if (finalPrice != null) {
            finalPrice.setText("Rs " + finalAmount);
        }
    }




    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, removeItem;
        public TextView tvName, tvType, tvPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_item);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvType = itemView.findViewById(R.id.tv_item_desc);
            tvPrice = itemView.findViewById(R.id.tv_item_price);
            removeItem = itemView.findViewById(R.id.iv_deleteitem);
        }
    }
}
