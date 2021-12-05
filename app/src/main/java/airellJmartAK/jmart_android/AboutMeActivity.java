package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView balance;
    private Button registerStore;

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
        registerStore = findViewById(R.id.btnRegisterStore);

        registerStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStore.setVisibility(v.INVISIBLE);
                formRegist.setVisibility(v.VISIBLE);
            }
        });
    }
}