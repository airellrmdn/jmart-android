package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import airellJmartAK.jmart_android.model.Account;
import airellJmartAK.jmart_android.request.RegisterRequest;

/**
 * RegisterActivity Class
 *
 * Digunakan untuk menampilkan halaman yang akan
 * digunakan untuk melakukan register akun.
 *
 * @author Airell Ramadhan B
 * @version 0.1
 */

public class RegisterActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userName = findViewById(R.id.editTextTextPersonName);
        EditText userEmail = findViewById(R.id.editTextTextEmailAddress2);
        EditText userPass = findViewById(R.id.editTextTextPassword2);
        Button buttonRegister = findViewById(R.id.buttonReg);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                loggedAccount = gson.fromJson(object.toString(), Account.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userName.getText().toString(), userEmail.getText().toString(), userPass.getText().toString(), listener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(registerRequest);
            }
        });
    }
}