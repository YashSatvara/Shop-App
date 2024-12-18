package com.example.shopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ViewSubscriptionFragment extends Fragment {

    private static final String PREFS_NAME = "SubscriptionPrefs";
    private TextView titleTextView, descriptionTextView, priceTextView, validityTextView;
    private Button payment2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_subscription, container, false);

        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        validityTextView = view.findViewById(R.id.validityTextView);
        payment2 = view.findViewById(R.id.payment2);

        loadSubscriptionDetails();

        payment2.setOnClickListener(new View.OnClickListener() {
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

    private void loadSubscriptionDetails() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        titleTextView.setText(sharedPreferences.getString("title", "N/A"));
        descriptionTextView.setText(sharedPreferences.getString("description", "N/A"));
        priceTextView.setText(sharedPreferences.getString("price", "N/A"));
        validityTextView.setText(sharedPreferences.getString("validity", "N/A"));
    }
}
