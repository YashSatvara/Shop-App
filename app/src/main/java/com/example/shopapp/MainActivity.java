package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private LinearLayout linearLayout;  // Change to LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomView);
        linearLayout = findViewById(R.id.linearLayout);  // Updated to reference the LinearLayout

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.nav_shop) {
                    loadFragment(new ShopFragment(), false);
                } else if (itemId == R.id.nav_loyalty) {
                    loadFragment(new LoyaltyFragment(), false);
                } else if (itemId == R.id.nav_price) {
                    loadFragment(new PriceFragment(), false);
                } else if (itemId == R.id.nav_more) {
                    loadFragment(new MoreFragment(), false);
                }

                return true;
            }
        });

        loadFragment(new HomeFragment(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.message1) {
            loadFragment(new ChatFragment(), false); // Load the CartFragment
            return true;
        } else if (id == R.id.notification) {
            loadFragment(new Notification(), false);
            return true;
        } else if (id == R.id.cart) {
            // Load CartFragment when cart is clicked
            loadFragment(new CartFragment(), false); // Load the CartFragment
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace content in the LinearLayout
        if (isAppInitialized) {
            fragmentTransaction.add(R.id.linearLayout, fragment);  // Use LinearLayout ID
        } else {
            fragmentTransaction.replace(R.id.linearLayout, fragment);  // Use LinearLayout ID
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // If there's something in the back stack, pop the last fragment
            fragmentManager.popBackStack();
        } else {
            // If no fragments are left, call super to handle default back action
            super.onBackPressed();
        }
    }

}
