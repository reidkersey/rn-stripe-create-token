
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactApplication;

import com.stripe.android.model.Card;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Token;
import com.stripe.android.model.BankAccount;

import java.util.Date;
import java.util.Map;

import android.widget.Toast;
import android.content.Context;

public class RNRnStripeCreateTokenModule extends ReactContextBaseJavaModule {
  private static final String CARD_INVALID = "CARD_INVALID";
  private static final String CARD_VALID = "CARD_VALID";
  private static final String BAD_TOKEN = "BAD_TOKEN";

  private final ReactApplicationContext reactContext;

  public RNRnStripeCreateTokenModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNRnStripeCreateToken";
  }

  @ReactMethod
  public void show(String message, int duration) {
    Toast.makeText(getReactApplicationContext(), message, duration).show();
  }

  @ReactMethod
  public void incremen(int i, Promise promise) {
    i += 3;
    promise.resolve(i);
  }

  @ReactMethod
  public void validateCard(String cardNumber, Integer cardExpMonth, Integer cardExpYear, String cardCVC, Promise promise) {
    Card card = new Card(
      cardNumber,
      cardExpMonth,
      cardExpYear,
      cardCVC
    );

    if (card.validateCard()) {
      promise.resolve(CARD_VALID);
    }else{
      promise.reject(CARD_INVALID);
    }
  }

  @ReactMethod
  public void createToken(String cardNumber, Integer cardExpMonth, Integer cardExpYear, String cardCVC, String name, String zip, final Promise promise) {
    this.createToken_all(cardNumber, cardExpMonth, cardExpYear, cardCVC, name, zip, null, null, null, null, null, null, promise);
  }

  @ReactMethod
  public void createToken_all(String cardNumber, Integer cardExpMonth, Integer cardExpYear, String cardCVC, String name, String zip,
            String addressLine1,
            String addressLine2,
            String addressCity,
            String addressState,
            String addressCountry,
            String currency,
      final Promise promise) {
    Context ctx = reactContext.getApplicationContext();
    
    Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
    card.setName(name);
    card.setAddressLine1(addressLine1);
    card.setAddressLine2(addressLine2);
    card.setAddressCity(addressCity);
    card.setAddressState(addressState);
    card.setAddressZip(zip);
    card.setAddressCountry(addressCountry);
    card.setCurrency(currency);

    if (!card.validateCard()) {
      promise.reject(CARD_INVALID);
      return;
    }

    Stripe stripe = new Stripe(ctx, "pk_test_N9MRUopzQht3lFPk3U9r32yq");
    stripe.createToken(
      card,
      new TokenCallback() {
        public void onSuccess(Token token) {
          WritableMap map = Arguments.createMap();
          WritableMap baMap = Arguments.createMap();
          WritableMap cardMap = Arguments.createMap();

          map.putString("id", token.getId());
          map.putBoolean("livemode", token.getLivemode());
          map.putBoolean("used", token.getUsed());
          map.putString("type", token.getType());
          map.putString("object", "token");

          BankAccount ba = token.getBankAccount();
          if(ba == null){
            map.putNull("bank_account");
          }else{
            baMap.putString("account_holder_name", ba.getAccountHolderName());
            baMap.putString("account_holder_type", ba.getAccountHolderType());
            baMap.putString("bank_name", ba.getBankName());
            baMap.putString("country", ba.getCountryCode());
            baMap.putString("currency", ba.getCurrency());
            baMap.putString("fingerprint", ba.getFingerprint());
            baMap.putString("last4", ba.getLast4());
            baMap.putString("routing_number", ba.getRoutingNumber());
            map.putMap("bank_account", baMap);
          }

          Card cd = token.getCard();
          if(cd == null){
            map.putNull("card");
          }else{
            cardMap.putString("id", cd.getId());
            cardMap.putString("object", "card");
            cardMap.putString("address_city", cd.getAddressCity());
            cardMap.putString("address_country", cd.getCountry());
            cardMap.putString("address_line1", cd.getAddressLine1());
            cardMap.putString("address_line1_check", cd.getAddressLine1Check());
            cardMap.putString("address_line2", cd.getAddressLine2());
            cardMap.putString("address_state", cd.getAddressState());
            cardMap.putString("address_zip", cd.getAddressZip());
            cardMap.putString("address_zip_check", cd.getAddressZipCheck());
            cardMap.putString("brand", cd.getBrand());
            cardMap.putString("country", cd.getCountry());
            cardMap.putString("currency", cd.getCurrency());
            cardMap.putString("cvc_check", cd.getCvcCheck());
            cardMap.putInt("exp_month", cd.getExpMonth());
            cardMap.putInt("exp_year", cd.getExpYear());
            cardMap.putString("fingerprint", cd.getFingerprint());
            cardMap.putString("funding", cd.getFunding());
            cardMap.putString("last4", cd.getLast4());
            cardMap.putString("name", cd.getName());
            map.putMap("card", cardMap);
          }

          promise.resolve(map);
        }
        public void onError(Exception error) {
          // Show localized error message
          promise.reject(BAD_TOKEN, error.getMessage());
        }
      }
    );

  }
}