package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

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

import airellJmartAK.jmart_android.model.Payment;
import airellJmartAK.jmart_android.model.Product;
import airellJmartAK.jmart_android.request.PaymentRequest;

public class PaymentActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Payment paid = null;
    Product product = ProductFragment.productClicked;
    private double discountedPrice = product.price * (100.0 - product.discount) / 100.0;
    public double total = 10000 + discountedPrice;
    private int prodCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView category = findViewById(R.id.coCategory);
        TextView productName = findViewById(R.id.coName);
        TextView priceProd = findViewById(R.id.coDiscPrice);
        TextView priceInvoice = findViewById(R.id.coRealPrice);
        TextView shipment = findViewById(R.id.coShip);
        TextView discount = findViewById(R.id.coDiscount);
        TextView amount = findViewById(R.id.itemCount);
        TextView totalPrice = findViewById(R.id.coTotal);
        EditText deliveryAddress = findViewById(R.id.deliveryAddress);
        Button buyNow = findViewById(R.id.checkoutButton);

        category.setText(String.valueOf(ProductFragment.productClicked.category));
        productName.setText(ProductFragment.productClicked.name);
        priceProd.setText("Rp" + String.valueOf(discountedPrice));
        priceInvoice.setText("Rp" + String.valueOf(ProductFragment.productClicked.price));
        discount.setText(String.valueOf(ProductFragment.productClicked.discount) + "%");
        totalPrice.setText("Rp" + String.valueOf(total));

        switch (ProductFragment.productClicked.shipmentPlans) {
            case 0:
                shipment.setText("INSTANT");
                break;
            case 1:
                shipment.setText("SAME DAY");
                break;
            case 2:
                shipment.setText("NEXT DAY");
                break;
            case 3:
                shipment.setText("REGULER");
                break;
            default:
                shipment.setText("KARGO");
                break;
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = deliveryAddress.getText().toString();
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object != null){
                                Toast.makeText(PaymentActivity.this, "success",Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(PaymentActivity.this, "failed",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(PaymentActivity.this, "Failed. Check your balance.",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                PaymentRequest paymentRequest = new PaymentRequest(String.valueOf(prodCount), address, listener, null);
                RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                queue.add(paymentRequest);
            }
        });

    }
}