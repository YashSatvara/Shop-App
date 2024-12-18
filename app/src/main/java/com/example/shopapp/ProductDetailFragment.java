package com.example.shopapp;

import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductDetailFragment extends Fragment {

    private ImageView productImage;
    private TextView productName, productPrice,productDescription;
    private Button payment_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        productImage = view.findViewById(R.id.prodimg);
        productName = view.findViewById(R.id.prodname);
        productDescription = view.findViewById(R.id.productDescription);
        productPrice = view.findViewById(R.id.prodprice);
        payment_btn = view.findViewById(R.id.payment_btn);


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ProductDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "N/A");
        String description = sharedPreferences.getString("description", "N/A");
        String price = sharedPreferences.getString("price", "N/A");
        int image = sharedPreferences.getInt("productImage", R.drawable.img1);

        productName.setText(name);
        productPrice.setText(price);
        productDescription.setText(description);
        productImage.setImageResource(image);

        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AllProductFragment
                Fragment purchaseFragment = new purchaseFragment(); // Replace with your AllProductFragment class
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayout, purchaseFragment); // Replace R.id.fragment_container with the actual container ID
                transaction.addToBackStack(null); // Adds to the back stack for backward navigation
                transaction.commit();
            }
        });


        return view;
    }
}