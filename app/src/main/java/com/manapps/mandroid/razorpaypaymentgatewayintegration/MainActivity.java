package com.manapps.mandroid.razorpaypaymentgatewayintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    Button startPaymentGateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPaymentGateway = findViewById(R.id.startPaymentGateway);

        startPaymentGateway.setOnClickListener(view -> {
            // this will show error untill you replace real keys with demo keys in startPayment function.
            startPayment("123", "INR", "1");
        });

    }

    public void startPayment(String order_id, String currency_code, String payable_amount) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        //   checkout.setImage(R.drawable.ic_launcher_background);

        /**
         * Reference to current activity
         */
        final Activity activity = this;
        //put key here which is provided to you when registering to razorpay website.  
        //checkout.setKeyID("rzp_live_key");
        checkout.setKeyID("rzp_test_key");
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "RazorPay Payment Integration Demo");
            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            //   options.put("description", "Reference No. #123456");
            options.put("order_id", order_id);
            options.put("currency", currency_code);
            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", payable_amount);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("Tag", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.e("success: ", paymentData.toString());
        Log.e("success: ", paymentData.getSignature());
        Log.e("success: ", paymentData.getOrderId());
        Log.e("success: ", paymentData.getPaymentId());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e("fail: ", String.valueOf(paymentData.getPaymentId()));
        Log.e("fail: ", paymentData.toString());
        Log.e("failed message : ", s);
        // this toast message is only shown here for git project to tell user to change keys only.
        Toast.makeText(this, "Use Real Keys provided from RazorPay to see functionality", Toast.LENGTH_SHORT).show();
    }
}