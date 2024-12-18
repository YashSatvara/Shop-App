package com.example.shopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Adapter.CartAdapter;
import com.example.shopapp.Model.itemsModel;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private Button checkoutButton;

    // UI elements for totals
    private TextView totalProductPrice;
    private TextView shippingCharges;
    private TextView orderDiscount;
    private TextView finalPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize Views
        cartRecyclerView = view.findViewById(R.id.cartrecyclerview);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        // Initialize TextViews for totals
        totalProductPrice = view.findViewById(R.id.totalProductPrice);
        shippingCharges = view.findViewById(R.id.shippingCharges);
        orderDiscount = view.findViewById(R.id.orderDiscount);
        finalPrice = view.findViewById(R.id.finalPrice);

        // Retrieve Cart Items
        List<itemsModel> cartItems = CartManager.getCartItems();

        // Set Up RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Pass TextViews to the adapter
        cartAdapter = new CartAdapter(cartItems, getContext(), totalProductPrice, shippingCharges, orderDiscount, finalPrice);
        cartRecyclerView.setAdapter(cartAdapter);

        // Checkout Button Logic
        checkoutButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Proceed to checkout", Toast.LENGTH_SHORT).show();
            navigateToPurchaseFragment();
        });

        // Back Button Handling
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackNavigation();
            }
        });

        return view;
    }

    // Navigate to PurchaseFragment
    private void navigateToPurchaseFragment() {
        Fragment purchaseFragment = new purchaseFragment(); // Replace with your purchase fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayout, purchaseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Handle Back Navigation
    private void handleBackNavigation() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.linearLayout, new HomeFragment())
                    .commit();
        }
    }
}
