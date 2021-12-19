package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import airellJmartAK.jmart_android.model.Product;
import airellJmartAK.jmart_android.request.CreateProductRequest;

/**
 * CreateProductActivity Class
 *
 * Digunakan untuk menambahkan produk yang akan dijual
 * pada aplikasi.
 *
 * @author Airell Ramadhan B
 * @version 0.1
 */

public class CreateProductActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        getSupportActionBar().setTitle("JMART");

        EditText nameInput = findViewById(R.id.inputNameCreate);
        EditText weightInput = findViewById(R.id.inputWeightCreate);
        EditText priceInput = findViewById(R.id.inputPriceCreate);
        EditText discountInput = findViewById(R.id.inputDiscountCreate);
        RadioGroup condition = findViewById(R.id.conditionList);
        RadioButton newCondition = findViewById(R.id.radioNew);
        RadioButton usedCondition = findViewById(R.id.radioUsed);
        Button create = (Button) findViewById(R.id.btnCreate);
        Button cancel = (Button) findViewById(R.id.btnCancel);
        Spinner category = (Spinner) findViewById(R.id.spinnerCategory);
        Spinner shipmentPlans = (Spinner) findViewById(R.id.spinnerShipment);

        /** Untuk menentukan value yang di-return saat memilih radio button */
        condition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioNew:
                        newCondition.setChecked(true);
                        break;
                    case R.id.radioUsed:
                        usedCondition.setChecked(true);
                        break;
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            product = gson.fromJson(object.toString(), Product.class);
                            Toast.makeText(CreateProductActivity.this,"Product Terdaftar",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        } catch (JSONException e) {
                            Toast.makeText(CreateProductActivity.this,"Product Gagal Terdaftar",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        System.out.println(product);
                    }
                };
                CreateProductRequest createProductRequest = new CreateProductRequest(LoginActivity.getLoggedAccount().id, nameInput.getText().toString(), weightInput.getText().toString(), String.valueOf(usedCondition.isChecked()) ,priceInput.getText().toString(), discountInput.getText().toString(), category.getSelectedItem().toString(), convertShipmentPlans(shipmentPlans.getSelectedItem().toString()),listener,null );
                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(createProductRequest);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInput.setText("");
                weightInput.setText("");
                priceInput.setText("");
                discountInput.setText("");
                usedCondition.setChecked(false);
                newCondition.setChecked(false);
                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected String convertShipmentPlans(String shipment){
        switch (shipment) {
            case "INSTANT":
                return "0";
            case "SAME DAY":
                return "1";
            case "NEXT DAY":
                return "2";
            case "REGULER":
                return "3";
            default:
                return "4";
        }
    }
}