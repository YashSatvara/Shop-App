package com.example.shopapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

public class purchaseFragment extends Fragment implements PaymentResultListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public purchaseFragment() {
        // Required empty public constructor
    }

    public static purchaseFragment newInstance(String param1, String param2) {
        purchaseFragment fragment = new purchaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_purchase, container, false);

        // Initialize the Razorpay payment button
        rootView.findViewById(R.id.btn_placeorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        return rootView;
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_KRCDODFgP0fIyM"); // Replace with your Razorpay Key

        JSONObject paymentDetails = new JSONObject();
        try {
            paymentDetails.put("name", "Shop App");
            paymentDetails.put("description", "Test Payment");
            paymentDetails.put("currency", "INR");

            // Amount in rupees (e.g., Rs 200)
            double amountInRupees = 200.0; // Update the amount as needed
            // Convert the amount to paise (1 INR = 100 paise)
            int amountInPaise = (int) (amountInRupees * 100);

            paymentDetails.put("amount", amountInPaise); // Razorpay requires the amount in paise
            paymentDetails.put("prefill", new JSONObject()
                    .put("email", "test@example.com")
                    .put("contact", "1234567890"));

            // Ensure the activity is valid before opening Razorpay
            if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
                checkout.open(getActivity(), paymentDetails);
            } else {
                // Handle the case when the fragment or activity is not valid
                // You can show a message or log the error here
                System.out.println("Error: Activity or Fragment is not valid.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String paymentId) {
        // Handle payment success
        // Example: Toast.makeText(getContext(), "Payment Successful! Payment ID: " + paymentId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        // Handle payment failure
        // Example: Toast.makeText(getContext(), "Payment Failed: " + response, Toast.LENGTH_LONG).show();
    }
}
