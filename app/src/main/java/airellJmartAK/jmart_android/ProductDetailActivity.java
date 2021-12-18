package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().setTitle("Product Detail");

        TextView productName = findViewById(R.id.productDetailName);
        TextView productPrice = findViewById(R.id.productDetailPrice);
        TextView productCategory = findViewById(R.id.productDetailCategory);
        TextView productCondition = findViewById(R.id.productDetailCondition);
        TextView productWeight = findViewById(R.id.productDetailWeight);
        TextView productShipmentPlan = findViewById(R.id.productDetailShipment);
        Button buyButton = findViewById(R.id.btnBuy);

        productName.setText(ProductFragment.productClicked.name);
        productPrice.setText("Rp" + String.valueOf(ProductFragment.productClicked.price));
        productCategory.setText(String.valueOf(ProductFragment.productClicked.category));

        String condition = "";
        if (ProductFragment.productClicked.conditionUsed){
            condition = "Used";
        } else {
            condition = "New";
        }
        productCondition.setText(condition);

        productWeight.setText(String.valueOf(ProductFragment.productClicked.weight));

        int shipmentPlansValue = ProductFragment.productClicked.shipmentPlans;
        if (shipmentPlansValue == 0){
            productShipmentPlan.setText("INSTANT");
        } else if(shipmentPlansValue == 1){
            productShipmentPlan.setText("SAME DAY");
        } else if (shipmentPlansValue == 2){
            productShipmentPlan.setText("NEXT DAY");
        } else if (shipmentPlansValue == 3){
            productShipmentPlan.setText("REGULER");
        } else if (shipmentPlansValue == 4){
            productShipmentPlan.setText("CARGO");
        } else {
            productShipmentPlan.setText("ERROR!");
        }

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailActivity.this, "Add to a Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}