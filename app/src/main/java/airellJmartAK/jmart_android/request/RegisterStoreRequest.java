package airellJmartAK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import airellJmartAK.jmart_android.LoginActivity;

public class RegisterStoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/" + LoginActivity.getLoggedAccount().id + "/registerStore";
    private final Map<String,String> params;

    public RegisterStoreRequest(int id, String name, String address, String phoneNumber, Response.Listener<String>listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);
        Integer i = id;
        params = new HashMap<>();
        params.put("id", i.toString());
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String,String> getParams(){
        return params;
    }
}
