package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import airellJmartAK.jmart_android.model.Payment;

public class PaymentActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Payment paid = null;

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
        priceProd.setText("Rp" + String.valueOf(ProductFragment.productClicked.price));
        priceInvoice.setText("Rp" + String.valueOf(ProductFragment.productClicked.price));
        discount.setText(String.valueOf(ProductFragment.productClicked.discount) + "%");
        totalPrice.setText("Rp" + String.valueOf(ProductFragment.productClicked.price));
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
                shipment.setText("CARGO");
                break;
        }

    }
}