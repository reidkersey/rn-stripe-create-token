
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.widget.Toast;

public class RNRnStripeCreateTokenModule extends ReactContextBaseJavaModule {

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
  public void show(int i Promise promise) {
    i += 3;
    promise.resolve(i);
  }

  @ReactMethod
  public void validateCard(String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC, Promise promise) {
    Card card = new Card(
      cardNumber,
      cardExpMonth,
      cardExpYear,
      cardCVC
    );

    if (card.validateCard()) {
      promise.resolve();
    }else{
      promise.reject(CARD_INVALID);
    }
  }

  @ReactMethod
  public void createToken(String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC,
      Promise promise) {
    Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);

    if (!card.validateCard()) {
      promise.reject(CARD_INVALID);
      return;
    }

    Stripe stripe = new Stripe(mContext, "pk_test_TYooMQauvdEDq54NiTphI7jx");
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

          Date created = token.getCreated();
          map.putDouble("created", Double(date.getTime()));

          BankAccount ba = token.getBankAccount();
          if(ba == null){
            map.putNull("bank_account");
          }else{
            baMap.putString("id", ba.getId());
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
            cardMap.putString("exp_month", cd.getExpMonth());
            cardMap.putString("exp_year", cd.getExpYear());
            cardMap.putString("fingerprint", cd.getFingerprint());
            cardMap.putString("funding", cd.getFunding());
            cardMap.putString("last4", cd.getLast4());
            cardMap.putMap("metadata", cd.getMetadata());
            cardMap.putString("name", cd.getName());
            cardMap.putString("tokenization_method", cd.getTokenizationMethod());
            map.putMap("card", cardMap);
          }

          promise.resolve(map);
        }
        public void onError(Exception error) {
          // Show localized error message
          promise.reject(BAD_TOKEN, e.getMessage());
        }
      }
    );

  }
}