package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import airellJmartAK.jmart_android.model.Store;
import airellJmartAK.jmart_android.request.RegisterStoreRequest;
import airellJmartAK.jmart_android.request.TopUpRequest;

public class AboutMeActivity extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView balance;
    private Button registerStore;

    private static Store storeAccount = null;
    private static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = findViewById(R.id.accountName);
        name.setText("" + LoginActivity.getLoggedAccount().name);

        email = findViewById(R.id.accountEmail);
        email.setText("" + LoginActivity.getLoggedAccount().email);

        balance = findViewById(R.id.accountBalance);
        balance.setText("" + LoginActivity.getLoggedAccount().balance);

        ConstraintLayout formRegist = findViewById(R.id.constraintLayout3);
        ConstraintLayout accountStoreDetail = findViewById(R.id.constraintLayoutAccountStoreDetail);

        EditText topUpInput = findViewById(R.id.editTopUp);
        EditText nameReg = findViewById(R.id.editNameReg);
        EditText addressReg = findViewById(R.id.editAddressReg);
        EditText phoneNumReg = findViewById(R.id.editPhoneReg);

        registerStore = findViewById(R.id.btnRegisterStore); // Tombol untuk menampilkan Form daftar
        Button topUp = findViewById(R.id.btnTopUp);
        Button createReg = findViewById(R.id.btnRegister);
        Button cancelReg = findViewById(R.id.btnCancel);

        CardView registerStoreCard = findViewById(R.id.cardRegisterStore);
        CardView storeAboutCard = findViewById(R.id.cardStoreAbout);

        registerStore.setVisibility(View.GONE);

        if (LoginActivity.getLoggedAccount().store == null){
            registerStore.setVisibility(View.VISIBLE);
        }
        else {
            storeAboutCard.setVisibility(View.VISIBLE);
            TextView nameCard = findViewById(R.id.dataNameTextAbout);
            nameCard.setText("" + LoginActivity.getLoggedAccount().store.name);
            TextView addressCard = findViewById(R.id.dataAddressTextAbout);
            addressCard.setText("" + LoginActivity.getLoggedAccount().store.address);
            TextView phoneCard = findViewById(R.id.dataPhoneTextAbout);
            phoneCard.setText("" + LoginActivity.getLoggedAccount().store.phoneNumber);
        }

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Boolean.parseBoolean(response)){
                            Toast.makeText(AboutMeActivity.this, "Top Up Success", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AboutMeActivity.this, "Top Up Failed!", Toast.LENGTH_SHORT).show();
                        }
                        LoginActivity.loggedAccount.balance += Double.parseDouble(topUpInput.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                };
                TopUpRequest topUpRequest = new TopUpRequest(LoginActivity.getLoggedAccount().id, Double.parseDouble(topUpInput.getText().toString()), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topUpRequest);
            }
        });

        registerStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStore.setVisibility(v.GONE);
                formRegist.setVisibility(v.VISIBLE);
                cancelReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerStore.setVisibility(v.VISIBLE);
                        formRegist.setVisibility(v.GONE);
                    }
                });
            }
        });

        createReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(AboutMeActivity.this, "Register Store Success!", Toast.LENGTH_SHORT).show();
                            LoginActivity.loggedAccount.store = gson.fromJson(object.toString(), Store.class);
                            System.out.println(LoginActivity.loggedAccount.store);
                            finish();
                            startActivity(getIntent());
                        } catch (JSONException e){
                            Toast.makeText(AboutMeActivity.this, "Register Store Failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                RegisterStoreRequest registerStoreRequest = new RegisterStoreRequest(LoginActivity.getLoggedAccount().id, nameReg.getText().toString(), addressReg.getText().toString(), phoneNumReg.getText().toString(), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(registerStoreRequest);
            }
        });
    }
}