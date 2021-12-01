package airellJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mainText = findViewById(R.id.textView);
        mainText.setText("Hello " + LoginActivity.getLoggedAccount().name + "!");
    }
}