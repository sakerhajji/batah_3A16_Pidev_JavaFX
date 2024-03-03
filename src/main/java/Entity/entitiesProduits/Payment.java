package Entity.entitiesProduits;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OpRsKDnblNBKzE2kK39OD555ii9NMHPi52n456XQUvASMfRlh048Zx6xuIJpT3HzQd0OnEthJF1kReBj4NgIG4O00Stf20Tdg";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}