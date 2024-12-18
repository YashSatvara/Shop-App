package com.example.shopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Adapter.CategoriesAdapter;
import com.example.shopapp.Adapter.CustomAdapter;
import com.example.shopapp.Adapter.ProductAdapter;
import com.example.shopapp.Adapter.SubscriptionAdapter;
import com.example.shopapp.Model.SubscriptionModel;
import com.example.shopapp.Model.categoriesModel;
import com.example.shopapp.Model.itemsModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    int images[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img4};
    String names[] = {"Timeless Elegance Gold", "Intricate Filigree Gold", "Timeless Elegance Gold", "Geometric Gold Bracelet", "Neckless Gold"};
    String type[] = {"Ring", "Chain", "Necklace", "Pendant", "Golden Earings"};
    String price[] = {"₹ 2500", "₹ 3500", "₹ 5000", "₹ 7000", "₹ 6500"};

    private RecyclerView productRecyclerView,categories;
    private ProductAdapter productAdapter;
    private RecyclerView subscriptionRecyclerView;
    private SubscriptionAdapter subscriptionAdapter;
    private List<SubscriptionModel> subscriptionList;
    private TextView allproduct,message;
    private Button button2;
    CustomAdapter customAdapter;
    List<itemsModel> itemsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize allproduct TextView and set OnClickListener
        allproduct = view.findViewById(R.id.viewallprod);

        message=view.findViewById(R.id.message);
        button2 =view.findViewById(R.id.button2);
        allproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AllProductFragment
                Fragment allProductFragment = new AllProductFragment(); // Replace with your AllProductFragment class
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayout, allProductFragment); // Replace R.id.fragment_container with the actual container ID
                transaction.addToBackStack(null); // Adds to the back stack for backward navigation
                transaction.commit();
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AllProductFragment
                Fragment chatfragment = new ChatFragment(); // Replace with your AllProductFragment class
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayout, chatfragment); // Replace R.id.fragment_container with the actual container ID
                transaction.addToBackStack(null); // Adds to the back stack for backward navigation
                transaction.commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
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


        for (int i = 0; i < names.length; i++) {
            itemsModel item = new itemsModel(names[i], type[i], price[i], images[i]);
            itemsList.add(item);
        }
        // Initialize the first RecyclerView for products
        productRecyclerView = view.findViewById(R.id.productrecview);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        customAdapter = new CustomAdapter(itemsList, getContext());
        productRecyclerView.setAdapter(customAdapter);



        // Initialize the second RecyclerView for subscriptions
        subscriptionRecyclerView = view.findViewById(R.id.precview);
        subscriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize subscription list
        subscriptionList = new ArrayList<>();
        subscriptionList.add(new SubscriptionModel("Basic", "Buy this basic subscription plan...", "₹699", "Valid for 1 month"));
        subscriptionList.add(new SubscriptionModel("Premium", "Get unlimited access to all...", "₹1299", "Valid for 3 months"));

        // Set up the subscription adapter
        subscriptionAdapter = new SubscriptionAdapter(getContext(), subscriptionList);
        subscriptionRecyclerView.setAdapter(subscriptionAdapter);


        categories = view.findViewById(R.id.categories);
        categories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Create and set data for the adapter
        List<categoriesModel> categoryList = new ArrayList<>();
        categoryList.add(new categoriesModel("Ring", R.drawable.ring_2));
        categoryList.add(new categoriesModel("Necklaces", R.drawable.neckless_2));
        categoryList.add(new categoriesModel("Earing", R.drawable.earing));

        // Add more categories as needed

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoryList);
        categories.setAdapter(categoriesAdapter);


        return view;
    }
}
