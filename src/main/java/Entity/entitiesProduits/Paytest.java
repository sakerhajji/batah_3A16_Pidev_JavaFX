package Entity.entitiesProduits;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;

import java.util.HashMap;
import java.util.Map;

public class Paytest {


    public static void main(String[] args) throws StripeException {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OpRsKDnblNBKzE2kK39OD555ii9NMHPi52n456XQUvASMfRlh048Zx6xuIJpT3HzQd0OnEthJF1kReBj4NgIG4O00Stf20Tdg";
        Customer a=Customer.retrieve("cus_PfNMTRDVzGFjyL");
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", " 4242 4242 4242 4242");
        cardParams.put("exp_month", 12);
        cardParams.put("exp_year", 2034);
        cardParams.put("cvc", "123");

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);

        // Attach the Token to the Customer's sources
        Map<String, Object> sourceParams = new HashMap<>();
        sourceParams.put("source", token.getId());
        a.getSources().create(sourceParams);

        // Print the updated customer information
        System.out.println(a);


    }

}