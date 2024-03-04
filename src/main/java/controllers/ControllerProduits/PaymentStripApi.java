package controllers.ControllerProduits;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.HashMap;
import java.util.Map;

import static controllers.ControllerProduits.PaiementController.floatToInt;


public class  PaymentStripApi {

    public static void pay(float dtAmount) throws StripeException {
        // Convert DT to USD using the current exchange rate
        float exchangeRate = 0.032f; // Replace with the actual exchange rate

        // Convert to USD
        float usdAmount = dtAmount * exchangeRate;

        // Check if the USD amount exceeds the maximum allowed by Stripe
        if (usdAmount > 999999.99) {
            // Handle the error appropriately (e.g., notify the user)
            System.out.println("Error: Amount exceeds the maximum allowed by Stripe.");
        } else {
            Stripe.apiKey = "sk_test_51OpRsKDnblNBKzE2kK39OD555ii9NMHPi52n456XQUvASMfRlh048Zx6xuIJpT3HzQd0OnEthJF1kReBj4NgIG4O00Stf20Tdg";
            Map<String, Object> params = new HashMap<>();
            params.put("amount", floatToInt(usdAmount));
            params.put("currency", "usd");
            /*params.put("customer", "cus_PfJJD1rAVR524l");

            Charge charge = Charge.create(params);
            System.out.println(charge);

*/  PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount((long) floatToInt(usdAmount))
                    .setCurrency("usd")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
            System.out.println(paymentIntent);
        }
    }
   /*private void processPayment() {
       try {
// Set your secret key here
           Stripe.apiKey = "sk_test_51OpRsKDnblNBKzE2kK39OD555ii9NMHPi52n456XQUvASMfRlh048Zx6xuIJpT3HzQd0OnEthJF1kReBj4NgIG4O00Stf20Tdg";

// Create a PaymentIntent with other payment details
           PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                   .setAmount(1000L) // Amount in cents (e.g., $10.00)
                   .setCurrency("usd")
                   .build();

           PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
           System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
       } catch (StripeException e) {
// If there was an error processing the payment, display the error message
           System.out.println("Payment failed. Error: " + e.getMessage());
       }
   }*/
}


