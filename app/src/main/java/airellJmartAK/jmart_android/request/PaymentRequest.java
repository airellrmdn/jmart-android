package airellJmartAK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import airellJmartAK.jmart_android.LoginActivity;
import airellJmartAK.jmart_android.ProductFragment;

/**
 * PaymentRequest Class
 *
 * PaymentRequest berfungsi untuk menghubungkan ke back-end sehingga
 * pengguna dapat melakukan pembayaran dan tersimpan di sistem back-end.
 *
 * @author Airell Ramadhan B
 */

public class PaymentRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/create";
    private final Map<String,String> params;

    public PaymentRequest(String productCount, String shipmentAddress, Response.Listener<String>listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(LoginActivity.loggedAccount.id));
        params.put("productId", String.valueOf(ProductFragment.productClicked.id));
        params.put("productCount", productCount);
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan",String.valueOf(ProductFragment.productClicked.shipmentPlans));
    }

    public Map<String,String> getParams(){
        return params;
    }
}
