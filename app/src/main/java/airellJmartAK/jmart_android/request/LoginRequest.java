package airellJmartAK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginRequest Class
 *
 * LoginRequest berfungsi untuk menghubungkan ke back-end sehingga
 * pengguna dapat melakukan login dengan akun yang tersimpan di sistem back-end.
 *
 * @author Airell Ramadhan B
 */

public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/login";
    private final Map<String,String> params;

    public LoginRequest(String email, String password, Response.Listener<String>listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String,String> getParams(){
        return params;
    }
}

