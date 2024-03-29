package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import airellJmartAK.jmart_android.model.Account;
import airellJmartAK.jmart_android.request.LoginRequest;

/**
 * LoginActivity Class
 *
 * Digunakan untuk melakukan login pada aplikasi.
 *
 * @author Airell Ramadhan B
 * @version 0.1
 */

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    public static Account loggedAccount = null;

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("JMART");

        TextView registerText = findViewById(R.id.textReg);

        /** Saat text register ditekan maka akan membuka RegisterActivity */
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        EditText textEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText textPassword = findViewById(R.id.editTextTextPassword);
        Button buttonLogin = findViewById(R.id.button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null) {
                                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                loggedAccount = gson.fromJson(object.toString(), Account.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,"Login Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                    }
                };
                LoginRequest loginRequest = new LoginRequest(textEmail.getText().toString(), textPassword.getText().toString(), listener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(loginRequest);
            }
        });
    }
}