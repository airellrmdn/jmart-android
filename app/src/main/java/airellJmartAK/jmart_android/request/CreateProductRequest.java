package airellJmartAK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * CreateProductRequest Class
 *
 * CreateProductRequest berfungsi untuk menghubungkan ke back-end sehingga
 * pengguna dapat menambahkan produk dan akan tersimpan di sistem back-end.
 *
 * @author Airell Ramadhan B
 */

public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String,String> params;

    public CreateProductRequest(int id, String name, String weight, String conditionUsed, String price, String discount, String category, String shipmentPlans, Response.Listener<String>listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);
        Integer i = id;
        params = new HashMap<>();
        params.put("accountId", i.toString());
        params.put("name", name);
        params.put("weight", weight);
        params.put("conditionUsed", conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }

    public Map<String,String> getParams(){
        return params;
    }
}
